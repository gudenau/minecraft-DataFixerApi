package net.gudenau.minecraft.datafixerapi.impl;

import com.google.gson.JsonObject;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import net.fabricmc.loader.api.FabricLoader;
import net.gudenau.minecraft.datafixerapi.api.v0.DataFixerApi;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import static net.gudenau.minecraft.datafixerapi.DataFixerEntry.MOD_ID;
import static net.gudenau.minecraft.datafixerapi.DataFixerEntry.LOGGER;

public final class DataFixerApiImpl implements DataFixerApi{
    private static final int LATEST_VANILLA_SCHEMA_VERSION = DataFixUtils.makeKey(SharedConstants.getGameVersion().getSaveVersion().getId());
    public static final BiFunction<Integer, Schema, Schema> VANILLA_DATAFIXER = (version, parent)->Schemas.getFixer().getSchema(LATEST_VANILLA_SCHEMA_VERSION);
    public static final DataFixerApiImpl INSTANCE = new DataFixerApiImpl();
    
    private static final Set<DynamicOps<?>> MISSING_OPS = new HashSet<>();
    static{
        if(FabricLoader.getInstance().isDevelopmentEnvironment()){
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                synchronized(MISSING_OPS){
                    if(!MISSING_OPS.isEmpty()){
                        LOGGER.warn("Missing ops:");
                        MISSING_OPS.forEach((op)->LOGGER.warn("\t" + op));
                    }
                }
            }, MOD_ID + " Devel Reminder"));
        }
    }
    
    private final Map<Identifier, DataFixerEntry> dataFixers = new IdentityHashMap<>();
    {
        // Might as well.
        dataFixers.put(new Identifier("vanilla"), new DataFixerEntry(
            ((DataFixerProxy)Schemas.getFixer()).original(),
            SharedConstants.getGameVersion().getSaveVersion().getId()
        ));
    }
    
    public Schema baseSchema = new Schema(0, VANILLA_DATAFIXER.apply(-1, null));
    
    private volatile boolean isLocked = false;
    
    public void lock(){
        isLocked = true;
    }
    
    private record DataFixerEntry(
        @NotNull DataFixer fixer,
        int dataVersion
    ){}
    
    @Override
    public @NotNull DataFixer register(@NotNull Identifier identifier, int currentVersion, @NotNull DataFixer fixer){
        Objects.requireNonNull(identifier, "identifier can not be null");
        if(currentVersion <= 0){
            throw new IllegalArgumentException("currentVersion must be greater than 0");
        }
        Objects.requireNonNull(fixer, "fixer can not be null");
        
        if(isLocked){
            throw new IllegalStateException("Data fixer %s was registered after the registry was locked".formatted(identifier));
        }
        
        LOGGER.info("Registering data fixer: " + identifier);
        dataFixers.put(identifier, new DataFixerEntry(fixer, currentVersion));
        return fixer;
    }
    
    @Override
    public @NotNull Optional<DataFixer> getDataFixer(@NotNull Identifier identifier){
        Objects.requireNonNull(identifier, "identifier can not be null");
        return Optional.ofNullable(dataFixers.get(identifier)).map(DataFixerEntry::fixer);
    }
    
    @Override
    public @NotNull Set<DataFixer> getDataFixers(@NotNull String namespace){
        Objects.requireNonNull(namespace, "namespace can not be null");
        return dataFixers.entrySet().stream()
            .filter((entry)->entry.getKey().getNamespace().equals(namespace))
            .map((entry)->entry.getValue().fixer())
            .collect(Collectors.toUnmodifiableSet());
    }
    
    private int getDataVersion(@NotNull NbtCompound tag, @NotNull Identifier identifier){
        if(tag.contains("ModDataVersions", NbtElement.COMPOUND_TYPE)){
            return tag.getCompound("ModDataVersions").getInt(identifier.toString());
        }
        return 0;
    }
    
    private int getDataVersion(@NotNull JsonObject object, @NotNull Identifier identifier){
        var element = object.get("ModDataVersions");
        if(element instanceof JsonObject versions){
            var version = versions.get(identifier.toString());
            return version == null ? 0 : version.getAsInt();
        }
        return 0;
    }
    
    // There is likely a faster way to handle this.
    <T> Dynamic<T> update(DSL.TypeReference typeReference, Dynamic<T> dynamic, T originalValue){
        var ops = dynamic.getOps();
        if(ops == NbtOps.INSTANCE){
            if(originalValue instanceof NbtCompound compound){
                return doUpdate(typeReference, dynamic, (id)->getDataVersion(compound, id));
            }
        }else if(ops == JsonOps.INSTANCE){
            if(originalValue instanceof JsonObject object){
                return doUpdate(typeReference, dynamic, (id)->getDataVersion(object, id));
            }
        }
        synchronized(MISSING_OPS){
            if(MISSING_OPS.add(ops)){
                LOGGER.warn("Unknown DynamicOps: " + ops);
            }
        }
        return dynamic;
    }
    
    private <T> Dynamic<T> doUpdate(DSL.TypeReference typeReference, Dynamic<T> original, ToIntFunction<Identifier> versionGetter){
        var current = original;
        for(var entry : dataFixers.entrySet()){
            int currentVersion = versionGetter.applyAsInt(entry.getKey());
            var fixerEntry = entry.getValue();
            current = fixerEntry.fixer().update(
                typeReference,
                current,
                currentVersion,
                fixerEntry.dataVersion()
            );
        }
    
        return current;
    }
    
    public void addDataVersions(NbtCompound tag){
        var versionTag = new NbtCompound();
        dataFixers.forEach((id, entry)->
            versionTag.putInt(id.toString(), entry.dataVersion())
        );
        tag.put("ModDataVersions", versionTag);
    }
    
    public void addDataVersions(JsonObject object){
        var versionObject = new JsonObject();
        dataFixers.forEach((id, entry)->
            versionObject.addProperty(id.toString(), entry.dataVersion())
        );
        object.add("ModDataVersions", versionObject);
    }
}

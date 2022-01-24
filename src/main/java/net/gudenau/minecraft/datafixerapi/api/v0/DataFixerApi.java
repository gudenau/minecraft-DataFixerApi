package net.gudenau.minecraft.datafixerapi.api.v0;

import com.mojang.datafixers.DataFixer;
import java.util.Optional;
import java.util.Set;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * The place to register and query custom {@link DataFixer}s.
 */
public interface DataFixerApi{
    /**
     * Gets the singleton instance of {@link DataFixerApi}.
     *
     * @return The singleton instance of {@link DataFixerApi}
     */
    static @NotNull DataFixerApi getInstance(){
        return DataFixerApiImpl.INSTANCE;
    }
    
    /**
     * Registers a new {@link DataFixer}. Should only be called from {@link DataFixerAdder} or {@link ClientDataFixerAdder}.
     *
     * @param identifier The {@link Identifier} of the new fixer
     * @param currentVersion The current data version of the mod
     * @param fixer The fixer to register
     * @return The passed fixer
     */
    @NotNull DataFixer register(@NotNull Identifier identifier, int currentVersion, @NotNull DataFixer fixer);
    
    /**
     * Gets a registered {@link DataFixer} from the {@link Identifier} used to register it.
     *
     * @param identifier The identifier used to register the fixer
     * @return The fixer, or empty if not found
     */
    @NotNull Optional<DataFixer> getDataFixer(@NotNull Identifier identifier);
    
    /**
     * Gets all of the {@link DataFixer}s registered in the provided namespace.
     *
     * @param namespace The namespace to query
     * @return A set of found fixers
     */
    @NotNull Set<DataFixer> getDataFixers(@NotNull String namespace);
}

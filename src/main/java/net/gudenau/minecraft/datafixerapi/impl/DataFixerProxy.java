package net.gudenau.minecraft.datafixerapi.impl;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import org.jetbrains.annotations.NotNull;

/**
 * A simple proxy used to hook into all of the vanilla data fixer calls.
 */
public record DataFixerProxy(
    @NotNull DataFixer original
) implements DataFixer{
    @Override
    public <T> Dynamic<T> update(DSL.TypeReference type, Dynamic<T> dynamic, int version, int newVersion){
        T originalValue = dynamic.getValue();
        dynamic = original.update(type, dynamic, version, newVersion);
        return DataFixerApiImpl.INSTANCE.update(type, dynamic, originalValue);
    }
    
    @Override
    public Schema getSchema(int key){
        return original.getSchema(key);
    }
}

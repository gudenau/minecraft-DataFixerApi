package net.gudenau.minecraft.datafixerapi.api.v0;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import java.util.function.BiFunction;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import org.jetbrains.annotations.NotNull;

/**
 * A few basic {@link Schema}s that are likely to be used by several {@link DataFixer}s.
 */
public interface BaseSchemas{
    /**
     * The base {@link Schema} that all custom {@link DataFixer}s should use for version 0.
     */
    @NotNull SchemaFactory BASE_SCHEMA = (version, parent)->DataFixerApiImpl.INSTANCE.baseSchema;
    
    /**
     * The vanilla identifier normalizing {@link Schema}.
     */
    @NotNull SchemaFactory IDENTIFIER_NORMALIZING_SCHEMA = IdentifierNormalizingSchema::new;
    
    /**
     * A blank, no-op {@link Schema}.
     */
    @NotNull SchemaFactory EMPTY_SCHEMA = Schema::new;
    
    /**
     * A small interface to clean up the {@link BiFunction}s that would otherwise be used.
     */
    @FunctionalInterface
    interface SchemaFactory extends BiFunction<Integer, Schema, Schema>{
        @NotNull Schema apply(int version, @NotNull Schema parent);
        
        default @NotNull Schema apply(@NotNull Integer version, @NotNull Schema parent){
            return apply(version.intValue(), parent);
        }
    }
}

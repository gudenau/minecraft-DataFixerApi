package net.gudenau.minecraft.datafixerapi.api.v0;

import com.mojang.datafixers.DataFixer;
import org.jetbrains.annotations.NotNull;

/**
 * An entrypoint you should use to register {@link DataFixer}s that exist on the server.
 */
public interface DataFixerAdder{
    /**
     * Invoked on the server and client to register new {@link DataFixer}s.
     *
     * @param dataFixerApi The instance returned by {@link DataFixerApi#getInstance}.
     */
    void addDataFixers(@NotNull DataFixerApi dataFixerApi);
}

package net.gudenau.minecraft.datafixerapi.api.v0;

import com.mojang.datafixers.DataFixer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

/**
 * An entrypoint you should use to register {@link DataFixer}s that only exist on the client.
 */
@Environment(EnvType.CLIENT)
public interface ClientDataFixerAdder{
    /**
     * Invoked on the client to register new {@link DataFixer}s.
     *
     * @param dataFixerApi The instance returned by {@link DataFixerApi#getInstance}.
     */
    void addClientDataFixers(@NotNull DataFixerApi dataFixerApi);
}

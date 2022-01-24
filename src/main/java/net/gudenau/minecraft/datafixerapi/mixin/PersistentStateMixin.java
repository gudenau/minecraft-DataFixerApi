package net.gudenau.minecraft.datafixerapi.mixin;

import java.io.File;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PersistentState.class)
public abstract class PersistentStateMixin{
    @Inject(
        method = "save",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void save(File file, CallbackInfo ci, NbtCompound tag){
        DataFixerApiImpl.INSTANCE.addDataVersions(tag);
    }
}

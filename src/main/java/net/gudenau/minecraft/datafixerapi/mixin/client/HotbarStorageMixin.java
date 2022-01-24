package net.gudenau.minecraft.datafixerapi.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(HotbarStorage.class)
public abstract class HotbarStorageMixin{
    @Inject(
        method = "save",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/GameVersion;getWorldVersion()I"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void serialize(
        CallbackInfo ci,
        NbtCompound tag
    ){
        DataFixerApiImpl.INSTANCE.addDataVersions(tag);
    }
}

package net.gudenau.minecraft.datafixerapi.mixin;

import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin{
    @Inject(
        method = "updateProperties",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/GameVersion;getWorldVersion()I"
        )
    )
    private void updateProperties(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci){
        DataFixerApiImpl.INSTANCE.addDataVersions(levelNbt);
    }
}

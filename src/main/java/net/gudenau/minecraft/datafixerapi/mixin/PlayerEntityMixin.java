package net.gudenau.minecraft.datafixerapi.mixin;

import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin{
    @Inject(
        method = "writeCustomDataToNbt",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            shift = At.Shift.AFTER
        )
    )
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        DataFixerApiImpl.INSTANCE.addDataVersions(nbt);
    }
}

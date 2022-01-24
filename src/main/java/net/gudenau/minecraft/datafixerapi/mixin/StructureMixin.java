package net.gudenau.minecraft.datafixerapi.mixin;

import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Structure.class)
public abstract class StructureMixin{
    @Inject(
        method = "writeNbt",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/GameVersion;getWorldVersion()I"
        )
    )
    private void serialize(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cir){
        DataFixerApiImpl.INSTANCE.addDataVersions(tag);
    }
}

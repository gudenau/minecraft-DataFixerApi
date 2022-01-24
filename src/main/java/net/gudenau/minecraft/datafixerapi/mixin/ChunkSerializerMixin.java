package net.gudenau.minecraft.datafixerapi.mixin;

import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkSerializer.class)
public abstract class ChunkSerializerMixin{
    @Inject(
        method = "serialize",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/GameVersion;getWorldVersion()I"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void serialize(
        ServerWorld world, Chunk chunk,
        CallbackInfoReturnable<NbtCompound> cir,
        ChunkPos chunkPos, NbtCompound tag
    ){
        DataFixerApiImpl.INSTANCE.addDataVersions(tag);
    }
}

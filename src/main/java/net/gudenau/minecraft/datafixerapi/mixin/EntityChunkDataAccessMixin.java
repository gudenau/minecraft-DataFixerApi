package net.gudenau.minecraft.datafixerapi.mixin;

import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.ChunkDataList;
import net.minecraft.world.storage.EntityChunkDataAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityChunkDataAccess.class)
public abstract class EntityChunkDataAccessMixin{
    @Inject(
        method = "writeChunkData",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/GameVersion;getWorldVersion()I"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void writeChunkData(
        ChunkDataList<Entity> dataList,
        CallbackInfo ci,
        ChunkPos chunkPos, NbtList nbtList, NbtCompound nbtCompound
    ){
        DataFixerApiImpl.INSTANCE.addDataVersions(nbtCompound);
    }
}

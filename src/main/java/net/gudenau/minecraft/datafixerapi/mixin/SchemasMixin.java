package net.gudenau.minecraft.datafixerapi.mixin;

import com.mojang.datafixers.DataFixer;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerProxy;
import net.minecraft.datafixer.Schemas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Schemas.class)
public abstract class SchemasMixin{
    @Inject(
        method = "create",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void create(CallbackInfoReturnable<DataFixer> cir){
        cir.setReturnValue(new DataFixerProxy(cir.getReturnValue()));
    }
}

package net.gudenau.minecraft.datafixerapi.mixin;

import com.google.gson.JsonObject;
import java.util.Map;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.stat.ServerStatHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerStatHandler.class)
public abstract class ServerStatHandlerMixin{
    @Inject(
        method = "asString",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/gson/JsonObject;addProperty(Ljava/lang/String;Ljava/lang/Number;)V",
            ordinal = 1
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void asString(CallbackInfoReturnable<String> cir, Map<?, ?> map, JsonObject stats, JsonObject object){
        DataFixerApiImpl.INSTANCE.addDataVersions(object);
    }
}

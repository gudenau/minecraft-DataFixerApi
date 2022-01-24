package net.gudenau.minecraft.datafixerapi.mixin;

import com.google.gson.JsonElement;
import java.util.Map;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin{
    @Inject(
        method = "save",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/gson/JsonElement;getAsJsonObject()Lcom/google/gson/JsonObject;"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void save(CallbackInfo ci, Map<Identifier, AdvancementProgress> map, JsonElement element){
        DataFixerApiImpl.INSTANCE.addDataVersions(element.getAsJsonObject());
    }
}

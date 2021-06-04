package com.feroxclient.fabric.mixins;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.event.events.TickEvent;
import com.feroxclient.fabric.util.MinecraftTrait;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public final class MinecraftClientMixin implements MinecraftTrait {
    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        if (mc.world != null && mc.player != null) {
            FeroxMod.EVENT_BUS.post(new TickEvent.Ingame());
        }
        FeroxMod.EVENT_BUS.post(new TickEvent.Always());
    }
}

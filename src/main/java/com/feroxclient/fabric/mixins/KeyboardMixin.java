package com.feroxclient.fabric.mixins;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.event.events.KeyPressEvent;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Keyboard.class)
public final class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        if(key != GLFW.GLFW_KEY_UNKNOWN && i != GLFW.GLFW_RELEASE){
            FeroxMod.EVENT_BUS.post(new KeyPressEvent(key));
        }
    }
}

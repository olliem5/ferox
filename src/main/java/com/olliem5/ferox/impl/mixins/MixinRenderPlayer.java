package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.render.Chams;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(RenderPlayer.class)
public final class MixinRenderPlayer implements Minecraft {
    @Inject(method = "renderLeftArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode = 181), cancellable = true)
    public void renderLeftArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.leftArm.getValue()) {
            Chams.chamsMode.renderLeftArmPre(abstractClientPlayer, callbackInfo);
        }
    }

    @Inject(method = "renderLeftArm", at = @At(value = "RETURN"), cancellable = true)
    public void renderLeftArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.leftArm.getValue()) {
            Chams.chamsMode.renderLeftArmPost(abstractClientPlayer, callbackInfo);
        }
    }

    @Inject(method = "renderRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode = 181), cancellable = true)
    public void renderRightArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.rightArm.getValue()) {
            Chams.chamsMode.renderRightArmPre(abstractClientPlayer, callbackInfo);
        }
    }

    @Inject(method = "renderRightArm", at = @At(value = "RETURN"), cancellable = true)
    public void renderRightArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.rightArm.getValue()) {
            Chams.chamsMode.renderRightArmPost(abstractClientPlayer, callbackInfo);
        }
    }
}

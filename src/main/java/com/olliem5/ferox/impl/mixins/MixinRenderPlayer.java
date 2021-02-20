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
    public void renderLeftArmBegin(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.leftHand.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glEnable(10754);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

            GL11.glColor4f(Chams.leftHandColour.getValue().getRed() / 255f, Chams.leftHandColour.getValue().getGreen() / 255f, Chams.leftHandColour.getValue().getBlue() / 255f, Chams.leftHandColour.getValue().getAlpha() / 255f);
        }
    }

    @Inject(method = "renderLeftArm", at = @At(value = "RETURN"), cancellable = true)
    public void renderLeftArmReturn(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.leftHand.getValue()) {
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
        }
    }

    @Inject(method = "renderRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode = 181), cancellable = true)
    public void renderRightArmBegin(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.rightHand.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glEnable(10754);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

            GL11.glColor4f(Chams.rightHandColour.getValue().getRed() / 255f, Chams.rightHandColour.getValue().getGreen() / 255f, Chams.rightHandColour.getValue().getBlue() / 255f, Chams.rightHandColour.getValue().getAlpha() / 255f);
        }
    }

    @Inject(method = "renderRightArm", at = @At(value = "RETURN"), cancellable = true)
    public void renderRightArmReturn(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        if (abstractClientPlayer == mc.player && ModuleManager.getModuleByName("Chams").isEnabled() && Chams.rightHand.getValue()) {
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
        }
    }
}

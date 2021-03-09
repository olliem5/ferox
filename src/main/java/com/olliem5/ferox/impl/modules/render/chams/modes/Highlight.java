package com.olliem5.ferox.impl.modules.render.chams.modes;

import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.impl.modules.render.Chams;
import com.olliem5.ferox.impl.modules.render.chams.ChamsMode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author olliem5
 */

public final class Highlight extends ChamsMode {
    @Override
    public void renderCrystal(ModelBase crystal, ModelBase crystalNoBase, EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        float rotation = entityEnderCrystal.innerRotation + partialTicks;
        float rotationMoved = MathHelper.sin(rotation * 0.2f) / 2.0f + 0.5f;

        rotationMoved += rotationMoved * rotationMoved;

        GlStateManager.translate(x, y, z);

        GL11.glDisable(GL_LIGHTING);

        RenderUtil.prepareGL();

        GL11.glColor4f(Chams.crystalColour.getValue().getRed() / 255.0f, Chams.crystalColour.getValue().getGreen() / 255.0f, Chams.crystalColour.getValue().getBlue() / 255.0f, Chams.crystalColour.getValue().getAlpha() / 255.0f);

        if (entityEnderCrystal.shouldShowBottom()) {
            crystal.render(entityEnderCrystal, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
        } else {
            crystalNoBase.render(entityEnderCrystal, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
        }

        RenderUtil.releaseGL();
    }

    @Override
    public void renderLivingBase(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtil.prepareGL();

        GL11.glColor4f(Chams.getChamsColour(entity).getRed() / 255.0f, Chams.getChamsColour(entity).getGreen() / 255.0f, Chams.getChamsColour(entity).getBlue() / 255.0f, Chams.getChamsColour(entity).getAlpha() / 255.0f);

        modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        RenderUtil.releaseGL();
    }

    @Override
    public void renderLeftArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtil.prepareGL();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

        GL11.glColor4f(Chams.leftArmColour.getValue().getRed() / 255f, Chams.leftArmColour.getValue().getGreen() / 255f, Chams.leftArmColour.getValue().getBlue() / 255f, Chams.leftArmColour.getValue().getAlpha() / 255f);
    }

    @Override
    public void renderLeftArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        RenderUtil.releaseGL();
    }

    @Override
    public void renderRightArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtil.prepareGL();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

        GL11.glColor4f(Chams.rightArmColour.getValue().getRed() / 255f, Chams.rightArmColour.getValue().getGreen() / 255f, Chams.rightArmColour.getValue().getBlue() / 255f, Chams.rightArmColour.getValue().getAlpha() / 255f);
    }

    @Override
    public void renderRightArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        RenderUtil.releaseGL();
    }
}

package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.render.Chams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author olliem5
 */

@Mixin(RenderEnderCrystal.class)
public final class MixinRenderEnderCrystal implements Minecraft {
    @Final
    @Shadow
    private ModelBase modelEnderCrystal;

    @Final
    @Shadow
    private ModelBase modelEnderCrystalNoBase;

    @Redirect(method = "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void doRender(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.crystalsMode.getValue() == Chams.ChamsModes.Highlight) {
            return;
        } else {
            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V", at = @At("RETURN"), cancellable = true)
    public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.crystalsMode.getValue() == Chams.ChamsModes.Highlight) {
            GL11.glPushMatrix();

            float rotation = entity.innerRotation + partialTicks;
            float rotationMoved = MathHelper.sin(rotation * 0.2f) / 2.0f + 0.5f;
            rotationMoved += rotationMoved * rotationMoved;

            GlStateManager.translate(x, y, z);

            GL11.glEnable(GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0f, -1.0E7f);
            GL11.glPushAttrib(GL_ALL_ATTRIB_BITS);
            GL11.glPolygonMode(GL_FRONT, GL_FILL);

            GL11.glDisable(GL_TEXTURE_2D);
            GL11.glDisable(GL_LIGHTING);
            GL11.glDisable(GL_DEPTH_TEST);
            GL11.glEnable(GL_LINE_SMOOTH);
            GL11.glEnable(GL_BLEND);
            GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            GL11.glColor4f(Chams.crystalColour.getValue().getRed() / 255.0f, Chams.crystalColour.getValue().getGreen() / 255.0f, Chams.crystalColour.getValue().getBlue() / 255.0f, Chams.crystalColour.getValue().getAlpha() / 255.0f);

            if (entity.shouldShowBottom()) {
                modelEnderCrystal.render(entity, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                modelEnderCrystalNoBase.render(entity, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
            }

            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0f, 100000.0f);
            GL11.glDisable(GL_POLYGON_OFFSET_FILL);
            GL11.glPopMatrix();
        }
    }

    @Inject(method = "doRender", at = @At(value="HEAD"))
    public void doRenderPre(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.crystalsMode.getValue() == Chams.ChamsModes.Vanilla) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "doRender", at = @At(value="RETURN"))
    public void doRenderPost(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.crystalsMode.getValue() == Chams.ChamsModes.Vanilla) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}

package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.render.Chams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> implements Minecraft {

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Redirect(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModel(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.players.getValue() && entity instanceof EntityPlayer && Chams.playersMode.getValue() == Chams.ChamsModes.Highlight || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.animals.getValue() && entity instanceof EntityAnimal  && Chams.animalsMode.getValue() == Chams.ChamsModes.Highlight || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.mobs.getValue() && entity instanceof EntityMob && Chams.mobsMode.getValue() == Chams.ChamsModes.Highlight) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(10754);

            if (entity instanceof EntityPlayer) {
                GL11.glColor4f(Chams.playerColour.getValue().getRed() / 255.0f, Chams.playerColour.getValue().getGreen() / 255.0f, Chams.playerColour.getValue().getBlue() / 255.0f, Chams.playerColour.getValue().getAlpha() / 255.0f);
            }

            if (entity instanceof EntityAnimal) {
                GL11.glColor4f(Chams.animalColour.getValue().getRed() / 255.0f, Chams.animalColour.getValue().getGreen() / 255.0f, Chams.animalColour.getValue().getBlue() / 255.0f, Chams.animalColour.getValue().getAlpha() / 255.0f);
            }

            if (entity instanceof EntityMob) {
                GL11.glColor4f(Chams.mobColour.getValue().getRed() / 255.0f, Chams.mobColour.getValue().getGreen() / 255.0f, Chams.mobColour.getValue().getBlue() / 255.0f, Chams.mobColour.getValue().getAlpha() / 255.0f);
            }

            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
        } else {
            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method = "doRender", at = @At(value="HEAD"))
    public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.players.getValue() && entity instanceof EntityPlayer && Chams.playersMode.getValue() == Chams.ChamsModes.Vanilla || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.animals.getValue() && entity instanceof EntityAnimal  && Chams.animalsMode.getValue() == Chams.ChamsModes.Vanilla || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.mobs.getValue() && entity instanceof EntityMob && Chams.mobsMode.getValue() == Chams.ChamsModes.Vanilla) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "doRender", at = @At(value="RETURN"))
    public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.players.getValue() && entity instanceof EntityPlayer && Chams.playersMode.getValue() == Chams.ChamsModes.Vanilla || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.animals.getValue() && entity instanceof EntityAnimal  && Chams.animalsMode.getValue() == Chams.ChamsModes.Vanilla || ModuleManager.getModuleByName("Chams").isEnabled() && Chams.mobs.getValue() && entity instanceof EntityMob && Chams.mobsMode.getValue() == Chams.ChamsModes.Vanilla) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}

package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.render.Chams;
import com.olliem5.ferox.impl.modules.render.chams.modes.Highlight;
import com.olliem5.ferox.impl.modules.render.chams.modes.Vanilla;
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
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.crystals.getValue() && !Chams.chamsMode.equals(new Vanilla())) {
            return;
        } else {
            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V", at = @At("RETURN"), cancellable = true)
    public void doRender(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && !Chams.chamsMode.equals(new Vanilla())) {
            Chams.chamsMode.renderCrystal(modelEnderCrystal, modelEnderCrystalNoBase, entityEnderCrystal, x, y, z, entityYaw, partialTicks, callbackInfo);
        }
    }

    @Inject(method = "doRender", at = @At(value="HEAD"))
    public void doRenderPre(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.chamsMode.equals(new Vanilla())) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "doRender", at = @At(value="RETURN"))
    public void doRenderPost(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("Chams").isEnabled() && Chams.crystals.getValue() && Chams.chamsMode.equals(new Vanilla())) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}

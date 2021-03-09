package com.olliem5.ferox.impl.modules.render.chams;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

public abstract class ChamsMode {
    public void renderCrystal(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {}

    public void renderLivingBase(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}

    public void renderLeftArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {}

    public void renderLeftArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {}

    public void renderRightArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {}

    public void renderRightArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {}
}

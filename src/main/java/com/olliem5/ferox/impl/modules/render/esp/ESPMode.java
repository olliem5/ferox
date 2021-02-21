package com.olliem5.ferox.impl.modules.render.esp;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

public abstract class ESPMode implements Minecraft {
    public void drawESPMixin(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {}

    public void drawESPCrystal(ModelBase modelEnderCrystal, ModelBase modelEnderCrystalNoBase, EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {}

    public void drawESP(RenderWorldLastEvent event) {}
}

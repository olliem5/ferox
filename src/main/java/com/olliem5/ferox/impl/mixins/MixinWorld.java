package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.render.SkyColour;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 * @author linustouchtips
 */

@Mixin(World.class)
public final class MixinWorld {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(Entity entity, float partialTicks, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("SkyColour").isEnabled() && SkyColour.sky.getValue()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(new Vec3d(SkyColour.skyColour.getValue().getRed() / 255.0f, SkyColour.skyColour.getValue().getGreen() / 255.0f, SkyColour.skyColour.getValue().getBlue() / 255.0f));
        }
    }
}

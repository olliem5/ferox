package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.render.BlockHighlight;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(RenderGlobal.class)
public final class MixinRenderGlobal {
    @Inject(method = "drawSelectionBox", at = @At("HEAD"), cancellable = true)
    public void drawSelectionBox(EntityPlayer entityPlayer, RayTraceResult rayTraceResult, int execute, float partialTicks, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("BlockHighlight").isEnabled() && BlockHighlight.cancelSelectionBox.getValue()) {
            callbackInfo.cancel();
        }
    }
}

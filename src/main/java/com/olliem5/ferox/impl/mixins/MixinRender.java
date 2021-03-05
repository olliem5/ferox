package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.render.ESP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 */

@Mixin(Render.class)
public final class MixinRender<T extends Entity> {
    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    public void getTeamColour(T entity, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("ESP").isEnabled()) {
            callbackInfoReturnable.cancel();

            if (ESP.entityCheck(entity)) {
                callbackInfoReturnable.setReturnValue(ESP.getESPColour(entity).getRGB());
            }
        }
    }
}

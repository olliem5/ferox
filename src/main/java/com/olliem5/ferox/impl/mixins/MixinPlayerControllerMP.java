package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.exploit.Reach;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 */

@Mixin(PlayerControllerMP.class)
public final class MixinPlayerControllerMP {
    @Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
    private void getReachDistanceHook(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("Reach").isEnabled()) {
            callbackInfoReturnable.setReturnValue(Reach.distance.getValue());
        }
    }
}

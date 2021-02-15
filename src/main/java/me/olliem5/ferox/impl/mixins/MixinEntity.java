package me.olliem5.ferox.impl.mixins;

import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.impl.modules.movement.Velocity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(Entity.class)
public final class MixinEntity {
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void modifyAcceleration(Entity entity, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("Velocity").isEnabled() && Velocity.noPush.getValue()) {
            callbackInfo.cancel();
        }
    }
}

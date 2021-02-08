package me.olliem5.ferox.impl.mixins;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.impl.events.PlayerMoveEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {
    @Shadow
    public abstract void move(MoverType moverType, double x, double y, double z);

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType moverType, double x, double y, double z, CallbackInfo callbackInfo) {
        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(moverType, x, y, z);

        Ferox.EVENT_BUS.post(playerMoveEvent);

        if (playerMoveEvent.isCancelled()) {
            move(moverType, playerMoveEvent.getX(), playerMoveEvent.getY(), playerMoveEvent.getZ());

            callbackInfo.cancel();
        }
    }
}

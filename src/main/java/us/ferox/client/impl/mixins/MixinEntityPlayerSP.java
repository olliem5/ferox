package us.ferox.client.impl.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.ferox.client.Ferox;
import us.ferox.client.impl.events.EventPlayerUpdate;
import us.ferox.client.impl.events.PlayerMoveEvent;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {
    @Shadow
    public abstract void move(MoverType type, double x, double y, double z);

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo callbackInfo) {
        EventPlayerUpdate event = new EventPlayerUpdate();
        Ferox.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo callbackInfo) {
        PlayerMoveEvent event = new PlayerMoveEvent(type, x, y, z);
        Ferox.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            move(type, event.getX(), event.getY(), event.getZ());
            callbackInfo.cancel();
        }
    }
}

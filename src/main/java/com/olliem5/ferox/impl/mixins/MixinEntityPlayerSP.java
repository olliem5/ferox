package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.packet.RotationManager;
import com.olliem5.ferox.impl.events.PlayerMoveEvent;
import com.olliem5.ferox.impl.modules.exploit.Portals;
import com.olliem5.ferox.impl.modules.movement.Velocity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 */

@Mixin(EntityPlayerSP.class)
public final class MixinEntityPlayerSP {
    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreen(EntityPlayerSP entityPlayerSP) {
        if (ModuleManager.getModuleByName("Portals").isEnabled() && Portals.useGUIS.getValue()) return;
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void closeScreen(Minecraft minecraft, GuiScreen guiScreen) {
        if (ModuleManager.getModuleByName("Portals").isEnabled() && Portals.useGUIS.getValue()) return;
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType moverType, double x, double y, double z, CallbackInfo callbackInfo) {
        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(moverType, x, y, z);
        Ferox.EVENT_BUS.dispatchPaceEvent(playerMoveEvent);

        if (playerMoveEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At(value = "HEAD"))
    private void onUpdateWalkingPlayerPre(CallbackInfo callbackInfo) {
        RotationManager.updatePlayerRotations();
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At(value = "RETURN"))
    private void onUpdateWalkingPlayerPost(CallbackInfo info) {
        RotationManager.restorePlayerRotations();
    }

    @Inject(method = "pushOutOfBlocks(DDD)Z", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("Velocity").isEnabled() && Velocity.noPush.getValue() && Velocity.noPushBlocks.getValue()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}

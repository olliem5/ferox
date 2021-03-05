package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

/**
 * @author olliem5
 */

@Mixin(NetworkManager.class)
public final class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent.Send(packet);
        Ferox.EVENT_BUS.dispatchPaceEvent(packetEvent);

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent.Receive(packet);
        Ferox.EVENT_BUS.dispatchPaceEvent(packetEvent);

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_, CallbackInfo callbackInfo) {
        if (p_exceptionCaught_2_ instanceof IOException && ModuleManager.getModuleByName("AntiPacketKick").isEnabled()) {
            callbackInfo.cancel();
        }
    }
}
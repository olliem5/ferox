package me.olliem5.ferox.impl.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.impl.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(NetworkManager.class)
public final class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent.Send(packet);

        Ferox.EVENT_BUS.post(packetEvent);

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent.Receive(packet);

        Ferox.EVENT_BUS.post(packetEvent);

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
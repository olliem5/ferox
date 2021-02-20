package com.olliem5.ferox.impl.modules.movement;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.impl.events.PacketEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

/**
 * @author olliem5
 */

@FeroxModule(name = "NoRotate", description = "Prevents server packets from turning your head", category = Category.Movement)
public final class NoRotate extends Module {
    @PaceHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook) event.getPacket();

            sPacketPlayerPosLook.yaw = mc.player.rotationYaw;
            sPacketPlayerPosLook.pitch = mc.player.rotationPitch;
        }
    }
}

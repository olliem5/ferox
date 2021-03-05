package com.olliem5.ferox.impl.modules.miscellaneous;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.events.PacketEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * @author olliem5
 */

@FeroxModule(name = "Freecam", description = "Moves your camera outisde of your body", category = Category.Miscellaneous)
public final class Freecam extends Module {
    public static final NumberSetting<Integer> speed = new NumberSetting<>("Speed", "The speed to move the camera at", 1, 5, 10, 0);
    public static final Setting<Boolean> cancelPackets = new Setting<>("Cancel Packets", "Cancels packets when you are in freecam", true);

    public Freecam() {
        this.addSettings(
                speed,
                cancelPackets
        );
    }

    private double posX;
    private double posY;
    private double posZ;

    private float pitch;
    private float yaw;

    private boolean isRidingEntity;

    private Entity ridingEntity;
    private EntityOtherPlayerMP fakePlayer = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (mc.player.getRidingEntity() != null) {
            isRidingEntity = true;
            ridingEntity = mc.player.getRidingEntity();
            mc.player.dismountRidingEntity();
        } else {
            posX = mc.player.posX;
            posY = mc.player.posY;
            posZ = mc.player.posZ;
        }

        pitch = mc.player.rotationPitch;
        yaw = mc.player.rotationYaw;

        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYaw = mc.player.rotationYaw;
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;

        mc.world.addEntityToWorld(fakePlayer.entityId, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.player.noClip = false;
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(0.05f);
        mc.player.setPositionAndRotation(posX, posY, posZ, yaw, pitch);

        posX = posY = posZ = 0.0;
        pitch = yaw = 0.0f;

        mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0.0f;

        mc.world.removeEntityFromWorld(fakePlayer.entityId);

        fakePlayer = null;

        if (isRidingEntity) {
            mc.player.startRiding(ridingEntity, true);
        }
    }

    public void onUpdate() {
        if (nullCheck()) return;

        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.fallDistance = 0;
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed(speed.getValue() / 100.0f);
    }

    @PaceHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (cancelPackets.getValue() && event.getPacket() instanceof CPacketPlayer) {
            event.setCancelled(true);
        }
    }
}

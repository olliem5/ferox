package com.olliem5.ferox.impl.modules.miscellaneous;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.events.PacketEvent;
import com.olliem5.ferox.impl.events.PlayerMoveEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.*;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import org.lwjgl.input.Keyboard;

import java.security.Key;

/**
 * @author olliem5
 */

@FeroxModule(name = "Freecam", description = "Moves your camera outside of your body", category = Category.Miscellaneous)
public final class Freecam extends Module {
    public static final Setting<FreecamModes> mode = new Setting<>("Mode", "The mode to use for freecam, use camera for baritone", FreecamModes.Normal);
    public static final NumberSetting<Float> speed = new NumberSetting<>("Speed", "The speed to move the camera at", 0.01f, 0.20f, 0.40f, 2);

    public static final Setting<Boolean> cancelPackets = new Setting<>("Cancel Packets", "Cancels packets when you are in freecam", true);
    public static final Setting<Boolean> cPacketPlayer = new Setting<>(cancelPackets, "CPacketPlayer", "Cancels the CPacketPlayer packet in freecam", true);
    public static final Setting<Boolean> cPacketInput = new Setting<>(cancelPackets, "CPacketInput", "Cancels the CPacketInput packet in freecam", true);
    public static final Setting<Boolean> cPacketUseEntity = new Setting<>(cancelPackets, "CPacketUseEntity", "Cancels the CPacketUseEntity packet in freecam", true);
    public static final Setting<Boolean> cPacketPlayerTryUseItem = new Setting<>(cancelPackets, "CPacketPlayerTryUseItem", "Cancels the CPacketPlayerTryUseItem packet in freecam", true);
    public static final Setting<Boolean> cPacketPlayerTryUseItemOnBlock = new Setting<>(cancelPackets, "CPacketPlayerTryUseItemOnBlock", "Cancels the CPacketPlayerTryUseItemOnBlock packet in freecam", true);
    public static final Setting<Boolean> cPacketVehicleMove = new Setting<>(cancelPackets, "CPacketVehicleMove", "Cancels the CPacketVehicleMove packet in freecam", true);

    public Freecam() {
        this.addSettings(
                mode,
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

    private Entity ridingEntity = null;
    private EntityOtherPlayerMP fakePlayer = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            isRidingEntity = mc.player.getRidingEntity() != null;

            if (mc.player.getRidingEntity() == null) {
                posX = mc.player.posX;
                posY = mc.player.posY;
                posZ = mc.player.posZ;
            } else {
                ridingEntity = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
            }

            pitch = mc.player.rotationPitch;
            yaw = mc.player.rotationYaw;

            generateFakePlayer();

            mc.player.noClip = true;
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed(speed.getValue());
        } else {
            generateFakePlayer();

            fakePlayer.noClip = true;
            fakePlayer.capabilities.isFlying = true;
            fakePlayer.capabilities.setFlySpeed(speed.getValue());

            mc.setRenderViewEntity(fakePlayer);
        }
    }

    private void generateFakePlayer() {
        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYaw = mc.player.rotationYaw;
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.inventory.copyInventory(mc.player.inventory);

        mc.world.addEntityToWorld(fakePlayer.entityId, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            mc.player.noClip = false;
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.setFlySpeed(0.05f);
            mc.player.setPositionAndRotation(posX, posY, posZ, yaw, pitch);

            posX = posY = posZ = 0.0;
            pitch = yaw = 0.0f;

            mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0.0f;

            if (isRidingEntity) {
                mc.player.startRiding(ridingEntity, true);
            } else {
                ridingEntity = null;
            }
        } else {
            fakePlayer.noClip = false;
            fakePlayer.capabilities.isFlying = false;
            fakePlayer.capabilities.setFlySpeed(0.05f);

            mc.setRenderViewEntity(mc.player);
        }

        mc.world.removeEntityFromWorld(fakePlayer.entityId);

        fakePlayer = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            mc.player.noClip = true;
            mc.player.onGround = false;
            mc.player.fallDistance = 0;
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed(speed.getValue());
        } else {
            fakePlayer.noClip = true;
            fakePlayer.capabilities.isFlying = true;
            fakePlayer.capabilities.setFlySpeed(speed.getValue());
        }
    }

    @PaceHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            if (cancelPackets.getValue() && cPacketPlayer.getValue() && event.getPacket() instanceof CPacketPlayer) {
                event.setCancelled(true);
            } else if (cancelPackets.getValue() && cPacketInput.getValue() && event.getPacket() instanceof CPacketInput) {
                event.setCancelled(true);
            } else if (cancelPackets.getValue() && cPacketUseEntity.getValue() && event.getPacket() instanceof CPacketUseEntity) {
                event.setCancelled(true);
            } else if (cancelPackets.getValue() && cPacketPlayerTryUseItem.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItem) {
                event.setCancelled(true);
            } else if (cancelPackets.getValue() && cPacketPlayerTryUseItemOnBlock.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                event.setCancelled(true);
            } else if (cancelPackets.getValue() && cPacketVehicleMove.getValue() && event.getPacket() instanceof CPacketVehicleMove) {
                event.setCancelled(true);
            }
        }
    }

    @PaceHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            mc.player.noClip = true;
        } else {
            fakePlayer.noClip = true;
        }
    }

    @PaceHandler
    public void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        if (nullCheck()) return;

        if (mode.getValue() == FreecamModes.Normal) {
            mc.player.noClip = true;
        } else {
            fakePlayer.noClip = true;
        }
    }

    public enum FreecamModes {
        Normal,
        Camera
    }
}
package com.olliem5.ferox.api.util.packet;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

/**
 * @author linustouchtips
 * @author 086
 */

public final class RotationUtil implements Minecraft {
    public static void lockYaw(double rotation) {
        if (mc.player.rotationYaw >= rotation) {
            mc.player.rotationYaw = 0.0f;
        }

        if (mc.player.rotationYaw <= rotation) {
            mc.player.rotationYaw = 0.0f;
        }
    }

    public static void lockPitch(double rotation) {
        if (mc.player.rotationPitch >= rotation) {
            mc.player.rotationPitch = 0.0f;
        }

        if (mc.player.rotationPitch <= rotation) {
            mc.player.rotationPitch = 0.0f;
        }
    }

    public static void lookAtPacket(double px, double py, double pz, EntityPlayer entityPlayer) {
        double[] v = calculateLookAt(px, py, pz, entityPlayer);

        setYawAndPitch((float) v[0], (float) v[1]);
    }

    public static double yaw;
    public static double pitch;
    public static boolean isSpoofingAngles;

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    public static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    private static double[] calculateLookAt(double px, double py, double pz, EntityPlayer entityPlayer) {
        double dirx = entityPlayer.posX - px;
        double diry = entityPlayer.posY - py;
        double dirz = entityPlayer.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;

        yaw += 90.0;

        return new double[] {yaw, pitch};
    }

    public static String getFacing() {
        switch (MathHelper.floor(mc.player.rotationYaw + 8.0f / 360.0f + 0.5) & 7) {
            case 0:
            case 1:
                return " [" + ChatFormatting.WHITE + "+Z" + ChatFormatting.RESET + "]";
            case 2:
            case 3:
                return " [" + ChatFormatting.WHITE + "-X" + ChatFormatting.RESET + "]";
            case 4:
            case 5:
                return " [" + ChatFormatting.WHITE + "-Z" + ChatFormatting.RESET + "]";
            case 6:
            case 7:
                return " [" + ChatFormatting.WHITE + "+X" + ChatFormatting.RESET + "]";
        }

        return "Invalid Direction";
    }
}

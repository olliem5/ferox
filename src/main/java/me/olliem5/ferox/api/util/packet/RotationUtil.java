package me.olliem5.ferox.api.util.packet;

import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author linustouchtips
 */

public final class RotationUtil implements Minecraft {
    public static void lockYaw(double rotation) {
        if (mc.player.rotationYaw >= rotation) {
            mc.player.rotationYaw = 0;
        }

        if (mc.player.rotationYaw <= rotation) {
            mc.player.rotationYaw = 0;
        }
    }

    public static void lockPitch(double rotation) {
        if (mc.player.rotationPitch >= rotation) {
            mc.player.rotationPitch = 0;
        }

        if (mc.player.rotationPitch <= rotation) {
            mc.player.rotationPitch = 0;
        }
    }

    public static void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);

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

    private static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw, pitch};
    }
}

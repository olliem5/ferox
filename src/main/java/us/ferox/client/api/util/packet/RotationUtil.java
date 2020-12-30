package us.ferox.client.api.util.packet;

import us.ferox.client.api.traits.Minecraft;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class RotationUtil implements Minecraft {

    public static void lockYaw(double rotation) {
        if (mc.player.rotationYaw >= rotation)
            mc.player.rotationYaw = 0;

        if (mc.player.rotationYaw <= rotation)
            mc.player.rotationYaw = 0;
    }

    public static void lockPitch(double rotation) {
        if (mc.player.rotationPitch >= rotation)
            mc.player.rotationPitch = 0;

        if (mc.player.rotationPitch <= rotation)
            mc.player.rotationPitch = 0;
    }
}

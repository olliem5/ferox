package me.olliem5.ferox.api.util.module;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.player.MotionUtil;

/**
 * @author linustouchtips
 */

public final class ElytraUtil implements Minecraft {
    public static void accelerateElytra(double horizontal) {
        double yaw = MotionUtil.calcMoveYaw(mc.player.rotationYaw);
        double motX = 0.0;
        double motZ = 0.0;

        yaw -= mc.player.moveStrafing * 90.0;

        if (mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown()) {
            motX = (-Math.sin(yaw) * horizontal) * -1.0;
            motZ = (Math.cos(yaw) * horizontal) * -1.0;
        } else if (mc.gameSettings.keyBindForward.isKeyDown()) {
            motX = -Math.sin(yaw) * horizontal;
            motZ = Math.cos(yaw) * horizontal;
        }

        mc.player.motionX = motX;
        mc.player.motionZ = motZ;

        if (mc.player.moveStrafing == 0.0f && mc.player.moveForward == 0.0f) {
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
        }
    }

    public static void freezeElytra(double fallSpeed, double yOffset) {
        mc.player.motionX = 0.0;
        mc.player.motionY = 0.0;
        mc.player.motionZ = 0.0;

        mc.player.setVelocity(0.0, 0.0, 0.0);
        mc.player.setPosition(mc.player.posX, mc.player.posY - fallSpeed + yOffset, mc.player.posZ);
    }
}

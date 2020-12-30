package us.ferox.client.api.util.module;

import us.ferox.client.api.traits.Minecraft;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class MotionUtil implements Minecraft {
    static float roundedForward = getRoundedMovementInput(mc.player.movementInput.moveForward);
    static float roundedStrafing = getRoundedMovementInput(mc.player.movementInput.moveStrafe);

    public static boolean isMoving() {
        return (mc.player.moveForward != 0.0D || mc.player.moveStrafing != 0.0D);
    }

    public static boolean hasMotion() {
        return mc.player.motionX != 0.0 && mc.player.motionZ != 0.0 && mc.player.motionY != 0.0;
    }

    public static double calcMoveYaw(float yawIn) {
        float moveForward = roundedForward;
        float moveString = roundedStrafing;

        float strafe = 90 * moveString;
        if (moveForward != 0f)
            strafe *= moveForward * 0.5f;
        else
            strafe *= 1f;

        float yaw = yawIn - strafe;
        if (moveForward < 0f)
            yaw -= 180;
        else
            yaw -= 0;

        return Math.toRadians(yaw);
    }

    private static float getRoundedMovementInput(Float input) {
        if (input > 0)
            input = 1f;
        else if (input < 0)
            input = -1f;
        else
            input = 0f;

        return input;
    }
}

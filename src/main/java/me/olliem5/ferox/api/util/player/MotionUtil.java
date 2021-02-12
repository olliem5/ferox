package me.olliem5.ferox.api.util.player;

import me.olliem5.ferox.api.traits.Minecraft;

/**
 * @author olliem5
 * @author linustouchtips
 * @author Hoosiers
 */

public final class MotionUtil implements Minecraft {
    private static float roundedForward = getRoundedMovementInput(mc.player.movementInput.moveForward);
    private static float roundedStrafing = getRoundedMovementInput(mc.player.movementInput.moveStrafe);

    public static boolean isMoving() {
        return (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f);
    }

    public static double calcMoveYaw(float yawIn) {
        float moveForward = roundedForward;
        float moveString = roundedStrafing;

        float strafe = 90.0f * moveString;

        if (moveForward != 0.0f) {
            strafe *= moveForward * 0.5f;
        } else {
            strafe *= 1.0f;
        }

        float yaw = yawIn - strafe;
        if (moveForward < 0.0f) {
            yaw -= 180.0f;
        } else {
            yaw -= 0.0f;
        }

        return Math.toRadians(yaw);
    }

    private static float getRoundedMovementInput(float input) {
        if (input > 0.0f) {
            input = 1.0f;
        } else if (input < 0.0f) {
            input = -1.0f;
        } else {
            input = 0.0f;
        }

        return input;
    }
}

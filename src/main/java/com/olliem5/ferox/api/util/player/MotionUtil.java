package com.olliem5.ferox.api.util.player;

import com.olliem5.ferox.api.traits.Minecraft;

/**
 * @author olliem5
 * @author linustouchtips
 * @author Hoosiers
 * @author historian (I think)
 */

public final class MotionUtil implements Minecraft {
    private static final float roundedForward = getRoundedMovementInput(mc.player.movementInput.moveForward);
    private static final float roundedStrafing = getRoundedMovementInput(mc.player.movementInput.moveStrafe);

    public static boolean isMoving() {
        return (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f);
    }

    public static double calcMoveYaw(float yawIn) {
        float moveForward = roundedForward;

        float strafe = 90.0f * roundedStrafing;

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

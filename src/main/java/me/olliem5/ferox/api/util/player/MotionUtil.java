package me.olliem5.ferox.api.util.player;

import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.potion.Potion;

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

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;

        if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();

            baseSpeed *= 1.0 + 0.2 * (amplifier + 1.0);
        }

        return baseSpeed;
    }

    public static double[] forward(double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
            }

            side = 0.0f;

            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;

        return new double[] {posX, posZ};
    }
}

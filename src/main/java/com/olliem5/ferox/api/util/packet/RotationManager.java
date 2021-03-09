package com.olliem5.ferox.api.util.packet;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * @author olliem5
 */

public final class RotationManager implements Minecraft {
    private static float yaw;
    private static float pitch;

    public static void updatePlayerRotations() {
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
    }

    public static void setPlayerRotations(float yaw, float pitch) {
        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
    }

    public static void restorePlayerRotations() {
        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
    }

    public static void rotateToBlockPos(BlockPos blockPos) {
        float[] angle = calculateRotationAngle(RotationManager.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f));
        setPlayerRotations(angle[0], angle[1]);
    }

    public static void rotateToEntity(Entity entity) {
        float[] angle = calculateRotationAngle(RotationManager.mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionEyes(mc.getRenderPartialTicks()));
        setPlayerRotations(angle[0], angle[1]);
    }

    public static float[] calculateRotationAngle(Vec3d vec3d, Vec3d vec3d1) {
        double difX = vec3d1.x - vec3d.x;
        double difY = (vec3d1.y - vec3d.y) * -1.0;
        double difZ = vec3d1.z - vec3d.z;
        double dist = MathHelper.sqrt(difX * difX + difZ * difZ);

        return new float[] {(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))};
    }
}

package com.olliem5.ferox.api.util.packet;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
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

    public static void setPlayerRotations(float yaw, float pitch, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, mc.player.onGround));

            mc.player.renderYawOffset = yaw;
            mc.player.rotationYawHead = yaw;
        } else {
            mc.player.rotationYaw = yaw;
            mc.player.rotationPitch = pitch;
        }
    }

    public static void restorePlayerRotations() {
        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
    }

    public static void rotateToBlockPos(BlockPos blockPos, boolean packet) {
        float[] angle = calculateRotationAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((float) blockPos.getX() + 0.5f, (float) blockPos.getY() - 0.5f, (float) blockPos.getZ() + 0.5f));
        setPlayerRotations(angle[0], angle[1], packet);
    }

    public static void rotateToEntity(Entity entity, boolean packet) {
        float[] angle = calculateRotationAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
        setPlayerRotations(angle[0], angle[1], packet);
    }

    public static float[] calculateRotationAngle(Vec3d vec3d, Vec3d vec3d1) {
        double xDifference = vec3d1.x - vec3d.x;
        double yDifference = (vec3d1.y - vec3d.y) * -1.0;
        double zDifference = vec3d1.z - vec3d.z;
        double distance = MathHelper.sqrt(xDifference * xDifference + zDifference * zDifference);

        return new float[] {(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(zDifference, xDifference)) - 90.0), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(yDifference, distance)))};
    }
}

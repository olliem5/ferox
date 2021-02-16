package com.olliem5.ferox.api.util.world;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author olliem5
 */

public final class PlaceUtil implements Minecraft {
    public static void placeBlock(BlockPos blockPos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(blockPos)) {

                Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, blockPos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, blockPos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};

                mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec3d.z - mc.player.posZ), (vec3d.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec3d.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec3d.x - mc.player.posX) * (vec3d.x - mc.player.posX) + (vec3d.z - mc.player.posZ) * (vec3d.z - mc.player.posZ)))))), mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(blockPos), EnumHand.MAIN_HAND);

                mc.player.swingArm(EnumHand.MAIN_HAND);

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

                return;
            }
        }
    }

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }

        return false;
    }
}

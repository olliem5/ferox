package com.olliem5.ferox.api.util.world;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.packet.RotationManager;
import com.olliem5.ferox.api.util.packet.RotationUtil;
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
    public static void placeBlock(BlockPos blockPos, boolean rotate, boolean packetRotate) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(blockPos)) {
                if (rotate) {
                    RotationManager.rotateToBlockPos(blockPos, packetRotate);
                }

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(blockPos), EnumHand.MAIN_HAND);

                mc.player.swingArm(EnumHand.MAIN_HAND);

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

                return;
            }
        }
    }

    public static boolean isIntercepted(BlockPos blockPos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }

        return false;
    }
}

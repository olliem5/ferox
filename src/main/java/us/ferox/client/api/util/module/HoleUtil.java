package us.ferox.client.api.util.module;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import us.ferox.client.api.traits.Minecraft;

public class HoleUtil implements Minecraft {
    public static boolean isObsidianHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);

        return !(
                mc.world.getBlockState(boost).getBlock() != Blocks.AIR
                        || isBedrockHole(blockPos)
                        || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR
                        || mc.world.getBlockState(boost7).getBlock() != Blocks.AIR
                        || mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK
                        || mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK
                        || mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK
                        || mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK
                        || mc.world.getBlockState(boost8).getBlock() != Blocks.AIR
                        || mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK
        );
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);

        return mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost7).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK
                && mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK
                && mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK
                && mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK
                && mc.world.getBlockState(boost8).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK;
    }

    public static boolean isPlayerInHole(EntityPlayer entityPlayer) {
        Vec3d[] hole = {
                entityPlayer.getPositionVector().add(1.0D, 0.0D, 0.0D),
                entityPlayer.getPositionVector().add(-1.0D, 0.0D, 0.0D),
                entityPlayer.getPositionVector().add(0.0D, 0.0D, 1.0D),
                entityPlayer.getPositionVector().add(0.0D, 0.0D, -1.0D),
                entityPlayer.getPositionVector().add(0.0D, -1.0D, 0.0D)
        };

        int holeBlocks = 0;

        for (Vec3d vec3d : hole) {
            BlockPos offset = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

            if (mc.world.getBlockState(offset).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(offset).getBlock() == Blocks.BEDROCK) {
                ++holeBlocks;
            }

            if (holeBlocks == 5) {
                return true;
            }
        }
        return false;
    }
}

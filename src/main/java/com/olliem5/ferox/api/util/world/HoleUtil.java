package com.olliem5.ferox.api.util.world;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author olliem5
 */

public final class HoleUtil implements Minecraft {
    public static BlockPos[] holeOffsets = new BlockPos[] {
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -1),
            new BlockPos(0, -1, 0)
    };

    public static boolean isObsidianHole(BlockPos blockPos) {
        boolean isObsidianHole = true;

        for (BlockPos blockPos1 : holeOffsets) {
            Block block = mc.world.getBlockState(blockPos.add(blockPos1)).getBlock();

            if (block != Blocks.OBSIDIAN) {
                isObsidianHole = false;
            }
        }

        if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() != Blocks.AIR) {
            isObsidianHole = false;
        }

        return isObsidianHole;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        boolean isBedrockHole = true;

        for (BlockPos blockPos1 : holeOffsets) {
            Block block = mc.world.getBlockState(blockPos.add(blockPos1)).getBlock();

            if (block != Blocks.BEDROCK) {
                isBedrockHole = false;
            }
        }

        if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() != Blocks.AIR) {
            isBedrockHole = false;
        }

        return isBedrockHole;
    }

    public static boolean isEnderChestHole(BlockPos blockPos) {
        boolean isEnderChestHole = true;

        for (BlockPos blockPos1 : holeOffsets) {
            Block block = mc.world.getBlockState(blockPos.add(blockPos1)).getBlock();

            if (block != Blocks.ENDER_CHEST) {
                isEnderChestHole = false;
            }
        }

        if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() != Blocks.AIR) {
            isEnderChestHole = false;
        }

        return isEnderChestHole;
    }

    public static boolean isEnchantingTableHole(BlockPos blockPos) {
        boolean isEnchantingTableHole = true;

        for (BlockPos blockPos1 : holeOffsets) {
            Block block = mc.world.getBlockState(blockPos.add(blockPos1)).getBlock();

            if (block != Blocks.ENCHANTING_TABLE) {
                isEnchantingTableHole = false;
            }
        }

        if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() != Blocks.AIR) {
            isEnchantingTableHole = false;
        }

        return isEnchantingTableHole;
    }

    public static boolean isAnvilHole(BlockPos blockPos) {
        boolean isAnvilHole = true;

        for (BlockPos blockPos1 : holeOffsets) {
            Block block = mc.world.getBlockState(blockPos.add(blockPos1)).getBlock();

            if (block != Blocks.ANVIL) {
                isAnvilHole = false;
            }
        }

        if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() != Blocks.AIR) {
            isAnvilHole = false;
        }

        return isAnvilHole;
    }

    public static boolean isVoidHole(BlockPos blockPos) {
        return mc.player.dimension == -1 ? (blockPos.getY() == 0 || blockPos.getY() == 127) && mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR : blockPos.getY() == 0 && mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
    }

    public static boolean isPlayerInHole(EntityPlayer entityPlayer) {
        Vec3d[] hole = {
                entityPlayer.getPositionVector().add(1.0, 0.0, 0.0),
                entityPlayer.getPositionVector().add(-1.0, 0.0, 0.0),
                entityPlayer.getPositionVector().add(0.0, 0.0, 1.0),
                entityPlayer.getPositionVector().add(0.0, 0.0, -1.0),
                entityPlayer.getPositionVector().add(0.0, -1.0, 0.0)
        };

        int holeBlocks = 0;

        for (Vec3d vec3d : hole) {
            BlockPos offset = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

            if (mc.world.getBlockState(offset).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(offset).getBlock() == Blocks.BEDROCK) {
                holeBlocks++;
            }

            if (holeBlocks == 5) {
                return true;
            }
        }

        return false;
    }
}

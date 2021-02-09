package me.olliem5.ferox.api.util.world;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.math.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

/**
 * @author olliem5
 * @author linustouchtips
 */

public final class BlockUtil implements Minecraft {
    public static List<BlockPos> getSphere(BlockPos blockPos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<>();

        int cx = blockPos.getX();
        int cy = blockPos.getY();
        int cz = blockPos.getZ();
        int x = cx - (int) r;

        while ((float) x <= (float) cx + r) {
            int z = cz - (int) r;

            while ((float) z <= (float) cz + r) {
                int y = sphere ? cy - (int) r : cy;

                do {
                    float f = sphere ? (float) cy + r : (float) (cy + h);

                    if (!((float) y < f)) break;

                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);

                    if (!(!(dist < (double) (r * r)) || hollow && dist < (double) ((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }

                    ++y;

                } while (true);

                ++z;
            }

            ++x;
        }

        return circleblocks;
    }

    public static List<BlockPos> getNearbyBlocks(EntityPlayer entityPlayer, double blockRange, boolean motion) {
        List<BlockPos> nearbyBlocks = new ArrayList<>();

        int range = (int) MathUtil.roundNumber(blockRange, 0);

        if (motion) {
            entityPlayer.getPosition().add(new Vec3i(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ));
        }

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    nearbyBlocks.add(entityPlayer.getPosition().add(x, y, z));
                }
            }
        }

        return nearbyBlocks;
    }

    @SuppressWarnings("deprecation")
    public static BlockResistance getBlockResistance(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.WATER) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.LAVA)) {
            return BlockResistance.Blank;
        }

        else if (mc.world.getBlockState(blockPos).getBlock().getBlockHardness(mc.world.getBlockState(blockPos), mc.world, blockPos) != -1) {
            return BlockResistance.Breakable;
        }

        else if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ANVIL) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ENCHANTING_TABLE) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Resistant;
        }

        else if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK)) {
            return BlockResistance.Unbreakable;
        }

        return null;
    }

    public enum BlockResistance {
        Blank,
        Breakable,
        Resistant,
        Unbreakable
    }
}

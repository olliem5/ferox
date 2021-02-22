package com.olliem5.ferox.api.util.world;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.math.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

/**
 * @author olliem5
 * @author linustouchtips
 */

public final class BlockUtil implements Minecraft {
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
}

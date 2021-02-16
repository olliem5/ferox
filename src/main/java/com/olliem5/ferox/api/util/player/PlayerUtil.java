package com.olliem5.ferox.api.util.player;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author olliem5
 */

public final class PlayerUtil implements Minecraft {
    public static boolean isInViewFrustrum(BlockPos blockPos) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), false, true, false) == null;
    }

    public static boolean blockIntersectsPlayer(BlockPos blockPos) {
        return new AxisAlignedBB(blockPos).intersects(mc.player.getEntityBoundingBox());
    }

    public static Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }
}

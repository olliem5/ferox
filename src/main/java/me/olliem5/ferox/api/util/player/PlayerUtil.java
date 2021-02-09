package me.olliem5.ferox.api.util.player;

import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

/**
 * @author olliem5
 */

public final class PlayerUtil implements Minecraft {
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static boolean isMoving(EntityPlayer entityPlayer) {
        return entityPlayer.moveForward != 0 || entityPlayer.moveStrafing != 0;
    }

    public static void setSpeed(EntityPlayer entityPlayer, double speed) {
        double[] dir = MotionUtil.forward(speed);

        entityPlayer.motionX = dir[0];
        entityPlayer.motionZ = dir[1];
    }
}

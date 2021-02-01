package me.olliem5.ferox.api.util.minecraft;

import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil implements Minecraft {
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}

package us.ferox.client.api.util.minecraft;

import net.minecraft.util.math.BlockPos;
import us.ferox.client.api.traits.Minecraft;

public class PlayerUtil implements Minecraft {
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}

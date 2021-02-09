package me.olliem5.ferox.api.util.player;

import me.olliem5.ferox.api.friend.FriendManager;
import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author olliem5
 */

public final class TargetUtil implements Minecraft {
    public static EntityPlayer getClosestPlayer(double range) {
        if (mc.world.getLoadedEntityList().size() == 0) return null;

        EntityPlayer closestPlayer = mc.world.playerEntities.stream()
                .filter(entityPlayer -> entityPlayer != mc.player)
                .filter(entityPlayer -> !entityPlayer.isDead)
                .filter(entityPlayer -> mc.player.getDistance(entityPlayer) <= range)
                .filter(entityPlayer -> !FriendManager.isFriend(entityPlayer.getName()))
                .findFirst()
                .orElse(null);

        return closestPlayer;
    }
}

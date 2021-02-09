package me.olliem5.ferox.impl.modules.movement.speed;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.events.PlayerMoveEvent;

/**
 * @author olliem5
 */

public abstract class SpeedMode implements Minecraft {
    public abstract void onUpdate();

    public void onPlayerMove(PlayerMoveEvent event) {}

    public boolean shouldSpeedNotRun() {
        return (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInWater() || mc.player.isInWater() || mc.player.capabilities.isFlying || mc.player.isElytraFlying());
    }
}


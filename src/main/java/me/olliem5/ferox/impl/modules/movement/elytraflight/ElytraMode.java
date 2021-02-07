package me.olliem5.ferox.impl.modules.movement.elytraflight;

import me.olliem5.ferox.api.traits.Minecraft;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public abstract class ElytraMode implements Minecraft {
    public void onHorizontalMovement() {}

    public void onVerticalMovement() {}

    public void onAscendingMovement() {}

    public void noMovement() {}

    public void onRotation() {}
}

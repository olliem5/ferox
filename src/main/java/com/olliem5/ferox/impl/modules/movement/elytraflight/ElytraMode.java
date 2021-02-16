package com.olliem5.ferox.impl.modules.movement.elytraflight;

import com.olliem5.ferox.api.traits.Minecraft;

/**
 * @author linustouchtips
 */

public abstract class ElytraMode implements Minecraft {
    public void onHorizontalMovement() {}

    public void onVerticalMovement() {}

    public void onAscendingMovement() {}

    public void noMovement() {}

    public void onRotation() {}
}

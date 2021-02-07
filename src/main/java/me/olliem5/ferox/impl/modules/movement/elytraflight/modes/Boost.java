package me.olliem5.ferox.impl.modules.movement.elytraflight.modes;

import me.olliem5.ferox.api.util.module.ElytraUtil;
import me.olliem5.ferox.api.util.packet.RotationUtil;
import me.olliem5.ferox.impl.modules.movement.ElytraFlight;
import me.olliem5.ferox.impl.modules.movement.elytraflight.ElytraMode;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public final class Boost extends ElytraMode {

    @Override
    public void onHorizontalMovement() {
        ElytraUtil.accelerateElytra(ElytraFlight.horizontalSpeed.getValue());
    }

    @Override
    public void noMovement() {
        ElytraUtil.freezeElytra(0, ElytraFlight.yOffset.getValue());
    }

    @Override
    public void onRotation() {
        if (ElytraFlight.lockRotation.getValue()) {
            RotationUtil.lockPitch(ElytraFlight.ncpRotations.getValue());
            RotationUtil.lockYaw(ElytraFlight.ncpRotations.getValue());
        }
    }
}

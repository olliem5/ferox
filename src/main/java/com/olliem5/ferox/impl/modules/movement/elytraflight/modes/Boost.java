package com.olliem5.ferox.impl.modules.movement.elytraflight.modes;

import com.olliem5.ferox.api.util.module.ElytraUtil;
import com.olliem5.ferox.api.util.packet.RotationUtil;
import com.olliem5.ferox.impl.modules.movement.ElytraFlight;
import com.olliem5.ferox.impl.modules.movement.elytraflight.ElytraMode;

/**
 * @author linustouchtips
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

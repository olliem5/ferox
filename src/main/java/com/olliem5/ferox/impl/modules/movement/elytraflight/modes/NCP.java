package com.olliem5.ferox.impl.modules.movement.elytraflight.modes;

import com.olliem5.ferox.impl.modules.movement.ElytraFlight;
import com.olliem5.ferox.impl.modules.movement.elytraflight.ElytraMode;
import com.olliem5.ferox.api.util.module.ElytraUtil;
import com.olliem5.ferox.api.util.packet.RotationUtil;

/**
 * @author linustouchtips
 */

public final class NCP extends ElytraMode {
    @Override
    public void onVerticalMovement() {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.rotationPitch = ElytraFlight.ncpRotations.getValue() * -1;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.rotationPitch = ElytraFlight.ncpRotations.getValue();
        }
    }

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
        RotationUtil.lockPitch(ElytraFlight.ncpRotations.getValue());

        if (ElytraFlight.lockRotation.getValue()) {
            RotationUtil.lockYaw(ElytraFlight.ncpRotations.getValue());
        }
    }
}

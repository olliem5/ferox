package me.olliem5.ferox.impl.modules.movement.elytra.mode;

import me.olliem5.ferox.impl.modules.movement.elytra.ElytraMode;
import me.olliem5.ferox.api.util.module.ElytraUtil;
import me.olliem5.ferox.api.util.packet.RotationUtil;
import me.olliem5.ferox.impl.modules.movement.ElytraFlight;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class NCP extends ElytraMode {

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

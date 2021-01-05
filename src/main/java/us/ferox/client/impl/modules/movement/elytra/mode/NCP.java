package us.ferox.client.impl.modules.movement.elytra.mode;

import us.ferox.client.api.util.module.ElytraUtil;
import us.ferox.client.api.util.packet.RotationUtil;
import us.ferox.client.impl.modules.movement.ElytraFlight;
import us.ferox.client.impl.modules.movement.elytra.ElytraMode;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class NCP extends ElytraMode {

    @Override
    public void onVerticalMovement() {
        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.player.rotationPitch = ElytraFlight.ncpRotations.getValue().floatValue() * -1;

        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.player.rotationPitch = ElytraFlight.ncpRotations.getValue().floatValue();
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

        if (ElytraFlight.lockRotation.getValue())
            RotationUtil.lockYaw(ElytraFlight.ncpRotations.getValue());
    }
}

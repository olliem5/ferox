package us.ferox.client.impl.modules.movement.elytra.mode;

import us.ferox.client.api.util.module.ElytraUtil;
import us.ferox.client.api.util.packet.RotationUtil;
import us.ferox.client.impl.modules.movement.ElytraFlight;
import us.ferox.client.impl.modules.movement.elytra.ElytraMode;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class Boost extends ElytraMode {

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

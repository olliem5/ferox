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
            mc.player.rotationPitch = (float) ElytraFlight.ncpRotations.getValue() * -1;

        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.player.rotationPitch = (float) ElytraFlight.ncpRotations.getValue();
    }

    @Override
    public void onHorizontalMovement() {
        ElytraUtil.accelerateElytra((double) ElytraFlight.horizontalSpeed.getValue());
    }

    @Override
    public void noMovement() {
        ElytraUtil.freezeElytra(0, (double) ElytraFlight.yOffset.getValue());
    }

    @Override
    public void onRotation() {
        RotationUtil.lockPitch((double) ElytraFlight.ncpRotations.getValue());

        if (ElytraFlight.lockRotation.getValue())
            RotationUtil.lockYaw((double) ElytraFlight.ncpRotations.getValue());
    }
}

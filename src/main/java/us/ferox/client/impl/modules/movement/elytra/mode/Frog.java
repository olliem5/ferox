package us.ferox.client.impl.modules.movement.elytra.mode;

import us.ferox.client.api.util.module.ElytraUtil;
import us.ferox.client.api.util.packet.RotationUtil;
import us.ferox.client.impl.modules.movement.ElytraFlight;
import us.ferox.client.impl.modules.movement.elytra.ElytraMode;

public class Frog extends ElytraMode {

    @Override
    public void onVerticalMovement() {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            ElytraUtil.accelerateElytra((double) ElytraFlight.horizontalSpeed.getValue());
            mc.player.motionY = ((double) ElytraFlight.verticalSpeed.getValue());
        }

        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.player.motionY = ((double) ElytraFlight.verticalSpeed.getValue() * -1);
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
        if (ElytraFlight.lockRotation.getValue()) {
            RotationUtil.lockPitch((double) ElytraFlight.ncpRotations.getValue());
            RotationUtil.lockYaw((double) ElytraFlight.ncpRotations.getValue());
        }
    }
}

package us.ferox.client.impl.modules.movement.elytra.mode;

import us.ferox.client.api.util.minecraft.PlayerUtil;
import us.ferox.client.api.util.packet.RotationUtil;
import us.ferox.client.impl.modules.movement.ElytraFlight;
import us.ferox.client.impl.modules.movement.elytra.ElytraMode;

public class Frog extends ElytraMode {

    @Override
    public void onVerticalMovement() {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            PlayerUtil.ElytraUtil.accelerateElytra(ElytraFlight.horizontalSpeed.getValue());
            mc.player.motionY = ElytraFlight.verticalSpeed.getValue();
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = (ElytraFlight.verticalSpeed.getValue() * -1);
        }
    }

    @Override
    public void onHorizontalMovement() {
        PlayerUtil.ElytraUtil.accelerateElytra(ElytraFlight.horizontalSpeed.getValue());
    }

    @Override
    public void noMovement() {
        PlayerUtil.ElytraUtil.freezeElytra(0, ElytraFlight.yOffset.getValue());
    }

    @Override
    public void onRotation() {
        if (ElytraFlight.lockRotation.getValue()) {
            RotationUtil.lockPitch(ElytraFlight.ncpRotations.getValue());
            RotationUtil.lockYaw(ElytraFlight.ncpRotations.getValue());
        }
    }
}

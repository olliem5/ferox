package com.olliem5.ferox.impl.modules.movement.elytraflight.modes;

import com.olliem5.ferox.impl.modules.movement.ElytraFlight;
import com.olliem5.ferox.impl.modules.movement.elytraflight.ElytraMode;
import com.olliem5.ferox.api.util.module.ElytraUtil;
import com.olliem5.ferox.api.util.packet.RotationUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

/**
 * @author linustouchtips
 */

public final class Firework extends ElytraMode {
    @Override
    public void onVerticalMovement() {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            InventoryUtil.switchToSlot(Items.FIREWORKS);
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = (ElytraFlight.verticalSpeed.getValue() * -1);
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

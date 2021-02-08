package me.olliem5.ferox.impl.modules.movement.speed.modes;

import me.olliem5.ferox.api.util.player.MotionUtil;
import me.olliem5.ferox.api.util.player.PlayerUtil;
import me.olliem5.ferox.impl.modules.movement.Speed;
import me.olliem5.ferox.impl.modules.movement.speed.SpeedMode;

public final class OnGround extends SpeedMode {
    @Override
    public void onUpdate() {
        if (!PlayerUtil.isMoving(mc.player) || shouldSpeedNotRun()) return;

        if (mc.player.onGround) {
            if (mc.player.ticksExisted % 2 == 0) {
                PlayerUtil.setSpeed(mc.player, MotionUtil.getBaseMoveSpeed() - Speed.onGroundSpeedOne.getValue());
            } else {
                PlayerUtil.setSpeed(mc.player, MotionUtil.getBaseMoveSpeed() + Speed.onGroundSpeedTwo.getValue());
            }
        } else {
            mc.player.motionY = -1;
        }
    }
}

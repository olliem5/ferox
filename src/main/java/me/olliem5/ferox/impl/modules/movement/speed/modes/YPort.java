package me.olliem5.ferox.impl.modules.movement.speed.modes;

import me.olliem5.ferox.api.util.minecraft.MotionUtil;
import me.olliem5.ferox.api.util.minecraft.PlayerUtil;
import me.olliem5.ferox.api.util.minecraft.TimerUtil;
import me.olliem5.ferox.impl.modules.movement.Speed;
import me.olliem5.ferox.impl.modules.movement.speed.SpeedMode;

public final class YPort extends SpeedMode {
    @Override
    public void onUpdate() {
        if (!PlayerUtil.isMoving(mc.player) || shouldSpeedNotRun()) return;

        if (mc.player.onGround) {
            TimerUtil.setTimer(1.15f);

            mc.player.jump();

            PlayerUtil.setSpeed(mc.player, MotionUtil.getBaseMoveSpeed() + Speed.yPortSpeed.getValue());
        } else {
            mc.player.motionY = -1;

            TimerUtil.resetTimer();
        }
    }
}

package me.olliem5.ferox.api.util.world;

import me.olliem5.ferox.api.traits.Minecraft;

public final class TimerUtil implements Minecraft {
    public static void setTimer(float speed) {
        mc.timer.tickLength = 50.0f / speed;
    }

    public static void resetTimer() {
        mc.timer.tickLength = 50;
    }
}

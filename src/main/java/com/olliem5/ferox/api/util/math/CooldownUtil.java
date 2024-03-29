package com.olliem5.ferox.api.util.math;

/**
 * @author olliem5
 */

public final class CooldownUtil {
    private long time;

    public CooldownUtil() {
        time = -1;
    }

    public boolean passed(double ms) {
        return System.currentTimeMillis() - this.time >= ms;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

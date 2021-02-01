package me.olliem5.ferox.api.util.colour;

import java.awt.*;

public class RainbowUtil {
    public static Color getRainbow() {
        return Color.getHSBColor((float) (System.currentTimeMillis() % 7500L) / 7500f, 0.8f, 0.8f);
    }

    public static int getRollingRainbow(long offset) {
        float hue = ((System.currentTimeMillis() + (offset * 500)) % (90000L)) / (30000.0f);

        return Color.HSBtoRGB(hue, 0.85f, 0.85f);
    }
}
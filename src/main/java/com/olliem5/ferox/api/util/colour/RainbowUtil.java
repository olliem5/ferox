package com.olliem5.ferox.api.util.colour;

import java.awt.*;

/**
 * @author olliem5
 */

public final class RainbowUtil {
    public static Color getRainbow() {
        return Color.getHSBColor((float) (System.currentTimeMillis() % 7500L) / 7500f, 0.85f, 0.85f);
    }
}
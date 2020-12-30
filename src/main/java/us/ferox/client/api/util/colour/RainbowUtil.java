package us.ferox.client.api.util.colour;

import java.awt.*;

public class RainbowUtil {
    public static Color getRainbow() {
        return Color.getHSBColor((float) (System.currentTimeMillis() % 7500L) / 7500f, 0.8f, 0.8f);
    }
}
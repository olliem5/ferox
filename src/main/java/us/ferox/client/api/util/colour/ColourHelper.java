package us.ferox.client.api.util.colour;

import java.awt.*;

public class ColourHelper {
    public static Color integrateAlpha(Color color, float alpha) {
        float red = (float) color.getRed() / 255;
        float green = (float) color.getGreen() / 255;
        float blue = (float) color.getBlue() / 255;

        return new Color(red, green, blue, alpha);
    }
}

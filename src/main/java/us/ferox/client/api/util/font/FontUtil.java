package us.ferox.client.api.util.font;

import us.ferox.client.Ferox;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.impl.modules.ferox.Font;

public class FontUtil implements Minecraft {
    public static void drawString(String text, float x, float y, int colour) {
        switch ((Font.Fonts) Font.font.getValue()) {
            case Lato:
                Ferox.latoFont.drawString(text, x, y, colour);
                break;
            case Ubuntu:
                Ferox.ubuntuFont.drawString(text, x, y, colour);
                break;
            case Verdana:
                Ferox.verdanaFont.drawString(text, x, y, colour);
                break;
            case Comfortaa:
                Ferox.comfortaaFont.drawString(text, x, y, colour);
                break;
            case Subtitle:
                Ferox.subtitleFont.drawString(text, x, y, colour);
                break;
            case Minecraft:
                mc.fontRenderer.drawString(text, (int) x, (int) y, colour);
                break;
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, int colour) {
        switch ((Font.Fonts) Font.font.getValue()) {
            case Lato:
                Ferox.latoFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Ubuntu:
                Ferox.ubuntuFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Verdana:
                Ferox.verdanaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Comfortaa:
                Ferox.comfortaaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Subtitle:
                Ferox.subtitleFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Minecraft:
                mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, colour);
                break;
        }
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (Font.shadow.getValue()) {
            drawStringWithShadow(Font.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
        } else {
            drawString(Font.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
        }
    }

    public static float getStringWidth(String text) {
        switch (Font.font.getValue()) {
            case Lato:
                return Ferox.latoFont.getStringWidth(text);
            case Ubuntu:
                return Ferox.ubuntuFont.getStringWidth(text);
            case Verdana:
                return Ferox.verdanaFont.getStringWidth(text);
            case Comfortaa:
                return Ferox.comfortaaFont.getStringWidth(text);
            case Subtitle:
                return Ferox.subtitleFont.getStringWidth(text);
            case Minecraft:
                return mc.fontRenderer.getStringWidth(text);
        }
        return -1;
    }

    public static float getStringHeight(String text) {
        switch ((Font.Fonts) Font.font.getValue()) {
            case Lato:
                return Ferox.latoFont.getStringHeight(text);
            case Ubuntu:
                return Ferox.ubuntuFont.getStringHeight(text);
            case Verdana:
                return Ferox.verdanaFont.getStringHeight(text);
            case Comfortaa:
                return Ferox.comfortaaFont.getStringHeight(text);
            case Subtitle:
                return Ferox.subtitleFont.getStringHeight(text);
            case Minecraft:
                return mc.fontRenderer.FONT_HEIGHT;
        }
        return -1;
    }
}

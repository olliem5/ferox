package com.olliem5.ferox.api.util.render.font;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.ferox.Font;

/**
 * @author olliem5
 */

public final class FontUtil implements Minecraft {
    public static final FeroxFontRenderer latoFont = new FeroxFontRenderer("Lato", 17.0f);
    public static final FeroxFontRenderer ubuntuFont = new FeroxFontRenderer("Ubuntu", 17.0f);
    public static final FeroxFontRenderer verdanaFont = new FeroxFontRenderer("Verdana", 17.0f);
    public static final FeroxFontRenderer comfortaaFont = new FeroxFontRenderer("Comfortaa", 17.0f);
    public static final FeroxFontRenderer subtitleFont = new FeroxFontRenderer("Subtitle", 17.0f);

    public static void drawString(String text, float x, float y, int colour) {
        switch (Font.font.getValue()) {
            case Ubuntu:
                ubuntuFont.drawString(text, x, y, colour);
                break;
            case Lato:
                latoFont.drawString(text, x, y, colour);
                break;
            case Verdana:
                verdanaFont.drawString(text, x, y, colour);
                break;
            case Comfortaa:
                comfortaaFont.drawString(text, x, y, colour);
                break;
            case Subtitle:
                subtitleFont.drawString(text, x, y, colour);
                break;
            case Minecraft:
                mc.fontRenderer.drawString(text, (int) x, (int) y, colour);
                break;
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, int colour) {
        switch (Font.font.getValue()) {
            case Ubuntu:
                ubuntuFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Lato:
                latoFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Verdana:
                verdanaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Comfortaa:
                comfortaaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Subtitle:
                subtitleFont.drawStringWithShadow(text, x, y, colour);
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
            case Ubuntu:
                return ubuntuFont.getStringWidth(text);
            case Lato:
                return latoFont.getStringWidth(text);
            case Verdana:
                return verdanaFont.getStringWidth(text);
            case Comfortaa:
                return comfortaaFont.getStringWidth(text);
            case Subtitle:
                return subtitleFont.getStringWidth(text);
            case Minecraft:
                return mc.fontRenderer.getStringWidth(text);
        }

        return -1;
    }

    public static float getStringHeight(String text) {
        switch (Font.font.getValue()) {
            case Ubuntu:
                return ubuntuFont.getStringHeight(text);
            case Lato:
                return latoFont.getStringHeight(text);
            case Verdana:
                return verdanaFont.getStringHeight(text);
            case Comfortaa:
                return comfortaaFont.getStringHeight(text);
            case Subtitle:
                return subtitleFont.getStringHeight(text);
            case Minecraft:
                return mc.fontRenderer.FONT_HEIGHT;
        }

        return -1;
    }
}

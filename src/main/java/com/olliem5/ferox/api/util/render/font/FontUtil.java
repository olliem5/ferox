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
            case Font.FontModes.Ubuntu:
                ubuntuFont.drawString(text, x, y, colour);
                break;
            case Font.FontModes.Lato:
                latoFont.drawString(text, x, y, colour);
                break;
            case Font.FontModes.Verdana:
                verdanaFont.drawString(text, x, y, colour);
                break;
            case Font.FontModes.Comfortaa:
                comfortaaFont.drawString(text, x, y, colour);
                break;
            case Font.FontModes.Subtitle:
                subtitleFont.drawString(text, x, y, colour);
                break;
            case Font.FontModes.Minecraft:
                mc.fontRenderer.drawString(text, (int) x, (int) y, colour);
                break;
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, int colour) {
        switch (Font.font.getValue()) {
            case Font.FontModes.Ubuntu:
                ubuntuFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Font.FontModes.Lato:
                latoFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Font.FontModes.Verdana:
                verdanaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Font.FontModes.Comfortaa:
                comfortaaFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Font.FontModes.Subtitle:
                subtitleFont.drawStringWithShadow(text, x, y, colour);
                break;
            case Font.FontModes.Minecraft:
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
            case Font.FontModes.Ubuntu:
                return ubuntuFont.getStringWidth(text);
            case Font.FontModes.Lato:
                return latoFont.getStringWidth(text);
            case Font.FontModes.Verdana:
                return verdanaFont.getStringWidth(text);
            case Font.FontModes.Comfortaa:
                return comfortaaFont.getStringWidth(text);
            case Font.FontModes.Subtitle:
                return subtitleFont.getStringWidth(text);
            case Font.FontModes.Minecraft:
                return mc.fontRenderer.getStringWidth(text);
        }

        return -1;
    }

    public static float getStringHeight(String text) {
        switch (Font.font.getValue()) {
            case Font.FontModes.Ubuntu:
                return ubuntuFont.getStringHeight(text);
            case Font.FontModes.Lato:
                return latoFont.getStringHeight(text);
            case Font.FontModes.Verdana:
                return verdanaFont.getStringHeight(text);
            case Font.FontModes.Comfortaa:
                return comfortaaFont.getStringHeight(text);
            case Font.FontModes.Subtitle:
                return subtitleFont.getStringHeight(text);
            case Font.FontModes.Minecraft:
                return mc.fontRenderer.FONT_HEIGHT;
        }

        return -1;
    }
}

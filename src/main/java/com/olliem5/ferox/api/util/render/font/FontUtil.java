package com.olliem5.ferox.api.util.render.font;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.ferox.ClientFont;

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
        switch (ClientFont.font.getValue()) {
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
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, int colour) {
        switch (ClientFont.font.getValue()) {
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
        }
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (ClientFont.shadow.getValue()) {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            }
        } else {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, colour);
            }
        }
    }

    public static float getStringWidth(String text) {
        switch (ClientFont.font.getValue()) {
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
        }

        return -1;
    }

    public static float getStringHeight(String text) {
        switch (ClientFont.font.getValue()) {
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
        }

        return -1;
    }
}

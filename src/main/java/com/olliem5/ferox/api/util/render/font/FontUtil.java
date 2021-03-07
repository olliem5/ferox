package com.olliem5.ferox.api.util.render.font;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.ferox.ClientFont;

/**
 * @author olliem5
 */

public final class FontUtil implements Minecraft {
    public static final FeroxFontRenderer ubuntuFont = new FeroxFontRenderer("Ubuntu", 17.0f);
    public static final FeroxFontRenderer latoFont = new FeroxFontRenderer("Lato", 17.0f);
    public static final FeroxFontRenderer verdanaFont = new FeroxFontRenderer("Verdana", 17.0f);
    public static final FeroxFontRenderer comfortaaFont = new FeroxFontRenderer("Comfortaa", 17.0f);
    public static final FeroxFontRenderer subtitleFont = new FeroxFontRenderer("Subtitle", 17.0f);
    public static final FeroxFontRenderer comicSansFont = new FeroxFontRenderer("ComicSans", 17.0f);

    public static FeroxFontRenderer getCurrentCustomFont() {
        switch (ClientFont.font.getValue()) {
            case Ubuntu:
                return ubuntuFont;
            case Lato:
                return latoFont;
            case Verdana:
                return verdanaFont;
            case Comfortaa:
                return comfortaaFont;
            case Subtitle:
                return subtitleFont;
            case ComicSans:
                return comicSansFont;
        }

        return ubuntuFont;
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (ClientFont.shadow.getValue()) {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            }
        } else {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, colour);
            }
        }
    }

    public static float getStringWidth(String text) {
        return getCurrentCustomFont().getStringWidth(text);
    }

    public static float getStringHeight(String text) {
        return getCurrentCustomFont().getStringHeight(text);
    }
}

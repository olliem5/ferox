package com.olliem5.ferox.api.util.render.font;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.font.renderer.CustomFontRenderer;
import com.olliem5.ferox.api.util.render.font.renderer.FeroxFontRenderer;
import com.olliem5.ferox.impl.modules.ferox.ClientFont;

import java.awt.*;
import java.io.InputStream;

/**
 * @author olliem5
 * @author linustouchtips
 */

//public final class FontUtil implements Minecraft {
//    public static CustomFontRenderer ubuntuFont = null;
//    public static CustomFontRenderer latoFont = null;
//    public static CustomFontRenderer verdanaFont = null;
//    public static CustomFontRenderer comfortaaFont = null;
//    public static CustomFontRenderer subtitleFont = null;
//
//    public static void loadFonts() {
//        try {
//            ubuntuFont = new CustomFontRenderer(getFont("Ubuntu.ttf", 35.0f));
//            latoFont = new CustomFontRenderer(getFont("Lato.ttf", 35.0f));
//            verdanaFont = new CustomFontRenderer(getFont("Verdana.ttf", 35.0f));
//            comfortaaFont = new CustomFontRenderer(getFont("Comfortaa.ttf", 35.0f));
//            subtitleFont = new CustomFontRenderer(getFont("Subtitle.ttf", 35.0f));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    private static Font getFont(String fontName, float size) {
//        try {
//            InputStream inputStream = FontUtil.class.getResourceAsStream("/assets/ferox/fonts/" + fontName);
//
//            Font awtClientFont = Font.createFont(0, inputStream);
//            awtClientFont = awtClientFont.deriveFont(0, size);
//
//            inputStream.close();
//
//            return awtClientFont;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Font("default", 0, (int) size);
//        }
//    }
//
//    public static void drawString(String text, int x, int y, int colour) {
//        switch (ClientFont.font.getValue()) {
//            case Ubuntu:
//                ubuntuFont.drawString(text, x, y, colour);
//                break;
//            case Lato:
//                latoFont.drawString(text, x, y, colour);
//                break;
//            case Verdana:
//                verdanaFont.drawString(text, x, y, colour);
//                break;
//            case Comfortaa:
//                comfortaaFont.drawString(text, x, y, colour);
//                break;
//            case Subtitle:
//                subtitleFont.drawString(text, x, y, colour);
//                break;
//            case Minecraft:
//                mc.fontRenderer.drawString(text, x, y, colour);
//                break;
//        }
//    }
//
//    public static void drawStringWithShadow(String text, float x, float y, int colour) {
//        switch (ClientFont.font.getValue()) {
//            case Ubuntu:
//                ubuntuFont.drawStringWithShadow(text, x, y, colour);
//                break;
//            case Lato:
//                latoFont.drawStringWithShadow(text, x, y, colour);
//                break;
//            case Verdana:
//                verdanaFont.drawStringWithShadow(text, x, y, colour);
//                break;
//            case Comfortaa:
//                comfortaaFont.drawStringWithShadow(text, x, y, colour);
//                break;
//            case Subtitle:
//                subtitleFont.drawStringWithShadow(text, x, y, colour);
//                break;
//            case Minecraft:
//                mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, colour);
//                break;
//        }
//    }
//
//    public static void drawText(String text, float x, float y, int colour) {
//        if (ClientFont.shadow.getValue()) {
//            drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
//        } else {
//            drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, colour);
//        }
//    }
//
//    public static float getStringWidth(String text) {
//        switch (ClientFont.font.getValue()) {
//            case Ubuntu:
//                return ubuntuFont.getStringWidth(text);
//            case Lato:
//                return latoFont.getStringWidth(text);
//            case Verdana:
//                return verdanaFont.getStringWidth(text);
//            case Comfortaa:
//                return comfortaaFont.getStringWidth(text);
//            case Subtitle:
//                return subtitleFont.getStringWidth(text);
//            case Minecraft:
//                return mc.fontRenderer.getStringWidth(text);
//        }
//
//        return -1;
//    }
//
//    public static float getFontHeight() {
//        switch (ClientFont.font.getValue()) {
//            case Ubuntu:
//                return ubuntuFont.getHeight();
//            case Lato:
//                return latoFont.getHeight();
//            case Verdana:
//                return verdanaFont.getHeight();
//            case Comfortaa:
//                return comfortaaFont.getHeight();
//            case Subtitle:
//                return subtitleFont.getHeight();
//            case Minecraft:
//                return mc.fontRenderer.FONT_HEIGHT;
//        }
//
//        return -1;
//    }
//}

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
            case Minecraft:
                mc.fontRenderer.drawString(text, (int) x, (int) y, colour);
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
            case Minecraft:
                mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, colour);
                break;
        }
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (ClientFont.shadow.getValue()) {
            drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
        } else {
            drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, colour);
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
            case Minecraft:
                return mc.fontRenderer.getStringWidth(text);
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
            case Minecraft:
                return mc.fontRenderer.FONT_HEIGHT;
        }

        return -1;
    }
}

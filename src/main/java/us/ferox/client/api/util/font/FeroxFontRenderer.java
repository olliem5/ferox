package us.ferox.client.api.util.font;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import us.ferox.client.api.traits.Minecraft;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FeroxFontRenderer implements Minecraft {
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile(StringUtil.COLOR_CHAR + "[0123456789abcdefklmnor]");
    private final int[] colorCodes = {0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
    private final Map<String, Float> cachedStringWidth = new HashMap<>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor;
    private String name;
    private float size;

    public FeroxFontRenderer(String fontName, float fontSize) {
        name = fontName;
        size = fontSize;
        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            prevScaleFactor = resolution.getScaleFactor();
            unicodeFont = new UnicodeFont(getFontByName(fontName).deriveFont(fontSize * prevScaleFactor / 2));
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();

            prevScaleFactor = resolution.getScaleFactor();

            try {
                unicodeFont = new UnicodeFont(getFontByName("Ubuntu").deriveFont(fontSize * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public static Font getFontByName(String name) throws IOException, FontFormatException {
         if (name == "Ubuntu") {
            return getFontFromInput("/assets/ferox/fonts/Ubuntu.ttf");
         } else if (name == "Lato") {
             return getFontFromInput("/assets/ferox/fonts/Lato.ttf");
         } else if (name == "Verdana") {
             return getFontFromInput("/assets/ferox/fonts/Verdana.ttf");
         } else if (name == "Comfortaa") {
             return getFontFromInput("/assets/ferox/fonts/Comfortaa.ttf");
         } else if (name == "Subtitle") {
             return getFontFromInput("/assets/ferox/fonts/Subtitle.ttf");
         } else {
             return Font.createFont(Font.TRUETYPE_FONT, new File("/assets/ferox/fonts/" + name + ".ttf"));
         }
    }

    private static Font font = null;

    public static Font getFontFromInput(String path) throws IOException, FontFormatException {
        Font newFont = Font.createFont(Font.TRUETYPE_FONT, FeroxFontRenderer.class.getResourceAsStream(path));

        if (newFont != null) {
            font = newFont;
        }

        return font;
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }

        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            if (resolution.getScaleFactor() != prevScaleFactor) {
                prevScaleFactor = resolution.getScaleFactor();
                unicodeFont = new UnicodeFont(getFontByName(name).deriveFont(size * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            }
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }

        this.antiAliasingFactor = resolution.getScaleFactor();

        GL11.glPushMatrix();
        GlStateManager.scale(1 / antiAliasingFactor, 1 / antiAliasingFactor, 1 / antiAliasingFactor);

        x *= antiAliasingFactor;
        y *= antiAliasingFactor;

        float originalX = x;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);

        int currentColor = color;

        char[] characters = text.toCharArray();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String[] parts = COLOR_CODE_PATTERN.split(text);

        int index = 0;

        for (String s : parts) {
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {
                    unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                    x += unicodeFont.getWidth(s3);

                    index += s3.length();

                    if (index < characters.length && characters[index] == '\r') {
                        x = originalX;

                        index++;
                    }
                }

                if (index < characters.length && characters[index] == '\n') {
                    x = originalX;
                    y += getHeight(s2) * 2;

                    index++;
                }
            }

            if (index < characters.length) {
                char colorCode = characters[index];
                if (colorCode == StringUtil.COLOR_CHAR) {
                    char colorChar = characters[index + 1];
                    int codeIndex = ("0123456789" + "abcdef").indexOf(colorChar);
                    if (codeIndex < 0) {
                        if (colorChar == 'r') {
                            currentColor = color;
                        }
                    } else {
                        currentColor = colorCodes[codeIndex];
                    }
                    index += 2;
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.bindTexture(0);
        GlStateManager.popMatrix();

        return (int) getWidth(text);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        if (text == null || text == "") {
            return 0;
        }

        drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0x000000);

        return drawString(text, x, y, color);
    }

    public UnicodeFont getFont() {
        return unicodeFont;
    }

    public float getWidth(String text) {
        if (cachedStringWidth.size() > 1000) {
            cachedStringWidth.clear();
        }
        return cachedStringWidth.computeIfAbsent(text, e -> unicodeFont.getWidth(StringUtil.stripColor(text)) / antiAliasingFactor);
    }

    public float getHeight(String s) {
        return unicodeFont.getHeight(s) / 2.0F;
    }

    public float getStringWidth(String text) {
        return unicodeFont.getWidth(StringUtil.stripColor(text)) / 2;
    }

    public float getStringHeight(String text) {
        return getHeight(text);
    }
}

package com.olliem5.ferox.api.util.render.font.renderer;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Random;

/**
 * @author olliem5
 * @author linustouchtips
 */

public final class CustomFontRenderer extends FontRenderer implements Minecraft {
    private final ImageAWT font;

    public CustomFontRenderer(Font font) {
        super(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), null, false);
        this.font = new ImageAWT(font);
        new ImageAWT(font.deriveFont(2));
        this.FONT_HEIGHT = this.getHeight();
    }

    public int getHeight() {
        return this.font.getHeight() / 2;
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, true);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        float currY = y - 3.0f;

        if (text.contains("\n")) {
            String[] parts = text.split("\n");

            float newY = 0.0f;

            for (String s : parts) {
                this.drawText(s, x, currY + newY, color, dropShadow);

                newY += (float) this.getHeight();
            }

            return 0;
        }

        if (dropShadow) {
            this.drawText(text, x + 0.5f, currY + 0.5f, 0x000000, true);
        }

        return this.drawText(text, x, currY, color, false);
    }

    private int drawText(String text, float x, float y, int color, boolean ignoreColor) {
        if (text == null) return 0;

        if (text.isEmpty()) return (int) x;

        GlStateManager.translate((double) x - 1.5, (double) y + 0.5, 0.0);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();

        int currentColor = color;

        if ((currentColor & 0xFC000000) == 0) {
            currentColor |= 0xFF000000;
        }

        int alpha = currentColor >> 24 & 0xFF;

        if (text.contains("§")) {
            String[] parts = text.split("§");
            ImageAWT currentFont = this.font;

            double width = 0.0;
            boolean randomCase = false;

            for (int index = 0; index < parts.length; ++index) {
                String part = parts[index];

                if (part.isEmpty()) continue;

                if (index == 0) {
                    currentFont.drawString(part, width, 0.0, currentColor);
                    width += currentFont.getStringWidth(part);

                    continue;
                }

                String words = part.substring(1);

                char type = part.charAt(0);
                int colorIndex = "0123456789abcdefklmnor".indexOf(type);

                switch (colorIndex) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        if (!ignoreColor) {
                            currentColor = ColorUtils.hexColors[colorIndex] | alpha << 24;
                        }

                        randomCase = false;
                        break;
                    case 16: {
                        randomCase = true;
                        break;
                    }
                    case 18:
                        break;
                    case 21:
                        currentColor = color;
                        if ((currentColor & 0xFC000000) == 0) {
                            currentColor |= 0xFF000000;
                        }

                        randomCase = false;
                }

                currentFont = this.font;

                if (randomCase) {
                    currentFont.drawString(ColorUtils.randomMagicText(words), width, 0.0, currentColor);
                } else {
                    currentFont.drawString(words, width, 0.0, currentColor);
                }

                width += currentFont.getStringWidth(words);
            }
        } else {
            this.font.drawString(text, 0.0, 0.0, currentColor);
        }

        GlStateManager.disableBlend();
        GlStateManager.translate(-((double) x - 1.5), -((double) y + 0.5), 0.0);

        return (int) (x + (float) this.getStringWidth(text));
    }

    public int getColorCode(char charCode) {
        return ColorUtils.hexColors[CustomFontRenderer.getColorIndex(charCode)];
    }

    @Override
    public int getStringWidth(String text) {
        String currentText = text;

        if (text.contains("§")) {
            String[] parts = text.split("§");
            ImageAWT currentFont = this.font;

            int width = 0;

            for (int index = 0; index < parts.length; ++index) {
                String part = parts[index];

                if (part.isEmpty()) continue;

                if (index == 0) {
                    width += currentFont.getStringWidth(part);

                    continue;
                }

                String words = part.substring(1);

                currentFont =  this.font;
                width += currentFont.getStringWidth(words);
            }

            return width / 2;
        }

        return this.font.getStringWidth(currentText) / 2;
    }

    public int getCharWidth(char character) {
        return this.getStringWidth(String.valueOf(character));
    }

    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    protected void bindTexture(ResourceLocation location) {

    }

    public static int getColorIndex(char type) {
        switch (type) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return type - 48;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
                return type - 97 + 10;
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
                return type - 107 + 16;
            case 'r':
                return 21;
        }
        return -1;
    }

    private static class ColorUtils {
        public static int[] hexColors = new int[16];
        private static final Random random;
        private static final String magicAllowedCharacters = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■";

        private ColorUtils() {

        }

        public static String randomMagicText(String text) {
            StringBuilder stringBuilder = new StringBuilder();

            for (char ch : text.toCharArray()) {

                if (!ChatAllowedCharacters.isAllowedCharacter(ch)) continue;

                int index = random.nextInt(magicAllowedCharacters.length());

                stringBuilder.append(magicAllowedCharacters.charAt(index));
            }

            return stringBuilder.toString();
        }

        static {
            ColorUtils.hexColors[0] = 0;
            ColorUtils.hexColors[1] = 170;
            ColorUtils.hexColors[2] = 43520;
            ColorUtils.hexColors[3] = 43690;
            ColorUtils.hexColors[4] = 0xAA0000;
            ColorUtils.hexColors[5] = 0xAA00AA;
            ColorUtils.hexColors[6] = 0xFFAA00;
            ColorUtils.hexColors[7] = 0xAAAAAA;
            ColorUtils.hexColors[8] = 0x555555;
            ColorUtils.hexColors[9] = 0x5555FF;
            ColorUtils.hexColors[10] = 0x55FF55;
            ColorUtils.hexColors[11] = 0x55FFFF;
            ColorUtils.hexColors[12] = 0xFF5555;
            ColorUtils.hexColors[13] = 0xFF55FF;
            ColorUtils.hexColors[14] = 0xFFFF55;
            ColorUtils.hexColors[15] = 0xFFFFFF;

            random = new Random();
        }
    }
}

package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.ClientFont;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 *
 * TODO: Also change string width & string height checks to custom ones
 */

@Mixin(FontRenderer.class)
public final class MixinFontRenderer implements Minecraft {
    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At(value = "HEAD"), cancellable = true)
    public void renderString(String text, float x, float y, int colour, boolean shadow, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (mc.player == null || mc.world == null) return;

        if (ModuleManager.getModuleByName("Font").isEnabled() && ClientFont.overrideMinecraft.getValue()) {
            if (ClientFont.shadow.getValue()) {
                callbackInfoReturnable.setReturnValue(getShadowString(text, x, y, colour));
            } else {
                callbackInfoReturnable.setReturnValue(getString(text, x, y, colour));
            }
        }
    }

    private int getString(String text, float x, float y, int colour) {
        switch (ClientFont.font.getValue()) {
            case Ubuntu:
                return FontUtil.ubuntuFont.drawString(text, x, y, colour);
            case Lato:
                return FontUtil.latoFont.drawString(text, x, y, colour);
            case Verdana:
                return FontUtil.verdanaFont.drawString(text, x, y, colour);
            case Comfortaa:
                return FontUtil.comfortaaFont.drawString(text, x, y, colour);
            case Subtitle:
                return FontUtil.subtitleFont.drawString(text, x, y, colour);
        }

        return -1;
    }

    private int getShadowString(String text, float x, float y, int colour) {
        switch (ClientFont.font.getValue()) {
            case Ubuntu:
                FontUtil.ubuntuFont.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
                return FontUtil.ubuntuFont.drawString(text, x, y, colour);
            case Lato:
                FontUtil.latoFont.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
                return FontUtil.latoFont.drawString(text, x, y, colour);
            case Verdana:
                FontUtil.verdanaFont.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
                return FontUtil.verdanaFont.drawString(text, x, y, colour);
            case Comfortaa:
                FontUtil.comfortaaFont.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
                return FontUtil.comfortaaFont.drawString(text, x, y, colour);
            case Subtitle:
                FontUtil.subtitleFont.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
                return FontUtil.subtitleFont.drawString(text, x, y, colour);
        }

        return -1;
    }
}

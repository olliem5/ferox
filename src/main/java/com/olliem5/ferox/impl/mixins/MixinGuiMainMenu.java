package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.colour.ColourUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(GuiMainMenu.class)
public final class MixinGuiMainMenu extends GuiScreen {
    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        FontUtil.drawText(Ferox.NAME_VERSION, 2, 2, ColourUtil.getRainbow().getRGB());
    }
}

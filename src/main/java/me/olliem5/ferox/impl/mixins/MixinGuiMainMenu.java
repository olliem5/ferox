package me.olliem5.ferox.impl.mixins;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.font.FontUtil;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {
    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        FontUtil.drawText(Ferox.NAME_VERSION, 2, 2, RainbowUtil.getRainbow().getRGB());
    }
}

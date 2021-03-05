package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.gui.screens.mainmenu.FeroxGuiMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(net.minecraft.client.Minecraft.class)
public final class MixinMinecraft implements Minecraft {
    @Inject(method = "runTick()V", at = @At(value = "RETURN"))
    private void runTick(CallbackInfo callbackInfo) {
        if (mc.currentScreen instanceof GuiMainMenu && ModuleManager.getModuleByName("MainMenu").isEnabled()) {
            mc.displayGuiScreen(new FeroxGuiMainMenu());
        }
    }
}

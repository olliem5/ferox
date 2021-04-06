package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.miscellaneous.ChatTweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/**
 * @author olliem5
 */

@Mixin(GuiNewChat.class)
public final class MixinGuiNewChat {
    //TODO: Fix this
//    @Redirect(method = "setChatLine", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
//    public int maxChatLines(List<?> list) {
//        if (ModuleManager.getModuleByName("ChatTweaks").isEnabled() && ChatTweaks.infiniteScroll.getValue()) return -1;
//        else return list.size();
//    }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawBackground(int left, int top, int right, int bottom, int colour) {
        if (!ModuleManager.getModuleByName("ChatTweaks").isEnabled() || !ChatTweaks.clearBackground.getValue()) {
            Gui.drawRect(left, top, right, bottom, colour);
        }
    }
}

package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "ItemTooltips", description = "Shows handy tooltips for shulker boxes and maps", category = Category.RENDER)
public final class ItemTooltips extends Module {
    public static void renderShulkerTooltip(ItemStack itemStack, int x, int y) {
        Gui.drawRect(x + 8, y - 21, x + 158, y - 8, new Color(100, 20, 20, 200).getRGB());
        Gui.drawRect(x + 8, y - 6, x + 158, y + 49, new Color(0, 0, 0, 150).getRGB());

        FontUtil.drawString(itemStack.getDisplayName(), x + 10, y - 18, -1);

        for (int i = 0; i < 27; i++) {
            int offsetX = x + (i % 9) * 16 + 11;
            int offsetY = y + (i / 9) * 16 - 3;

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }
    }
}
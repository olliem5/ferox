package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "ItemTooltips", description = "Shows handy tooltips for shulker boxes and maps", category = Category.RENDER)
public final class ItemTooltips extends Module {
    public static final Setting<Boolean> shulkers = new Setting<>("Shulkers", "Allows for the shulker box tooltip to render", true);
    public static final Setting<Boolean> maps = new Setting<>("Maps", "Allows for the map tooltip to render", true);

    public ItemTooltips() {
        this.addSettings(
                shulkers,
                maps
        );
    }

    public static void renderShulkerTooltip(ItemStack itemStack, int x, int y) {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        Gui.drawRect(x + 8, y - 21, x + 158, y - 6, new Color(100, 20, 20, 225).getRGB());
        Gui.drawRect(x + 8, y - 6, x + 158, y + 48, new Color(0, 0, 0, 225).getRGB());

        FontUtil.drawString(itemStack.getDisplayName(), x + 10, y - 18, -1);

        GlStateManager.enableDepth();

        mc.getRenderItem().zLevel = 150.0f;

        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 27; i++) {
            int offsetX = x + (i % 9) * 16 + 11;
            int offsetY = y + (i / 9) * 16 - 3;

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();

        mc.getRenderItem().zLevel = 0.0f;

        GlStateManager.enableLighting();
    }
}

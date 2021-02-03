package me.olliem5.ferox.impl.hud;

import me.olliem5.ferox.api.hud.ComponentInfo;
import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;

@ComponentInfo(name = "Inventory")
public class InventoryComponent extends HudComponent {
    public static Setting<Boolean> background = new Setting("Background", true);

    public InventoryComponent() {
        setWidth(144);
        setHeight(48);

        this.addSettings(
                background
        );
    }

    @Override
    public void render() {
        if (background.getValue()) {
            Gui.drawRect(getPosX(), getPosY(), getPosX() + getWidth(), getPosY() + getHeight(), new Color(20, 20, 20, 100).getRGB());
        }

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);

            int offsetX = getPosX() + (i % 9) * 16;
            int offsetY = getPosY() + (i / 9) * 16;

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;

        GlStateManager.popMatrix();
    }
}

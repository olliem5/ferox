package us.ferox.client.impl.hud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import us.ferox.client.api.hud.ComponentInfo;
import us.ferox.client.api.hud.HudComponent;
import us.ferox.client.api.setting.Setting;

import java.awt.*;

@ComponentInfo(name = "Inventory")
public class InventoryComponent extends HudComponent {
    public InventoryComponent() {
        setWidth(144);
        setHeight(48);
    }

    Setting<Boolean> background = addSetting(new Setting("Background", true));

    @Override
    public void render() {
        if(background.getValue()) Gui.drawRect(getPosX(), getPosY(), getPosX() + getWidth(), getPosY() + getHeight(), new Color(20, 20, 20, 100).getRGB());

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

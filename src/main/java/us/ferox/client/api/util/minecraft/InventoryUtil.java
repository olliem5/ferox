package us.ferox.client.api.util.minecraft;

import net.minecraft.item.Item;
import us.ferox.client.api.traits.Minecraft;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class InventoryUtil implements Minecraft {

    public static void switchToSlot(Item item) {
        mc.player.inventory.currentItem = getHotbarItemSlot(item);
    }

    public static int getHotbarItemSlot(Item item) {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                slot = i;
                break;
            }
        }

        return slot;
    }
}

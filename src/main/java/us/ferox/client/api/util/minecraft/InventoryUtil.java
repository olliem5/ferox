package us.ferox.client.api.util.minecraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import us.ferox.client.api.traits.Minecraft;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

public class InventoryUtil implements Minecraft {
    public static void switchToSlot(Class<? extends Item> clazz) {
        if (mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(clazz)) return;
        int slot = getHotbarItemSlot(clazz);
        if (slot == -1) return;
        mc.player.inventory.currentItem = slot;
    }

    public static void switchToSlot(Item item) {
        if (mc.player.getHeldItemMainhand().getItem() == item) return;
        int slot = getHotbarItemSlot(item.getClass());
        if (slot == -1) return;
        mc.player.inventory.currentItem = slot;
    }

    public static int getHotbarItemSlot(Class<? extends Item> item) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem().getClass().isAssignableFrom(item)) {
                slot = i;
                break;
            }
        }

        return slot;
    }

    public static int getHotbarBlockSlot(Block block) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block)) {
                slot = i;
                break;
            }
        }

        return slot;
    }
}

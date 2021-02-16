package com.olliem5.ferox.api.util.player;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * @author olliem5
 * @author Reap
 */

public final class InventoryUtil implements Minecraft {
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

    public static void offhandItem(Item item) {
        final int slot = getInventoryItemSlot(item);

        if (slot != -1) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
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

    public static int getHotbarItemSlot(Item item) {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            Item selection = mc.player.inventory.getStackInSlot(i).getItem();

            if (selection.equals(item)) {
                slot = i;

                break;
            }
        }

        return slot;
    }

    private static int getInventoryItemSlot(Item item) {
        for (int i = 0; i < 36; i++) {
            final Item cacheItem = mc.player.inventory.getStackInSlot(i).getItem();

            if (cacheItem == item) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }

        return -1;
    }
}

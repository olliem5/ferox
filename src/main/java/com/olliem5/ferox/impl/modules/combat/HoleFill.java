package com.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import com.olliem5.ferox.api.util.player.PlayerUtil;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.BlockUtil;
import com.olliem5.ferox.api.util.world.HoleUtil;
import com.olliem5.ferox.api.util.world.PlaceUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author olliem5
 *
 * TODO: Fix disable setting
 * TODO: Delay
 */

@FeroxModule(name = "HoleFill", description = "Fills holes around you with obsidian", category = Category.Combat)
public final class HoleFill extends Module {
    public static final Setting<BlockModes> blockMode = new Setting<>("Block", "The block to fill holes with", BlockModes.Obsidian);
    public static final NumberSetting<Integer> holeRange = new NumberSetting<>("Hole Range", "The range to search for holes to fill in", 1, 3, 10, 0);
    public static final Setting<Boolean> disables = new Setting<>("Disables", "Disables the module when there are no holes to fill", true);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the block placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the block placements", new Color(56, 202, 17, 188));

    public HoleFill() {
        this.addSettings(
                blockMode,
                holeRange,
                disables,
                renderPlace
        );
    }

    private int obsidianSlot;
    private int enderChestSlot;
    private int webSlot;

    private BlockPos renderBlock = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);
        enderChestSlot = InventoryUtil.getHotbarBlockSlot(Blocks.ENDER_CHEST);
        webSlot = InventoryUtil.getHotbarBlockSlot(Blocks.WEB);

        switch (blockMode.getValue()) {
            case Obsidian:
                if (obsidianSlot == -1) {
                    MessageUtil.sendClientMessage("No Obsidian, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                break;
            case EnderChest:
                if (enderChestSlot == -1) {
                    MessageUtil.sendClientMessage("No Ender Chests, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                break;
            case Web:
                if (webSlot == -1) {
                    MessageUtil.sendClientMessage("No Webs, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                break;
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        renderBlock = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        List<BlockPos> holesToFill = getHolesToFill();

        for (BlockPos blockPos : holesToFill) {
            if (holesToFill.size() == 0) {
                if (disables.getValue()) {
                    this.toggle();
                }

                return;
            }

            final int oldInventorySlot = mc.player.inventory.currentItem;

            switch (blockMode.getValue()) {
                case Obsidian:
                    if (obsidianSlot != -1) {
                        mc.player.inventory.currentItem = obsidianSlot;
                    }

                    break;
                case EnderChest:
                    if (enderChestSlot != -1) {
                        mc.player.inventory.currentItem = enderChestSlot;
                    }

                    break;
                case Web:
                    if (webSlot != -1) {
                        mc.player.inventory.currentItem = webSlot;
                    }

                    break;
            }

            renderBlock = holesToFill.get(0);

            switch (blockMode.getValue()) {
                case Obsidian:
                    if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                        PlaceUtil.placeBlock(holesToFill.get(0));
                    }

                    break;
                case EnderChest:
                    if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.ENDER_CHEST)) {
                        PlaceUtil.placeBlock(holesToFill.get(0));
                    }

                    break;
                case Web:
                    if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WEB)) {
                        PlaceUtil.placeBlock(holesToFill.get(0));
                    }

                    break;
            }

            mc.player.inventory.currentItem = oldInventorySlot;
        }
    }

    public List<BlockPos> getHolesToFill() {
            return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                    .filter(blockPos -> HoleUtil.isObsidianHole(blockPos) || HoleUtil.isBedrockHole(blockPos))
                    .filter(blockPos -> !PlayerUtil.blockIntersectsPlayer(blockPos))
                    .collect(Collectors.toList());
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        if (renderPlace.getValue() && renderBlock != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            RenderUtil.draw(renderBlock, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
        }
    }

    public String getArraylistInfo() {
        return blockMode.getValue().toString();
    }

    public enum BlockModes {
        Obsidian,
        EnderChest,
        Web
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

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
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.block.Block;
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

    private BlockPos blockToFill = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);
        enderChestSlot = InventoryUtil.getHotbarBlockSlot(Blocks.ENDER_CHEST);
        webSlot = InventoryUtil.getHotbarBlockSlot(Blocks.WEB);

        if (getBlockSlot() == -1) {
            MessageUtil.sendClientMessage("No " + getBlockText() + ", " + ChatFormatting.RED + "Disabling!");
            this.disable();
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        blockToFill = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        List<BlockPos> holesToFill = getHolesToFill();
        BlockPos currentHoleToFill = null;

        for (BlockPos blockPos : holesToFill) {
            if (holesToFill.size() == 0) {
                if (disables.getValue()) {
                    MessageUtil.sendClientMessage("No holes to fill, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                return;
            }

            currentHoleToFill = blockPos;
        }

        int oldInventorySlot = mc.player.inventory.currentItem;

        if (getBlockSlot() != -1) {
            mc.player.inventory.currentItem = getBlockSlot();
        }

        blockToFill = currentHoleToFill;

        if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(getBlockBlock()) && blockToFill != null) {
            PlaceUtil.placeBlock(blockToFill);
        }

        mc.player.inventory.currentItem = oldInventorySlot;
    }

    private String getBlockText() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return "Obsidian";
            case EnderChest:
                return "Ender Chests";
            case Web:
                return "Webs";
        }

        return "Obsidian";
    }

    private int getBlockSlot() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return obsidianSlot;
            case EnderChest:
                return enderChestSlot;
            case Web:
                return webSlot;
        }

        return obsidianSlot;
    }

    private Block getBlockBlock() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return Blocks.OBSIDIAN;
            case EnderChest:
                return Blocks.ENDER_CHEST;
            case Web:
                return Blocks.WEB;
        }

        return Blocks.OBSIDIAN;
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

        if (renderPlace.getValue() && blockToFill != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            RenderUtil.draw(blockToFill, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
        }
    }

    @Override
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

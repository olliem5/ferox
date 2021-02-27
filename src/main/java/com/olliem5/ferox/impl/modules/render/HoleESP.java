package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.BlockUtil;
import com.olliem5.ferox.api.util.world.HoleUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author olliem5
 */

@FeroxModule(name = "HoleESP", description = "Highlights safe holes for crystal pvp", category = Category.Render)
public final class HoleESP extends Module {
    public static final NumberSetting<Integer> holeRange = new NumberSetting<>("Hole Range", "The range to search for holes in", 1, 5, 10, 0);

    public static final Setting<Boolean> obsidian = new Setting<>("Obsidian Holes", "Allows obsidian holes to be rendered", true);
    public static final Setting<RenderModes> obsidianRenderMode = new Setting<>(obsidian, "O Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> obsidianBoxHeight = new NumberSetting<>(obsidian, "O Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> obsidianOutlineHeight = new NumberSetting<>(obsidian, "O Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> obsidianOutlineWidth = new NumberSetting<>(obsidian, "O Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> obsidianHoleColour = new Setting<>(obsidian, "Obsidian Hole Colour", "The colour for obsidian holes", new Color(222, 38, 38, 169));

    public static final Setting<Boolean> bedrock = new Setting<>("Bedrock Holes", "Allows bedrock holes to be rendered", true);
    public static final Setting<RenderModes> bedrockRenderMode = new Setting<>(bedrock, "B Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> bedrockBoxHeight = new NumberSetting<>(bedrock, "B Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> bedrockOutlineHeight = new NumberSetting<>(bedrock, "B Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> bedrockOutlineWidth = new NumberSetting<>(bedrock, "B Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> bedrockHoleColour = new Setting<>(bedrock, "Bedrock Hole Colour", "The colour for bedrock holes", new Color(61, 194, 46, 169));

    public static final Setting<Boolean> enderChest = new Setting<>("Ender Chest Holes", "Allows ender chest holes to be rendered", true);
    public static final Setting<RenderModes> enderChestRenderMode = new Setting<>(enderChest, "E Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> enderChestBoxHeight = new NumberSetting<>(enderChest, "E Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> enderChestOutlineHeight = new NumberSetting<>(enderChest, "E Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> enderChestOutlineWidth = new NumberSetting<>(enderChest, "E Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> enderChestHoleColour = new Setting<>(enderChest, "Ender Chest Hole Colour", "The colour for ender chest holes", new Color(222, 38, 38, 169));

    public static final Setting<Boolean> enchantingTable = new Setting<>("Enchant Table Holes", "Allows enchanting table holes to be rendered", true);
    public static final Setting<RenderModes> enchantingTableRenderMode = new Setting<>(enchantingTable, "ET Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> enchantingTableBoxHeight = new NumberSetting<>(enchantingTable, "ET Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> enchantingTableOutlineHeight = new NumberSetting<>(enchantingTable, "ET Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> enchantingTableOutlineWidth = new NumberSetting<>(enchantingTable, "ET Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> enchantingTableHoleColour = new Setting<>(enchantingTable, "Enchanting Table Hole Colour", "The colour for enchanting table holes", new Color(222, 38, 38, 169));

    public static final Setting<Boolean> anvil = new Setting<>("Anvil Holes", "Allows mixed holes to be rendered", true);
    public static final Setting<RenderModes> anvilRenderMode = new Setting<>(anvil, "A Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> anvilBoxHeight = new NumberSetting<>(anvil, "A Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> anvilOutlineHeight = new NumberSetting<>(anvil, "A Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> anvilOutlineWidth = new NumberSetting<>(anvil, "A Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> anvilHoleColour = new Setting<>(anvil, "Anvil Hole Colour", "The colour for mixed holes", new Color(222, 38, 38, 169));

    public HoleESP() {
        this.addSettings(
                holeRange,
                obsidian,
                bedrock,
                enderChest,
                enchantingTable,
                anvil
        );
    }

    public List<BlockPos> findObsidianHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(HoleUtil::isObsidianHole)
                .collect(Collectors.toList());
    }

    public List<BlockPos> findBedrockHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(HoleUtil::isBedrockHole)
                .collect(Collectors.toList());
    }

    public List<BlockPos> findEnderChestHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(HoleUtil::isEnderChestHole)
                .collect(Collectors.toList());
    }

    public List<BlockPos> findEnchantingTableHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(HoleUtil::isEnchantingTableHole)
                .collect(Collectors.toList());
    }

    public List<BlockPos> findAnvilHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(HoleUtil::isAnvilHole)
                .collect(Collectors.toList());
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        List<BlockPos> obsidianHoles = findObsidianHoles();
        List<BlockPos> bedrockHoles = findBedrockHoles();
        List<BlockPos> enderChestHoles = findEnderChestHoles();
        List<BlockPos> enchantingTableHoles = findEnchantingTableHoles();
        List<BlockPos> anvilHoles = findAnvilHoles();

        if (obsidian.getValue() && obsidianHoles != null) {
            GL11.glLineWidth(obsidianOutlineWidth.getValue().floatValue());

            for (BlockPos obsidianHole : findObsidianHoles()) {
                RenderUtil.draw(obsidianHole, obsidianRenderMode.getValue() != RenderModes.Outline, obsidianRenderMode.getValue() != RenderModes.Box, obsidianBoxHeight.getValue(), obsidianOutlineHeight.getValue(), obsidianHoleColour.getValue());
            }
        }

        if (bedrock.getValue() && bedrockHoles != null) {
            GL11.glLineWidth(bedrockOutlineWidth.getValue().floatValue());

            for (BlockPos bedrockHole : findBedrockHoles()) {
                RenderUtil.draw(bedrockHole, bedrockRenderMode.getValue() != RenderModes.Outline, bedrockRenderMode.getValue() != RenderModes.Box, bedrockBoxHeight.getValue(), bedrockOutlineHeight.getValue(), bedrockHoleColour.getValue());
            }
        }

        if (enderChest.getValue() && enderChestHoles != null) {
            GL11.glLineWidth(enderChestOutlineWidth.getValue().floatValue());

            for (BlockPos enderChestHole : findEnderChestHoles()) {
                RenderUtil.draw(enderChestHole, enderChestRenderMode.getValue() != RenderModes.Outline, enderChestRenderMode.getValue() != RenderModes.Box, enderChestBoxHeight.getValue(), enderChestOutlineHeight.getValue(), enderChestHoleColour.getValue());
            }
        }

        if (enchantingTable.getValue() && enchantingTableHoles != null) {
            GL11.glLineWidth(enchantingTableOutlineWidth.getValue().floatValue());

            for (BlockPos enchantingTableHole : findEnchantingTableHoles()) {
                RenderUtil.draw(enchantingTableHole, enchantingTableRenderMode.getValue() != RenderModes.Outline, enchantingTableRenderMode.getValue() != RenderModes.Box, enchantingTableBoxHeight.getValue(), enchantingTableOutlineHeight.getValue(), enchantingTableHoleColour.getValue());
            }
        }

        if (anvil.getValue() && anvilHoles != null) {
            GL11.glLineWidth(anvilOutlineWidth.getValue().floatValue());

            for (BlockPos anvilHole : findAnvilHoles()) {
                RenderUtil.draw(anvilHole, anvilRenderMode.getValue() != RenderModes.Outline, anvilRenderMode.getValue() != RenderModes.Box, anvilBoxHeight.getValue(), anvilOutlineHeight.getValue(), anvilHoleColour.getValue());
            }
        }
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

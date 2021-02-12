package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.ferox.api.util.world.BlockUtil;
import me.olliem5.ferox.api.util.world.HoleUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author olliem5
 */

@FeroxModule(name = "HoleESP", description = "Highlights safe holes for crystal pvp", category = Category.RENDER)
public final class HoleESP extends Module {
    public static final NumberSetting<Integer> holeRange = new NumberSetting<>("Hole Range", "The range to search for holes in", 1, 5, 10, 0);

    public static final Setting<Boolean> obsidian = new Setting<>("Obsidian Holes", "Allows obsidian holes to be rendered", true);
    public static final Setting<RenderModes> obsidianRenderMode = new Setting<>(obsidian, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> obsidianBoxHeight = new NumberSetting<>(obsidian, "Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> obsidianOutlineHeight = new NumberSetting<>(obsidian, "Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> obsidianOutlineWidth = new NumberSetting<>(obsidian, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> obsidianHoleColour = new Setting<>(obsidian, "Obsidian Hole Colour", "The colour for obsidian holes", new Color(222, 38, 38, 178));

    public static final Setting<Boolean> bedrock = new Setting<>("Bedrock Holes", "Allows bedrock holes to be rendered", true);
    public static final Setting<RenderModes> bedrockRenderMode = new Setting<>(bedrock, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> bedrockBoxHeight = new NumberSetting<>(bedrock, "Box Height", "The height of the box", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> bedrockOutlineHeight = new NumberSetting<>(bedrock, "Outline Height", "The height of the outline", -1.0, -0.7, 2.0, 1);
    public static final NumberSetting<Double> bedrockOutlineWidth = new NumberSetting<>(bedrock, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> bedrockHoleColour = new Setting<>(bedrock, "Bedrock Hole Colour", "The colour for bedrock holes", new Color(61, 194, 46, 169));

    public HoleESP() {
        this.addSettings(
                holeRange,
                obsidian,
                bedrock
        );
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        List<BlockPos> obsidianHoles = findObsidianHoles();
        List<BlockPos> bedrockHoles = findBedrockHoles();

        if (obsidian.getValue()) {
            GL11.glLineWidth(obsidianOutlineWidth.getValue().floatValue());

            if (obsidianHoles != null) {
                for (BlockPos obsidianHole : findObsidianHoles()) {
                    switch (obsidianRenderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(obsidianHole, true, false, obsidianBoxHeight.getValue(), obsidianOutlineHeight.getValue(), obsidianHoleColour.getValue());
                            break;
                        case Outline:
                            RenderUtil.draw(obsidianHole, false, true, obsidianBoxHeight.getValue(), obsidianOutlineHeight.getValue(), obsidianHoleColour.getValue());
                            break;
                        case Full:
                            RenderUtil.draw(obsidianHole, true, true, obsidianBoxHeight.getValue(), obsidianOutlineHeight.getValue(), obsidianHoleColour.getValue());
                            break;
                    }
                }
            }
        }

        if (bedrock.getValue()) {
            GL11.glLineWidth(bedrockOutlineWidth.getValue().floatValue());

            if (bedrockHoles != null) {
                for (BlockPos bedrockHole : findBedrockHoles()) {
                    switch (bedrockRenderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(bedrockHole, true, false, bedrockBoxHeight.getValue(), bedrockOutlineHeight.getValue(), bedrockHoleColour.getValue());
                            break;
                        case Outline:
                            RenderUtil.draw(bedrockHole, false, true, bedrockBoxHeight.getValue(), bedrockOutlineHeight.getValue(), bedrockHoleColour.getValue());
                            break;
                        case Full:
                            RenderUtil.draw(bedrockHole, true, true, bedrockBoxHeight.getValue(), bedrockOutlineHeight.getValue(), bedrockHoleColour.getValue());
                            break;
                    }
                }
            }
        }
    }

    public List<BlockPos> findObsidianHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(blockPos -> HoleUtil.isObsidianHole(blockPos))
                .collect(Collectors.toList());
    }

    public List<BlockPos> findBedrockHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, holeRange.getValue(), false).stream()
                .filter(blockPos -> HoleUtil.isBedrockHole(blockPos))
                .collect(Collectors.toList());
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

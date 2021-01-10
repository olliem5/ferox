package us.ferox.client.impl.modules.render;

import git.littledraily.eventsystem.Listener;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.minecraft.BlockUtil;
import us.ferox.client.api.util.minecraft.PlayerUtil;
import us.ferox.client.api.util.module.HoleUtil;
import us.ferox.client.api.util.render.RenderUtil;
import us.ferox.client.impl.events.WorldRenderEvent;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleInfo(name = "HoleESP", description = "Highlights safe holes for crystal pvp", category = Category.RENDER)
public class HoleESP extends Module {
    public static NumberSetting<Integer> holeRange = new NumberSetting<>("Hole Range", 1, 5, 10);

    public static Setting<Boolean> obsidian = new Setting<>("Obsidian Holes", true);
    public static NumberSetting<Integer> obsidianRed = new NumberSetting<>(obsidian, "Obsidian Red", 0, 100, 255);
    public static NumberSetting<Integer> obsidianGreen = new NumberSetting<>(obsidian, "Obsidian Green", 0, 100, 255);
    public static NumberSetting<Integer> obsidianBlue = new NumberSetting<>(obsidian, "Obsidian Blue", 0, 100, 255);
    public static NumberSetting<Integer> obsidianAlpha = new NumberSetting<>(obsidian, "Obsidian Alpha", 0, 100, 255);

    public static Setting<Boolean> bedrock = new Setting<>("Bedrock Holes", true);
    public static NumberSetting<Integer> bedrockRed = new NumberSetting<>(bedrock, "Bedrock Red", 0, 100, 255);
    public static NumberSetting<Integer> bedrockGreen = new NumberSetting<>(bedrock, "Bedrock Green", 0, 100, 255);
    public static NumberSetting<Integer> bedrockBlue = new NumberSetting<>(bedrock, "Bedrock Blue", 0, 100, 255);
    public static NumberSetting<Integer> bedrockAlpha = new NumberSetting<>(bedrock, "Bedrock Alpha", 0, 100, 255);

    public static Setting<Boolean> renderSettings = new Setting<>("Render Settings", true);
    public static Setting<RenderModes> renderMode = new Setting<>(renderSettings, "Render Mode", RenderModes.Full);
    public static NumberSetting<Double> boxHeight = new NumberSetting<>(renderSettings, "Box Height", -1.0, 0.0, 2.0);
    public static NumberSetting<Double> outlineHeight = new NumberSetting<>(renderSettings, "Outline Height", -1.0, 0.0, 2.0);
    public static NumberSetting<Double> outlineWidth = new NumberSetting<>(renderSettings, "Outline Width", 1.0, 2.0, 5.0);

    public HoleESP() {
        this.addSetting(holeRange);

        this.addSetting(obsidian);
        this.addSetting(obsidianRed);
        this.addSetting(obsidianGreen);
        this.addSetting(obsidianBlue);
        this.addSetting(obsidianAlpha);

        this.addSetting(bedrock);
        this.addSetting(bedrockRed);
        this.addSetting(bedrockGreen);
        this.addSetting(bedrockBlue);
        this.addSetting(bedrockAlpha);

        this.addSetting(renderSettings);
        this.addSetting(renderMode);
        this.addSetting(boxHeight);
        this.addSetting(outlineHeight);
        this.addSetting(outlineWidth);
    }

    @Listener
    public void onWorldRender(WorldRenderEvent event) {
        if (nullCheck()) return;

        List<BlockPos> obsidianHoles = findObsidianHoles();
        List<BlockPos> bedrockHoles = findBedrockHoles();

        Color obsidianHoleColour = new Color(obsidianRed.getValue(), obsidianGreen.getValue(), obsidianBlue.getValue(), obsidianAlpha.getValue());
        Color bedrockHoleColour = new Color(bedrockRed.getValue(), bedrockGreen.getValue(), bedrockBlue.getValue(), bedrockAlpha.getValue());

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (obsidian.getValue()) {
            if (obsidianHoles != null) {
                for (BlockPos obsidianHole : findObsidianHoles()) {
                    switch (renderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(RenderUtil.generateBB(obsidianHole.getX(), obsidianHole.getY(), obsidianHole.getZ()), true, false, boxHeight.getValue(), outlineHeight.getValue(), obsidianHoleColour);
                            break;
                        case Outline:
                            RenderUtil.draw(RenderUtil.generateBB(obsidianHole.getX(), obsidianHole.getY(), obsidianHole.getZ()), false, true, boxHeight.getValue(), outlineHeight.getValue(), obsidianHoleColour);
                            break;
                        case Full:
                            RenderUtil.draw(RenderUtil.generateBB(obsidianHole.getX(), obsidianHole.getY(), obsidianHole.getZ()), true, true, boxHeight.getValue(), outlineHeight.getValue(), obsidianHoleColour);
                            break;
                    }
                }
            }
        }

        if (bedrock.getValue()) {
            if (bedrockHoles != null) {
                for (BlockPos bedrockHole : findBedrockHoles()) {
                    switch (renderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(RenderUtil.generateBB(bedrockHole.getX(), bedrockHole.getY(), bedrockHole.getZ()), true, false, boxHeight.getValue(), outlineHeight.getValue(), bedrockHoleColour);
                            break;
                        case Outline:
                            RenderUtil.draw(RenderUtil.generateBB(bedrockHole.getX(), bedrockHole.getY(), bedrockHole.getZ()), false, true, boxHeight.getValue(), outlineHeight.getValue(), bedrockHoleColour);
                            break;
                        case Full:
                            RenderUtil.draw(RenderUtil.generateBB(bedrockHole.getX(), bedrockHole.getY(), bedrockHole.getZ()), true, true, boxHeight.getValue(), outlineHeight.getValue(), bedrockHoleColour);
                            break;
                    }
                }
            }
        }
    }

    public List<BlockPos> findObsidianHoles() {
        NonNullList positions = NonNullList.create();

        positions.addAll(BlockUtil.getSphere(PlayerUtil.getPlayerPos(), holeRange.getValue(), holeRange.getValue(), false, true, 0).stream()
                .filter(HoleUtil::isObsidianHole)
                .collect(Collectors.toList()));

        return positions;
    }

    public List<BlockPos> findBedrockHoles() {
        NonNullList positions = NonNullList.create();

        positions.addAll(BlockUtil.getSphere(PlayerUtil.getPlayerPos(), holeRange.getValue(), holeRange.getValue(), false, true, 0).stream()
                .filter(HoleUtil::isBedrockHole)
                .collect(Collectors.toList()));

        return positions;
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

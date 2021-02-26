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

@FeroxModule(name = "VoidESP", description = "Shows void holes", category = Category.Render)
public final class VoidESP extends Module {
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", "The range to search for void holes in", 1, 5, 10, 0);

    public static final Setting<Boolean> renderBlock = new Setting<>("Render", "Allows the void holes to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderBlock, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderBlock, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderBlock, "Render Colour", "The colour for the void hole", new Color(243, 11, 11, 220));

    public VoidESP() {
        this.addSettings(
                range,
                renderBlock
        );
    }

    public List<BlockPos> findVoidHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, range.getValue(), false).stream()
                .filter(HoleUtil::isVoidHole)
                .collect(Collectors.toList());
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        List<BlockPos> voidHoles = findVoidHoles();

        if (renderBlock.getValue() && voidHoles != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            for (BlockPos voidHole : findVoidHoles()) {
                RenderUtil.draw(voidHole, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
            }
        }
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

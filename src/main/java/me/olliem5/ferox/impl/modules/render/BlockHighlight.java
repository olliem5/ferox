package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "BlockHighlight", description = "Highlights the block that you are looking at", category = Category.RENDER)
public final class BlockHighlight extends Module {
    public static final Setting<Boolean> cancelSelectionBox = new Setting<>("Cancel Selection Box", "Cancels the rendering of the vanilla block selection box", true);

    public static final Setting<Boolean> renderBlock = new Setting<>("Render", "Allows the facing block to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderBlock, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderBlock, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderBlock, "Render Colour", "The colour for the facing block", new Color(135, 46, 181, 220));

    public BlockHighlight() {
        this.addSettings(
                cancelSelectionBox,
                renderBlock
        );
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (renderBlock.getValue()) {
            if (mc.objectMouseOver != null) {
                if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                    switch (renderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(mc.objectMouseOver.getBlockPos(), true, false, 0, 0, renderColour.getValue());
                            break;
                        case Outline:
                            RenderUtil.draw(mc.objectMouseOver.getBlockPos(), false, true, 0, 0, renderColour.getValue());
                            break;
                        case Full:
                            RenderUtil.draw(mc.objectMouseOver.getBlockPos(), true, true, 0, 0, renderColour.getValue());
                            break;
                    }
                }
            }
        }
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

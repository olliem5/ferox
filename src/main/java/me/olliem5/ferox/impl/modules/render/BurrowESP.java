package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author olliem5
 */

@FeroxModule(name = "BurrowESP", description = "Shows blocks that players are burrowed in", category = Category.Render)
public final class BurrowESP extends Module {
    public static final Setting<Boolean> renderOwn = new Setting<>("Render Own", "Renders your own burrow block", false);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", "The range to search for burrow blocks in", 1, 5, 10, 0);

    public static final Setting<Boolean> renderBlock = new Setting<>("Render", "Allows the burrow blocks to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderBlock, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderBlock, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderBlock, "Render Colour", "The colour for the burrow blocks", new Color(91, 79, 208, 220));

    public BurrowESP() {
        this.addSettings(
                renderOwn,
                range,
                renderBlock
        );
    }

    private List<BlockPos> burrowBlocksList = new ArrayList<>();

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        burrowBlocksList.clear();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        burrowBlocksList.clear();
    }

    public void onUpdate() {
        if (nullCheck()) return;

        burrowBlocksList.clear();

        for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer.getDistance(mc.player) <= range.getValue()) {
                if (entityPlayer == mc.player && renderOwn.getValue()) {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Blocks.OBSIDIAN) {
                        burrowBlocksList.add(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ));
                    }
                } else {
                    if (entityPlayer != mc.player && !renderOwn.getValue()) {
                        if (mc.world.getBlockState(new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ)).getBlock() == Blocks.OBSIDIAN) {
                            burrowBlocksList.add(new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
                        }
                    }
                }
            }
        }
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (renderBlock.getValue()) {
            for (BlockPos burrowBlock : burrowBlocksList) {
                if (burrowBlock != null) {
                    switch (renderMode.getValue()) {
                        case Box:
                            RenderUtil.draw(burrowBlock, true, false, 0, 0, renderColour.getValue());
                            break;
                        case Outline:
                            RenderUtil.draw(burrowBlock, false, true, 0, 0, renderColour.getValue());
                            break;
                        case Full:
                            RenderUtil.draw(burrowBlock, true, true, 0, 0, renderColour.getValue());
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

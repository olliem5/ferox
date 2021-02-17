package com.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import com.olliem5.ferox.api.util.player.TargetUtil;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.PlaceUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author olliem5
 */

@FeroxModule(name = "AutoTrap", description = "Automatically traps enemies with obsidian", category = Category.Combat)
public final class AutoTrap extends Module {
    public static final Setting<PlaceModes> placeMode = new Setting<>("Place", "The style of trap to place", PlaceModes.Full);
    public static final Setting<DisableModes> disableMode = new Setting<>("Disable", "When to disable the module", DisableModes.Finish);

    public static final NumberSetting<Integer> blocksPerTick = new NumberSetting<>("BPT", "Blocks per tick to place", 1, 1, 10, 0);
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range for a target to be found", 1.0, 4.4, 10.0, 1);

    public static final Setting<Boolean> timeout = new Setting<>("Timeout", "Allows the module to timeout and disable", true);
    public static final NumberSetting<Double> timeoutTicks = new NumberSetting<>("Timeout Ticks", "Ticks that have to pass to timeout", 1.0, 15.0, 20.0, 1);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the block placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the block placements", new Color(15, 60, 231, 201));

    public AutoTrap() {
        this.addSettings(
                placeMode,
                disableMode,
                blocksPerTick,
                targetRange,
                timeout,
                timeoutTicks,
                renderPlace
        );
    }

    private int obsidianSlot;
    private int blocksPlaced = 0;

    private boolean hasPlaced = false;

    private BlockPos renderBlock = null;
    private EntityPlayer target = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);

        if (obsidianSlot == -1) {
            MessageUtil.sendClientMessage("No Obsidian, " + ChatFormatting.RED + "Disabling!");
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        blocksPlaced = 0;
        hasPlaced = false;
        renderBlock = null;
        target = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        target = TargetUtil.getClosestPlayer(targetRange.getValue());

        if (timeout.getValue()) {
            if (this.isEnabled() && disableMode.getValue() != DisableModes.Never) {
                if (mc.player.ticksExisted % timeoutTicks.getValue() == 0) {
                    this.toggle();
                }
            }
        } else {
            if (hasPlaced == true && disableMode.getValue() == DisableModes.Finish) {
                this.toggle();
            }
        }

        blocksPlaced = 0;

        for (Vec3d vec3d : getPlaceType()) {
            if (target != null) {
                BlockPos blockPos = new BlockPos(vec3d.add(target.getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                    final int oldInventorySlot = mc.player.inventory.currentItem;

                    if (obsidianSlot != -1) {
                        mc.player.inventory.currentItem = obsidianSlot;
                    }

                    if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                        PlaceUtil.placeBlock(blockPos);
                    }

                    renderBlock = new BlockPos(vec3d.add(target.getPositionVector()));

                    mc.player.inventory.currentItem = oldInventorySlot;

                    blocksPlaced++;

                    if (blocksPlaced == blocksPerTick.getValue() && disableMode.getValue() != DisableModes.Never) return;
                }
            }
        }

        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
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
        if (target != null) {
            return target.getName();
        }

        return "";
    }

    private final List<Vec3d> fullTrap = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(1, 1, 0),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 2, -1),
            new Vec3d(0, 2, 1),
            new Vec3d(0, 2, 0)
    ));

    private final List<Vec3d> cityTrap = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(1, 1, 0),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 2, -1),
            new Vec3d(0, 2, 1),
            new Vec3d(0, 2, 0)
    ));

    private List<Vec3d> getPlaceType() {
        if (placeMode.getValue() == PlaceModes.Full) {
            return fullTrap;
        } else {
            return cityTrap;
        }
    }

    public enum PlaceModes {
        Full,
        City
    }

    public enum DisableModes {
        Finish,
        Never
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

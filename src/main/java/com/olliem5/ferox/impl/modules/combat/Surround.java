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
import com.olliem5.ferox.api.util.world.PlaceUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayer;
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

@FeroxModule(name = "Surround", description = "Surrounds you with obsidian to minimize crystal damage", category = Category.Combat)
public final class Surround extends Module {
    public static final Setting<PlaceModes> placeMode = new Setting<>("Place", "The style of surround to place", PlaceModes.Full);
    public static final Setting<DisableModes> disableMode = new Setting<>("Disable", "When to disable the module", DisableModes.Finish);
    public static final Setting<BlockModes> blockMode = new Setting<>("Block", "The block to surround with", BlockModes.Obsidian);

    public static final NumberSetting<Integer> blocksPerTick = new NumberSetting<>("BPT", "Blocks per tick to place", 1, 1, 10, 0);
    public static final Setting<Boolean> centerPlayer = new Setting<>("Center Player", "Center the player on the block for better placements", true);

    public static final Setting<Boolean> rotate = new Setting<>("Rotate", "Allow for rotations", true);
    public static final Setting<RotationModes> rotateMode = new Setting<>(rotate, "Mode", "The mode to use for rotations", RotationModes.Packet);

    public static final Setting<Boolean> timeout = new Setting<>("Timeout", "Allows the module to timeout and disable", true);
    public static final NumberSetting<Double> timeoutTicks = new NumberSetting<>(timeout, "Ticks", "Ticks that have to pass to timeout", 1.0, 15.0, 20.0, 1);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the block placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the block placements", new Color(182, 40, 226, 186));

    public Surround() {
        this.addSettings(
                placeMode,
                disableMode,
                blockMode,
                blocksPerTick,
                centerPlayer,
                rotate,
                timeout,
                renderPlace
        );
    }

    private int obsidianSlot;
    private int enderChestSlot;
    private int blocksPlaced = 0;

    private boolean hasPlaced = false;

    private Vec3d center = Vec3d.ZERO;

    private BlockPos renderBlock = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);
        enderChestSlot = InventoryUtil.getHotbarBlockSlot(Blocks.ENDER_CHEST);

        if (getBlockSlot() == -1) {
            MessageUtil.sendClientMessage("No " + getBlockText() + ", " + ChatFormatting.RED + "Disabling!");
            this.disable();
        } else {
            if (centerPlayer.getValue()) {
                mc.player.motionX = 0.0;
                mc.player.motionZ = 0.0;

                center = PlayerUtil.getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);

                mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
                mc.player.setPosition(center.x, center.y, center.z);
            }
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        blocksPlaced = 0;
        hasPlaced = false;
        center = Vec3d.ZERO;
        renderBlock = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (timeout.getValue()) {
            if (this.isEnabled() && disableMode.getValue() != DisableModes.Never) {
                if (mc.player.ticksExisted % timeoutTicks.getValue() == 0) {
                    this.toggle();
                }
            }
        } else if (hasPlaced && disableMode.getValue() == DisableModes.Finish) {
            this.toggle();
        } else {
            if (!mc.player.onGround) {
                this.toggle();
            }
        }

        blocksPlaced = 0;

        for (Vec3d vec3d : getPlaceType()) {
            BlockPos blockPos = new BlockPos(vec3d.add(mc.player.getPositionVector()));

            if (mc.world.getBlockState(blockPos).getBlock().isReplaceable(mc.world, blockPos)) {
                int oldInventorySlot = mc.player.inventory.currentItem;

                if (getBlockSlot() != -1) {
                    mc.player.inventory.currentItem = getBlockSlot();
                }

                if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(getBlockBlock())) {
                    PlaceUtil.placeBlock(blockPos, rotate.getValue(), rotateMode.getValue() == RotationModes.Packet);
                }

                renderBlock = new BlockPos(vec3d.add(mc.player.getPositionVector()));

                mc.player.inventory.currentItem = oldInventorySlot;

                blocksPlaced++;

                if (blocksPlaced == blocksPerTick.getValue() && disableMode.getValue() != DisableModes.Never) return;
            }
        }

        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
    }

    private String getBlockText() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return "Obsidian";
            case EnderChest:
                return "Ender Chests";
        }

        return "Obsidian";
    }

    private int getBlockSlot() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return obsidianSlot;
            case EnderChest:
                return enderChestSlot;
        }

        return obsidianSlot;
    }

    private Block getBlockBlock() {
        switch (blockMode.getValue()) {
            case Obsidian:
                return Blocks.OBSIDIAN;
            case EnderChest:
                return Blocks.ENDER_CHEST;
        }

        return Blocks.OBSIDIAN;
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        if (renderPlace.getValue() && renderBlock != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            RenderUtil.draw(renderBlock, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
        }
    }

    @Override
    public String getArraylistInfo() {
        return placeMode.getValue().toString();
    }

    private final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> antiCitySurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1),
            new Vec3d(2, 0, 0),
            new Vec3d(-2, 0, 0),
            new Vec3d(0, 0, 2),
            new Vec3d(0, 0, -2),
            new Vec3d(3, 0, 0),
            new Vec3d(-3, 0, 0),
            new Vec3d(0, 0, 3),
            new Vec3d(0, 0, -3)
    ));

    private List<Vec3d> getPlaceType() {
        if (placeMode.getValue() == PlaceModes.Standard) {
            return standardSurround;
        } else if (placeMode.getValue() == PlaceModes.Full) {
            return fullSurround;
        }

        return antiCitySurround;
    }

    public enum PlaceModes {
        Standard,
        Full,
        AntiCity
    }

    public enum DisableModes {
        Finish,
        Jump,
        Never
    }

    public enum BlockModes {
        Obsidian,
        EnderChest
    }

    public enum RotationModes {
        Packet,
        Legit
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

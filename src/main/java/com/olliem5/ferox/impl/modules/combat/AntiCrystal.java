package com.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.api.util.math.CooldownUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.Objects;

/**
 * TODO: AutoSwitch
 * TODO: Stop pressure plates from being placed right after crystal explodes
 */

/**
 * @author olliem5
 */

@FeroxModule(name = "AntiCrystal", description = "Makes crystals do very little damage, by using pressure plates", category = Category.Combat)
public final class AntiCrystal extends Module {
    public static final NumberSetting<Double> placeRange = new NumberSetting<>("Place Range", "The range to place pressure plates at", 0.0, 5.5, 10.0, 1);
    public static final NumberSetting<Integer> placeDelay = new NumberSetting<>("Place Delay", "The delay between places", 0, 2, 20, 0);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the block placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the block placements", new Color(66, 199, 16, 201));

    public AntiCrystal() {
        this.addSettings(
                placeRange,
                placeDelay,
                renderPlace
        );
    }

    private int woodenPressurePlateSlot;
    private int heavyWeightedPressurePlateSlot;
    private int lightWeightedPressurePlateSlot;
    private int stonePressurePlateSlot;

    private final CooldownUtil placeTimer = new CooldownUtil();

    private BlockPos renderBlock = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        handlePressurePlates(true);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        renderBlock = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(entity -> mc.player.getDistance(entity) <= placeRange.getValue())
                .filter(entity -> !hasPressurePlate((EntityEnderCrystal) entity))
                .min(Comparator.comparing(entity -> mc.player.getDistance(entity)))
                .orElse(null);

        handlePressurePlates(false);

        if (entityEnderCrystal != null) { //This shouldn't be needed....?
            if (placeTimer.passed(placeDelay.getValue() * 60)) {
                if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE) || mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE) || mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE) || mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.STONE_PRESSURE_PLATE)) {
                    renderBlock = new BlockPos(entityEnderCrystal.posX, entityEnderCrystal.posY -1, entityEnderCrystal.posZ);
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(entityEnderCrystal.getPosition(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                }

                placeTimer.reset();
            }
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

    private void handlePressurePlates(boolean search) {
        if (search) {
            woodenPressurePlateSlot = InventoryUtil.getHotbarBlockSlot(Blocks.WOODEN_PRESSURE_PLATE);
            heavyWeightedPressurePlateSlot = InventoryUtil.getHotbarBlockSlot(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
            lightWeightedPressurePlateSlot = InventoryUtil.getHotbarBlockSlot(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
            stonePressurePlateSlot = InventoryUtil.getHotbarBlockSlot(Blocks.STONE_PRESSURE_PLATE);

            if (woodenPressurePlateSlot == -1 && heavyWeightedPressurePlateSlot == -1 && lightWeightedPressurePlateSlot == -1 && stonePressurePlateSlot == -1) {
                MessageUtil.sendClientMessage("No Pressure Plates, " + ChatFormatting.RED + "Disabling!");
                this.toggle();
            }
        } else {
            if (woodenPressurePlateSlot != -1) {
                mc.player.inventory.currentItem = woodenPressurePlateSlot;
            } else if (heavyWeightedPressurePlateSlot != -1) {
                mc.player.inventory.currentItem = heavyWeightedPressurePlateSlot;
            } else if (lightWeightedPressurePlateSlot != -1) {
                mc.player.inventory.currentItem = lightWeightedPressurePlateSlot;
            } else if (stonePressurePlateSlot != -1) {
                mc.player.inventory.currentItem = stonePressurePlateSlot;
            }
        }
    }

    private boolean hasPressurePlate(EntityEnderCrystal entityEnderCrystal) {
        return mc.world.getBlockState(entityEnderCrystal.getPosition()).getBlock() == Blocks.WOODEN_PRESSURE_PLATE;
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}

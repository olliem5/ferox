package me.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.client.MessageUtil;
import me.olliem5.ferox.api.util.minecraft.InventoryUtil;
import me.olliem5.ferox.api.util.minecraft.PlaceUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "AutoTrap", description = "Automatically traps enemies with obsidian", category = Category.COMBAT)
public class AutoTrap extends Module {
    public static Setting<PlaceModes> placeMode = new Setting<>("Place", PlaceModes.Full);
    public static Setting<DisableModes> disableMode = new Setting<>("Disable", DisableModes.Finish);

    public static NumberSetting<Integer> blocksPerTick = new NumberSetting<>("BPT", 1, 1, 10, 0);
    public static NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", 1.0, 4.4, 10.0, 1);

    public static Setting<Boolean> timeout = new Setting<>("Timeout", true);
    public static NumberSetting<Double> timeoutTicks = new NumberSetting<>("Timeout Ticks", 1.0, 15.0, 20.0, 1);

    public static Setting<Boolean> renderBlock = new Setting<>("Render", true);
    public static Setting<Color> renderColour = new Setting<>(renderBlock, "Render Colour", new Color(15, 60, 231, 201));

    public AutoTrap() {
        this.addSetting(placeMode);
        this.addSetting(disableMode);
        this.addSetting(blocksPerTick);
        this.addSetting(targetRange);
        this.addSetting(timeout);
        this.addSetting(timeoutTicks);
        this.addSetting(renderBlock);
        this.addSetting(renderColour);
    }

    private int obsidianSlot;
    private int blocksPlaced = 0;

    private boolean hasPlaced = false;

    @Override
    public void onEnable() {
        obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);

        if (obsidianSlot == -1) {
            MessageUtil.sendClientMessage("No Obsidian, " + ChatFormatting.RED + "Disabling!");
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        blocksPlaced = 0;
        hasPlaced = false;
    }

    public void onUpdate() {
        if (nullCheck()) return;

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
            final EntityPlayer target = getClosestPlayer();

            if (target != null) {
                BlockPos blockPos = new BlockPos(vec3d.add(getClosestPlayer().getPositionVector()));

                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                    int oldInventorySlot = mc.player.inventory.currentItem;

                    if (obsidianSlot != -1) {
                        mc.player.inventory.currentItem = obsidianSlot;
                    }

                    PlaceUtil.placeBlock(blockPos);
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

    private EntityPlayer getClosestPlayer() {
        EntityPlayer closestPlayer = null;

        for (final EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer != mc.player) {
                final double distance = mc.player.getDistance(entityPlayer);

                if (distance < targetRange.getValue()) {
                    closestPlayer = entityPlayer;
                }
            }
        }

        return closestPlayer;
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
}

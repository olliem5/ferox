package us.ferox.client.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.client.MessageUtil;
import us.ferox.client.api.util.minecraft.InventoryUtil;
import us.ferox.client.api.util.minecraft.PlaceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Surround", description = "Surrounds you with blocks to minimize crystal damage", category = Category.COMBAT)
public class Surround extends Module {
    public static Setting<PlaceModes> placeMode = new Setting<>("Place", PlaceModes.Full);
    public static Setting<DisableModes> disableMode = new Setting<>("Disable", DisableModes.Finish);

    public static NumberSetting<Integer> blocksPerTick = new NumberSetting<>("BPT", 1, 1, 10);
    public static Setting<Boolean> centerPlayer = new Setting<>("Center Player", true);

    public static Setting<Boolean> timeout = new Setting<>("Timeout", true);
    public static NumberSetting<Double> timeoutTicks = new NumberSetting<>("Timeout Ticks", 1.0, 15.0, 20.0);

    private int obsidianSlot;

    public Surround() {
        this.addSetting(placeMode);
        this.addSetting(disableMode);
        this.addSetting(blocksPerTick);
        this.addSetting(centerPlayer);
        this.addSetting(timeout);
        this.addSetting(timeoutTicks);
    }

    private int blocksPlaced = 0;
    private boolean hasPlaced = false;
    private Vec3d center = Vec3d.ZERO;

    @Override
    public void onEnable() {
         obsidianSlot = InventoryUtil.getHotbarBlockSlot(Blocks.OBSIDIAN);

        if (obsidianSlot == -1) {
            MessageUtil.sendClientMessage("No Obsidian, " + ChatFormatting.RED + "Disabling!");
            this.toggle();
        } else {
            if (centerPlayer.getValue()) {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;

                center = getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);

                mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
                mc.player.setPosition(center.x, center.y, center.z);
            }
        }
    }

    @Override
    public void onDisable() {
        blocksPlaced = 0;
        hasPlaced = false;
        center = Vec3d.ZERO;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (timeout.getValue()) {
            if (this.isEnabled() && disableMode.getValue() != DisableModes.Never) {
                if (mc.player.ticksExisted % timeoutTicks.getValue() == 0) {
                    this.toggle();
                }
            }
        } else if (hasPlaced == true && disableMode.getValue() == DisableModes.Finish) {
            this.toggle();
        } else {
            if (!mc.player.onGround) {
                this.toggle();
            }
        }

        blocksPlaced = 0;

        for (Vec3d placePositions : getPlaceType()) {
            BlockPos blockPos = new BlockPos(placePositions.add(mc.player.getPositionVector()));

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

        if (blocksPlaced == 0) {
            hasPlaced = true;
        }
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

    public Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
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
}

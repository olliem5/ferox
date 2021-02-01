package me.olliem5.ferox.impl.modules.misc;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(name = "FastUse", description = "Allows you to use certain things faster", category = Category.MISC)
public class FastUse extends Module {
    public static Setting<Boolean> bows = new Setting<>("Bows", true);
    public static Setting<Boolean> offhandBows = new Setting<>("Offhand Bows", true);
    public static Setting<Boolean> fishingRods = new Setting<>("Fishing Rods", false);
    public static Setting<Boolean> experience = new Setting<>("Experience", true);
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static Setting<Boolean> throwables = new Setting<>("Throwables", false);
    public static Setting<Boolean> blocks = new Setting<>("Blocks", false);
    public static Setting<Boolean> other = new Setting<>("Other", false);

    public FastUse() {
        this.addSetting(bows);
        this.addSetting(fishingRods);
        this.addSetting(experience);
        this.addSetting(crystals);
        this.addSetting(throwables);
        this.addSetting(blocks);
        this.addSetting(other);
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (bows.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.stopActiveHand();
                }
            }
        }

        if (offhandBows.getValue()) {
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemBow) {
                if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    mc.player.stopActiveHand();
                }
            }
        }

        if (fishingRods.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (experience.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (crystals.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (throwables.getValue()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEgg || mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderPearl || mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderEye || mc.player.getHeldItemMainhand().getItem() instanceof ItemFireworkCharge || mc.player.getHeldItemMainhand().getItem() instanceof ItemSnowball || mc.player.getHeldItemMainhand().getItem() instanceof ItemSplashPotion) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (blocks.getValue()) {
            if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if (other.getValue()) {
            if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock || mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod)) {
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}

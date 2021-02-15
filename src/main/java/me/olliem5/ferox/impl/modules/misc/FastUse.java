package me.olliem5.ferox.impl.modules.misc;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * @author olliem5
 */

@FeroxModule(name = "FastUse", description = "Allows you to use certain things faster", category = Category.Misc)
public final class FastUse extends Module {
    public static final Setting<Boolean> bows = new Setting<>("Bows", "Allows bows to be used quickly", true);
    public static final Setting<Boolean> offhandBows = new Setting<>("Offhand Bows", "Allows bows to be used quickly in the offhand", true);
    public static final Setting<Boolean> fishingRods = new Setting<>("Fishing Rods", "Allows fishing rods to be used quickly", false);
    public static final Setting<Boolean> experience = new Setting<>("Experience", "Allows experience bottles to be used quickly", true);
    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows crystals to be used quickly", false);
    public static final Setting<Boolean> throwables = new Setting<>("Throwables", "Allows throwable items to be used quickly", false);
    public static final Setting<Boolean> blocks = new Setting<>("Blocks", "Allows blocks to be used quickly", false);
    public static final Setting<Boolean> other = new Setting<>("Other", "Allows other things to be used quickly", false);

    public FastUse() {
        this.addSettings(
                bows,
                offhandBows,
                fishingRods,
                experience,
                crystals,
                throwables,
                blocks,
                other
        );
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

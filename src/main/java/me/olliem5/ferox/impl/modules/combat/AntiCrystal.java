package me.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.util.client.MessageUtil;
import me.olliem5.ferox.api.util.math.CooldownUtil;
import me.olliem5.ferox.api.util.minecraft.InventoryUtil;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.util.Comparator;

/**
 * TODO: Checks for all types of pressure plates
 * TODO: Stop pressure plates from being placed right after crystal explodes
 */

@FeroxModule(name = "AntiCrystal", description = "Makes crystals do very little damage, by using pressure plates", category = Category.COMBAT)
public class AntiCrystal extends Module {
    public static NumberSetting<Double> placeRange = new NumberSetting<>("Place Range", "The range to place pressure plates at", 0.0, 5.5, 10.0, 1);
    public static NumberSetting<Integer> placeDelay = new NumberSetting<>("Place Delay", "The delay between places", 0, 2, 20, 0);

    public AntiCrystal() {
        this.addSettings(
                placeRange,
                placeDelay
        );
    }

    private int pressurePlateSlot;

    private CooldownUtil placeTimer = new CooldownUtil();

    @Override
    public void onEnable() {
        pressurePlateSlot = InventoryUtil.getHotbarBlockSlot(Blocks.WOODEN_PRESSURE_PLATE);

        if (pressurePlateSlot == -1) {
            MessageUtil.sendClientMessage("No Pressure Plate, " + ChatFormatting.RED + "Disabling!");
            this.toggle();
        }
    }

    public void onUpdate() {
        if (nullCheck()) return;

        EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                .filter(entity -> entity != null)
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(entity -> mc.player.getDistance(entity) <= placeRange.getValue())
                .filter(entity -> !hasPressurePlate((EntityEnderCrystal) entity))
                .min(Comparator.comparing(entity -> mc.player.getDistance(entity)))
                .orElse(null);

        if (entityEnderCrystal != null) {
            if (pressurePlateSlot != -1) {
                mc.player.inventory.currentItem = pressurePlateSlot;
            }

            if (placeTimer.passed(placeDelay.getValue() * 60)) {
                if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE)) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(entityEnderCrystal.getPosition(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                }

                placeTimer.reset();
            }
        }
    }

    private boolean hasPressurePlate(EntityEnderCrystal entityEnderCrystal) {
        return mc.world.getBlockState(entityEnderCrystal.getPosition()).getBlock() == Blocks.WOODEN_PRESSURE_PLATE;
    }
}

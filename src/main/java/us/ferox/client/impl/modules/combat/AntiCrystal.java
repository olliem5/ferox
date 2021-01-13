package us.ferox.client.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.util.client.CooldownUtil;
import us.ferox.client.api.util.client.MessageUtil;
import us.ferox.client.api.util.minecraft.InventoryUtil;

import java.util.Comparator;

/**
 * Credit - Max for the idea
 *
 * TODO: Checks for all types of pressure plates
 * TODO: Stop pressure plates from being placed right after crystal explodes
 */

@ModuleInfo(name = "AntiCrystal", description = "Makes crystals do very little damage, by using pressure plates", category = Category.COMBAT)
public class AntiCrystal extends Module {
    public static NumberSetting<Double> placeRange = new NumberSetting<>("Place Range", 0.0, 5.5, 10.0);
    public static NumberSetting<Integer> placeDelay = new NumberSetting<>("Place Delay", 0, 2, 20);

    public AntiCrystal() {
        this.addSetting(placeRange);
        this.addSetting(placeDelay);
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

            if (placeTimer.passed(placeDelay.getValue() * 100)) {
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

    public enum ItemModes {
        PressurePlate
    }
}

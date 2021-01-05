package us.ferox.client.impl.modules.combat;

import net.minecraft.init.Items;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.minecraft.InventoryUtil;

/**
 * TODO: PacketSwitch / GhostSwitch
 * TODO: Crystal Range & Damage Checks
 */

@ModuleInfo(name = "Offhand", description = "Manages the item in your offhand", category = Category.COMBAT)
public class Offhand extends Module {
    public static Setting<OffhandModes> offhandMode = new Setting<>("Mode", OffhandModes.Crystal);
    public static NumberSetting<Double> switchHealth = new NumberSetting<>("Totem Health", 1.0, 16.0, 36.0);

    public static Setting<Boolean> swordGapple = new Setting<>("Sword Gapple", true);
    public static Setting<Boolean> elytraTotem = new Setting<>("Elytra Totem", true);

    public static Setting<Boolean> fallTotem = new Setting<>("Fall Totem", true);
    public static NumberSetting<Double> fallDistance = new NumberSetting<>("Fall Distance", 1.0, 15.0, 100.0);

    public Offhand() {
        this.addSetting(offhandMode);
        this.addSetting(switchHealth);
        this.addSetting(swordGapple);
        this.addSetting(elytraTotem);
        this.addSetting(fallTotem);
        this.addSetting(fallDistance);
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (switchHealth.getValue() >= (mc.player.getHealth() + mc.player.getAbsorptionAmount()) || offhandMode.getValue() == OffhandModes.Totem || (elytraTotem.getValue() && mc.player.isElytraFlying()) || (fallTotem.getValue() && mc.player.fallDistance >= fallDistance.getValue() && !mc.player.isElytraFlying())) {
            if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                InventoryUtil.offhandItem(Items.TOTEM_OF_UNDYING);
            }
        } else if (swordGapple.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
            if (switchHealth.getValue() < (mc.player.getHealth() + mc.player.getAbsorptionAmount())) {
                if (mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                    InventoryUtil.offhandItem(Items.GOLDEN_APPLE);
                }
            } else {
                if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                    InventoryUtil.offhandItem(Items.TOTEM_OF_UNDYING);
                }
            }
        } else {
            switch (offhandMode.getValue()) {
                case Crystal:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                        InventoryUtil.offhandItem(Items.END_CRYSTAL);
                    }
                    break;
                case Gapple:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                        InventoryUtil.offhandItem(Items.GOLDEN_APPLE);
                    }
                    break;
                case Totem:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                        InventoryUtil.offhandItem(Items.TOTEM_OF_UNDYING);
                    }
                    break;
                case Bed:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.BED) {
                        InventoryUtil.offhandItem(Items.BED);
                    }
                    break;
                case Chorus:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.CHORUS_FRUIT) {
                        InventoryUtil.offhandItem(Items.CHORUS_FRUIT);
                    }
                    break;
                case Pearl:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.ENDER_PEARL) {
                        InventoryUtil.offhandItem(Items.ENDER_PEARL);
                    }
                    break;
                case Potion:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.POTIONITEM) {
                        InventoryUtil.offhandItem(Items.POTIONITEM);
                    }
                    break;
                case Bow:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.BOW) {
                        InventoryUtil.offhandItem(Items.BOW);
                    }
                    break;
                case XP:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.EXPERIENCE_BOTTLE) {
                        InventoryUtil.offhandItem(Items.EXPERIENCE_BOTTLE);
                    }
                    break;
            }
        }
    }

    public enum OffhandModes {
        Crystal,
        Gapple,
        Totem,
        Bed,
        Chorus,
        Pearl,
        Potion,
        Bow,
        XP
    }
}

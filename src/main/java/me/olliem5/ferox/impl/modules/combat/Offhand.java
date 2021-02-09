package me.olliem5.ferox.impl.modules.combat;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.player.InventoryUtil;
import net.minecraft.init.Items;

/**
 * TODO: PacketSwitch / GhostSwitch
 * TODO: Crystal Range & Damage Checks
 */

/**
 * @author olliem5
 */

@FeroxModule(name = "Offhand", description = "Manages the item in your offhand", category = Category.COMBAT)
public final class Offhand extends Module {
    public static final Setting<OffhandModes> offhandMode = new Setting<>("Mode", "The item to put in your offhand", OffhandModes.Crystal);
    public static final NumberSetting<Double> switchHealth = new NumberSetting<>("Totem Health", "Health to be at to switch to a totem", 1.0, 16.0, 36.0, 1);

    public static final Setting<Boolean> swordGapple = new Setting<>("Sword Gapple", "Puts a gapple in your offhand when holding a sword", true);
    public static final Setting<Boolean> elytraTotem = new Setting<>("Elytra Totem", "Puts a totem in your offhand if you are wearing an elytra", true);

    public static final Setting<Boolean> fallTotem = new Setting<>("Fall Totem", "Puts a totem in your offhand if you are falling", true);
    public static final NumberSetting<Double> fallDistance = new NumberSetting<>("Fall Distance", "Distance for the totem when falling", 1.0, 15.0, 100.0, 1);

    public Offhand() {
        this.addSettings(
                offhandMode,
                switchHealth,
                swordGapple,
                elytraTotem,
                fallTotem,
                fallDistance
        );
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
                case SpawnEgg:
                    if (mc.player.getHeldItemOffhand().getItem() != Items.SPAWN_EGG) {
                        InventoryUtil.offhandItem(Items.SPAWN_EGG);
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
        XP,
        SpawnEgg
    }
}

package com.olliem5.ferox.impl.modules.combat;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.math.CooldownUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import com.olliem5.ferox.api.util.player.TargetUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

/**
 * @author olliem5
 */

@FeroxModule(name = "Aura", description = "Automatically attacks players and entities in range", category = Category.Combat)
public final class Aura extends Module {
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range that players can be attacked in", 0.0, 5.0, 10.0, 1);

    public static final Setting<Boolean> attack = new Setting<>("Attack", "Allows Aura to attack", true);
    public static final Setting<AttackModes> attackMode = new Setting<>(attack, "Attack", "The mode for Aura attacks", AttackModes.Packet);
    public static final Setting<Boolean> cooldown = new Setting<>(attack, "Cooldown", "Allows for cooldown between swings", true);
    public static final Setting<Boolean> swing = new Setting<>(attack, "Swing", "Swings the player's arm after an attack", true);

    public static final Setting<Boolean> weapon = new Setting<>("Weapon", "Weapon settings for Aura", true);
    public static final Setting<WeaponModes> weaponMode = new Setting<>(weapon, "Weapon", "The weapon that Aura will use", WeaponModes.Sword);
    public static final Setting<Boolean> weaponOnly = new Setting<>(weapon, "Weapon Only", "Only allows attacking when you are holding your designated weapon", true);
    public static final Setting<Boolean> autoSwitch = new Setting<>(weapon, "Auto Switch", "Automatically switches to your designated weapon", true);

    public static final Setting<Boolean> pause = new Setting<>("Pause", "Allows Aura to pause", true);
    public static final Setting<Boolean> pauseWithCrystals = new Setting<>(pause, "With Crystals", "Pauses attacking if AutoCrystal is enabled or you are holding crystals in your main hand", false);
    public static final Setting<Boolean> pauseWhileEating = new Setting<>(pause, "While Eating", "Pauses attacking if you are eating", false);

    public Aura() {
        this.addSettings(
                targetRange,
                attack,
                weapon,
                pause
        );
    }

    private EntityPlayer target = null;

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        target = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        target = TargetUtil.getClosestPlayer(targetRange.getValue());

        doKillAura(target);
    }

    public void doKillAura(EntityPlayer entityPlayer) {
        if (weaponOnly.getValue() && weaponMode.getValue() == WeaponModes.Sword && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;

        if (weaponOnly.getValue() && weaponMode.getValue() == WeaponModes.Axe && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) return;

        if (pauseWithCrystals.getValue() && ModuleManager.getModuleByName("AutoCrystal").isEnabled() || mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) return;

        if (pauseWhileEating.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && mc.player.isHandActive()) return;

        if (target != null) {
            if (autoSwitch.getValue()) {
                switch (weaponMode.getValue()) {
                    case Sword:
                        InventoryUtil.switchToSlot(ItemSword.class);
                        break;
                    case Axe:
                        InventoryUtil.switchToSlot(ItemAxe.class);
                        break;
                }
            }

            if (cooldown.getValue()) {
                if (attackMode.getValue() == AttackModes.Normal) {
                    if (mc.player.getCooledAttackStrength(0) >= 1) {
                        mc.playerController.attackEntity(mc.player, entityPlayer);

                        if (swing.getValue()) {
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                } else {
                   mc.player.connection.sendPacket(new CPacketUseEntity(entityPlayer));

                   if (swing.getValue()) {
                      mc.player.swingArm(EnumHand.MAIN_HAND);
                   }
                    
                   mc.player.resetCooldown();
                }
            } else {
                if (attackMode.getValue() == AttackModes.Normal) {
                    mc.playerController.attackEntity(mc.player, entityPlayer);
                } else {
                    mc.player.connection.sendPacket(new CPacketUseEntity(entityPlayer));
                }

                if (swing.getValue()) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    public String getArraylistInfo() {
        if (target != null) {
            return target.getName();
        }

        return "";
    }

    public enum AttackModes {
        Normal,
        Packet
    }

    public enum WeaponModes {
        Sword,
        Axe
    }
}

package us.ferox.client.impl.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.client.CooldownUtil;
import us.ferox.client.api.util.minecraft.InventoryUtil;
import us.ferox.client.api.util.module.CrystalUtil;
import us.ferox.client.api.util.packet.RotationUtil;

import java.util.Comparator;

@ModuleInfo(name = "AutoCrystal", description = "Places and destroys end crystals to kill enemies", category = Category.COMBAT)
public class AutoCrystal extends Module {
    public static Setting<LogicModes> logicMode = new Setting<>("Logic", LogicModes.Breakplace);
    public static Setting<PlaceModes> placeMode = new Setting<>("Place", PlaceModes.Single);
    public static Setting<BreakModes> breakMode = new Setting<>("Break", BreakModes.Nearest);
    public static Setting<BreakTypes> breakType = new Setting<>("Break Type", BreakTypes.Swing);
    public static Setting<SwingModes> swingMode = new Setting<>("Swing", SwingModes.Mainhand);

    public static NumberSetting<Integer> placeDelay = new NumberSetting<>("Place Delay", 0, 2, 20, 0);
    public static NumberSetting<Integer> breakDelay = new NumberSetting<>("Break Delay", 0, 2, 20, 0);

    public static NumberSetting<Double> placeRange = new NumberSetting<>("Place Range", 0.0, 5.5, 10.0, 1);
    public static NumberSetting<Double> breakRange = new NumberSetting<>("Break Range", 0.0, 5.5, 10.0, 1);
    public static NumberSetting<Double> enemyRange = new NumberSetting<>("Enemy Range", 1.0, 15.0, 50.0, 1);
    public static NumberSetting<Double> wallsRange = new NumberSetting<>("Walls Range", 0.0, 3.5, 10.0, 1);

    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> raytrace = new Setting<>("Raytrace", true);
    public static Setting<Boolean> antiWeakness = new Setting<>("Anti Weakness", true);

    public static Setting<Boolean> syncBreak = new Setting<>("Sync Break", true);
    public static Setting<Boolean> reloadCrystal = new Setting<>("Reload Crystal", true);
    public static Setting<Boolean> antiDesync = new Setting<>("Anti Desync", true);
    public static NumberSetting<Integer> breakAttempts = new NumberSetting<>("Break Attempts", 1, 1, 5, 0);

    public static Setting<Boolean> antiSuicide = new Setting<>("Anti Suicide", true);
    public static NumberSetting<Double> antiSuicideHealth = new NumberSetting<>("Anti Suicide HP", 1.0, 15.0, 36.0, 1);
    public static NumberSetting<Double> minDamage = new NumberSetting<>("Min Damage", 0.0, 7.0, 36.0, 1);
    public static NumberSetting<Double> maxSelfDamage = new NumberSetting<>("Max Self Damage", 0.0, 8.0, 36.0, 1);
    public static NumberSetting<Double> faceplaceHP = new NumberSetting<>("Faceplace HP", 0.0, 8.0, 36.0, 1);

    public AutoCrystal() {
        this.addSetting(logicMode);
        this.addSetting(placeMode);
        this.addSetting(breakMode);
        this.addSetting(breakType);
        this.addSetting(swingMode);
        this.addSetting(placeDelay);
        this.addSetting(breakDelay);
        this.addSetting(placeRange);
        this.addSetting(breakRange);
        this.addSetting(enemyRange);
        this.addSetting(wallsRange);
        this.addSetting(rotate);
        this.addSetting(raytrace);
        this.addSetting(syncBreak);
        this.addSetting(reloadCrystal);
        this.addSetting(antiDesync);
        this.addSetting(breakAttempts);
        this.addSetting(antiSuicide);
        this.addSetting(antiSuicideHealth);
        this.addSetting(minDamage);
        this.addSetting(maxSelfDamage);
        this.addSetting(faceplaceHP);
    }

    private CooldownUtil breakTimer = new CooldownUtil();
    private CooldownUtil placeTimer = new CooldownUtil();

    private Entity entityTarget = null;
    private BlockPos blockTarget = null;

    @Override
    public void onDisable() {
        entityTarget = null;
        blockTarget = null;
        RotationUtil.resetRotation();
    }

    public void onUpdate() {
        if (nullCheck()) return;

        breakCrystal();
    }

    private void breakCrystal() {
        if (breakMode.getValue() != BreakModes.None) {
            EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                    .filter(entity -> entity != null)
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(entity -> mc.player.getDistance(entity) <= breakRange.getValue())
                    .min(Comparator.comparing(entity -> mc.player.getDistance(entity)))
                    .orElse(null);

            if (entityEnderCrystal != null && breakTimer.passed(breakDelay.getValue() * 60)) {
                if (antiSuicide.getValue() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= antiSuicideHealth.getValue()) return;

                if (!mc.player.canEntityBeSeen(entityEnderCrystal) && mc.player.getDistance(entityEnderCrystal) > wallsRange.getValue()) return;

                if (breakMode.getValue() == BreakModes.Nearest) {
                    if (rotate.getValue()) {
                        RotationUtil.lookAtPacket(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, mc.player);
                    }

                    if (antiWeakness.getValue() && mc.player.isPotionActive(Potion.getPotionById(18))) {
                        InventoryUtil.switchToSlot(ItemSword.class);
                    }

                    for (int i = 0; i < breakAttempts.getValue(); i++) {
                        if (breakType.getValue() == BreakTypes.Packet) {
                            CrystalUtil.attackCrystal(entityEnderCrystal, true);
                        } else {
                            CrystalUtil.attackCrystal(entityEnderCrystal, false);
                        }
                    }

                    if (swingMode.getValue() != SwingModes.None) {
                        switch (swingMode.getValue()) {
                            case Mainhand:
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                break;
                            case Offhand:
                                mc.player.swingArm(EnumHand.OFF_HAND);
                                break;
                            case Spam:
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                break;
                            case Both:
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                mc.player.swingArm(EnumHand.OFF_HAND);
                                break;
                        }
                    }

                    if (syncBreak.getValue()) {
                        entityEnderCrystal.setDead();
                    }

                    if (reloadCrystal.getValue()) {
                        mc.world.removeAllEntities();
                        mc.world.getLoadedEntityList();
                    }
                }
            }

            breakTimer.reset();

            if (!(placeMode.getValue() == PlaceModes.Multi)) return;
        }
    }

    public enum LogicModes {
        Breakplace,
        Placebreak
    }

    public enum PlaceModes {
        Single,
        Multi,
        None
    }

    public enum BreakModes {
        Nearest,
        OnlyOwn,
        None
    }

    public enum BreakTypes {
        Swing,
        Packet
    }

    public enum SwingModes {
        Mainhand,
        Offhand,
        Spam,
        Both,
        None
    }
}

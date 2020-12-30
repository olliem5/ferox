package us.ferox.client.impl.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.client.CooldownUtil;
import us.ferox.client.api.util.module.CrystalUtil;

import java.util.Comparator;

@ModuleInfo(name = "AutoCrystal", description = "Places and destroys end crystals to kill enemies", category = Category.COMBAT)
public class AutoCrystal extends Module {
    //Modes
    public static Setting<Enum> logicMode = new Setting("Logic", LogicModes.Breakplace);
    public static Setting<Enum> placeMode = new Setting("Place", PlaceModes.Single);
    public static Setting<Enum> breakMode = new Setting("Break", BreakModes.Nearest);
    public static Setting<Enum> breakType = new Setting("Break Type", BreakTypes.Swing);
    public static Setting<Enum> swingMode = new Setting("Swing", SwingModes.Mainhand);

    //Delays
    public static NumberSetting<Integer> placeDelay = new NumberSetting("Place Delay", 0, 2, 20);
    public static NumberSetting<Integer> breakDelay = new NumberSetting("Break Delay", 0, 2, 20);

    //Ranges
    public static NumberSetting<Double> placeRange = new NumberSetting("Place Range", 0.0, 5.5, 10.0);
    public static NumberSetting<Double> breakRange = new NumberSetting("Break Range", 0.0, 5.5, 10.0);
    public static NumberSetting<Double> enemyRange = new NumberSetting("Enemy Range", 1.0, 15.0, 50.0);
    public static NumberSetting<Double> wallsRange = new NumberSetting("Walls Range", 0.0, 3.5, 10.0);

    //Booleans
    public static Setting<Boolean> rotate = new Setting("Rotate", true);
    public static Setting<Boolean> raytrace = new Setting("Raytrace", true);
    public static Setting<Boolean> antiWeakness = new Setting("Anti Weakness", true);

    //Fixes
    public static Setting<Boolean> syncBreak = new Setting("Sync Break", true);
    public static Setting<Boolean> reloadCrystal = new Setting("Reload Crystal", true);
    public static Setting<Boolean> antiDesync = new Setting("Anti Desync", true);
    public static NumberSetting<Integer> breakAttempts = new NumberSetting("Break Attempts", 1.0, 1.0, 5.0);

    //Health
    public static Setting<Boolean> antiSuicide = new Setting("Anti Suicide", true);
    public static NumberSetting<Double> antiSuicideHealth = new NumberSetting("Anti Suicide HP", 1.0, 15.0, 36.0);
    public static NumberSetting<Double> minDamage = new NumberSetting("Min Damage", 0.0, 7.0, 36.0);
    public static NumberSetting<Double> maxSelfDamage = new NumberSetting("Max Self Damage", 0.0, 8.0, 36.0);
    public static NumberSetting<Double> faceplaceHP = new NumberSetting("Faceplace HP", 0.0, 8.0, 36.0);

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

    private static CrystalUtil crystalUtil = new CrystalUtil();

    private CooldownUtil breakTimer = new CooldownUtil();
    private CooldownUtil placeTimer = new CooldownUtil();

    private Entity entityTarget = null;
    private BlockPos blockTarget = null;

    @Override
    public void onDisable() {
        entityTarget = null;
        blockTarget = null;
        crystalUtil.resetRotation();
    }

    public void onUpdate() {
        breakCrystal();
    }

    private void breakCrystal() {
        if (breakMode.getValue() != BreakModes.None) {
            EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                    .filter(entity -> entity != null)
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(entity -> mc.player.getDistance(entity) <= (double) breakRange.getValue())
                    .min(Comparator.comparing(entity -> mc.player.getDistance(entity)))
                    .orElse(null);

            if (entityEnderCrystal != null && breakTimer.passed((double) breakDelay.getValue())) {
                if (antiSuicide.getValue() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= (double) antiSuicideHealth.getValue()) return;

                if (!mc.player.canEntityBeSeen(entityEnderCrystal) && mc.player.getDistance(entityEnderCrystal) > (double) wallsRange.getValue()) return;

                if (breakMode.getValue() == BreakModes.Nearest) {
                    if (rotate.getValue()) {
                        crystalUtil.lookAtPacket(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, mc.player);
                    }

                    for (int i = 0; i < (double) breakAttempts.getValue(); i++) {
                        if (breakType.getValue() == BreakTypes.Packet) {
                            CrystalUtil.attackCrystal(entityEnderCrystal, true);
                        } else {
                            CrystalUtil.attackCrystal(entityEnderCrystal, false);
                        }
                    }

                    if (swingMode.getValue() != SwingModes.None) {
                        switch ((SwingModes) swingMode.getValue()) {
                            case Mainhand:
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                break;
                            case Offhand:
                                mc.player.swingArm(EnumHand.OFF_HAND);
                                break;
                            case Retarted:
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
        Retarted,
        Both,
        None
    }
}

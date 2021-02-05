package me.olliem5.ferox.impl.modules.combat;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.math.CooldownUtil;
import me.olliem5.ferox.api.util.minecraft.InventoryUtil;
import me.olliem5.ferox.api.util.module.CrystalUtil;
import me.olliem5.ferox.api.util.packet.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;

@FeroxModule(name = "AutoCrystal", description = "Places and destroys end crystals to kill enemies", category = Category.COMBAT)
public final class AutoCrystal extends Module {
    public static final Setting<LogicModes> logicMode = new Setting<>("Logic", "The order to perform AutoCrystal functions", LogicModes.Breakplace);
    public static final Setting<PlaceModes> placeMode = new Setting<>("Place", "The mode for crystal placing", PlaceModes.Single);
    public static final Setting<BreakModes> breakMode = new Setting<>("Break", "The mode for crystal breaking", BreakModes.Nearest);
    public static final Setting<BreakTypes> breakType = new Setting<>("Break Type", "The mode for how the crystal is broken", BreakTypes.Swing);
    public static final Setting<SwingModes> swingMode = new Setting<>("Swing", "The mode for how the player swings at the crystal", SwingModes.Mainhand);

    public static final NumberSetting<Integer> placeDelay = new NumberSetting<>("Place Delay", "The delay between crystal places", 0, 2, 20, 0);
    public static final NumberSetting<Integer> breakDelay = new NumberSetting<>("Break Delay", "The delay between crystal breaks", 0, 2, 20, 0);

    public static final NumberSetting<Double> placeRange = new NumberSetting<>("Place Range", "The range to place crystals in", 0.0, 5.5, 10.0, 1);
    public static final NumberSetting<Double> breakRange = new NumberSetting<>("Break Range", "The range to break crystals in", 0.0, 5.5, 10.0, 1);
    public static final NumberSetting<Double> enemyRange = new NumberSetting<>("Enemy Range", "The range the target can be in", 1.0, 15.0, 50.0, 1);
    public static final NumberSetting<Double> wallsRange = new NumberSetting<>("Walls Range", "The range for places through walls", 0.0, 3.5, 10.0, 1);

    public static final Setting<Boolean> rotate = new Setting<>("Rotate", "Allow rotations to crystals and blocks", true);
    public static final Setting<Boolean> raytrace = new Setting<>("Raytrace", "Allow raytracing for placements", true);
    public static final Setting<Boolean> antiWeakness = new Setting<>("Anti Weakness", "Allow switching to a sword or tool when you have weakness", true);

    public static final Setting<Boolean> syncBreak = new Setting<>("Sync Break", "Sets crystals to dead after breaking them", true);
    public static final Setting<Boolean> reloadCrystal = new Setting<>("Reload Crystal", "Reloads world entities after a crystal break", true);
    public static final Setting<Boolean> antiDesync = new Setting<>("Anti Desync", "Removes desynced crystals", true);
    public static final NumberSetting<Integer> breakAttempts = new NumberSetting<>("Break Attempts", "How many times to swing at the crystal", 1, 1, 5, 0);

    public static final Setting<Boolean> antiSuicide = new Setting<>("Anti Suicide", "Stops crystals from doing too much damage to you", true);
    public static final NumberSetting<Double> antiSuicideHealth = new NumberSetting<>("Anti Suicide HP", "Health to be at to stop you killing yourself", 1.0, 15.0, 36.0, 1);
    public static final NumberSetting<Double> minDamage = new NumberSetting<>("Min Damage", "Minimum enemy damage for a place", 0.0, 7.0, 36.0, 1);
    public static final NumberSetting<Double> maxSelfDamage = new NumberSetting<>("Max Self Damage", "Maximum self damage for a place", 0.0, 8.0, 36.0, 1);
    public static final NumberSetting<Double> faceplaceHP = new NumberSetting<>("Faceplace HP", "Enemy health to faceplace at", 0.0, 8.0, 36.0, 1);

    public AutoCrystal() {
        this.addSettings(
                logicMode,
                placeMode,
                breakMode,
                breakType,
                swingMode,
                placeDelay,
                breakDelay,
                placeRange,
                breakRange,
                enemyRange,
                wallsRange,
                rotate,
                raytrace,
                syncBreak,
                rotate,
                raytrace,
                syncBreak,
                reloadCrystal,
                antiDesync,
                breakAttempts,
                antiSuicide,
                antiSuicideHealth,
                minDamage,
                maxSelfDamage,
                faceplaceHP
        );
    }

    private final CooldownUtil breakTimer = new CooldownUtil();
    private final CooldownUtil placeTimer = new CooldownUtil();

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

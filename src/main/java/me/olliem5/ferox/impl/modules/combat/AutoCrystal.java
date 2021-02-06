package me.olliem5.ferox.impl.modules.combat;

import git.littledraily.eventsystem.Listener;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.math.CooldownUtil;
import me.olliem5.ferox.api.util.minecraft.InventoryUtil;
import me.olliem5.ferox.api.util.module.CrystalUtil;
import me.olliem5.ferox.api.util.packet.RotationUtil;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.ferox.impl.events.PacketEvent;
import me.olliem5.ferox.impl.events.WorldRenderEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

@FeroxModule(name = "AutoCrystal", description = "Places and destroys end crystals to kill enemies", category = Category.COMBAT)
public final class AutoCrystal extends Module {

    /**
     * TODO: Settings to add
     *
     * Break
     * - Sequential
     * - UnsafeSync
     *
     * Place
     * - Walls Range
     * - AutoSwitch
     * - Predict
     *
     * FacePlace
     * - Armour target mode
     * - Faceplace in hole (user & enemy)
     *
     * Calculations
     * - VerifyPlace
     */

    /**
     * Break Settings
     */

    public static final Setting<Boolean> crystalBreak = new Setting<>("Break", "Allows for crystals to be broken", true);
    public static final Setting<BreakModes> breakMode = new Setting<>(crystalBreak, "Mode", "The mode for crystal breaking", BreakModes.Nearest);
    public static final Setting<BreakTypes> breakType = new Setting<>(crystalBreak, "Type", "The mode for how the crystal is broken", BreakTypes.Swing);
    public static final Setting<SwingModes> swingMode = new Setting<>(crystalBreak, "Swing", "The mode for how the player swings at the crystal", SwingModes.Mainhand);

    public static final Setting<Boolean> antiWeakness = new Setting<>(crystalBreak, "Anti Weakness", "Allow switching to a sword or tool when you have weakness", true);
    public static final Setting<Boolean> antiSuicide = new Setting<>(crystalBreak, "Anti Suicide", "Stops crystals from doing too much damage to you", true);
    public static final Setting<Boolean> throughWalls = new Setting<>(crystalBreak, "Through Walls", "Allows the AutoCrystal to break through walls", true);

    public static final NumberSetting<Double> antiSuicideHealth = new NumberSetting<>(crystalBreak, "Anti Suicide HP", "Health to be at to stop you killing yourself", 1.0, 15.0, 36.0, 1);
    public static final NumberSetting<Double> breakRange = new NumberSetting<>(crystalBreak, "Range", "The range to break crystals in", 0.0, 5.5, 10.0, 1);
    public static final NumberSetting<Integer> breakDelay = new NumberSetting<>(crystalBreak, "Delay", "The delay between crystal breaks", 0, 2, 20, 0);

    public static final NumberSetting<Integer> breakAttempts = new NumberSetting<>(crystalBreak, "Break Attempts", "How many times to swing at the crystal", 1, 1, 5, 0);

    /**
     * Place Settings
     */

    public static final Setting<Boolean> crystalPlace = new Setting<>("Place", "Allows for crystals to be placed", true);
    public static final Setting<Boolean> multiPlace = new Setting<>(crystalPlace, "Multi Place", "Allows for multiple crystal placements before break", false);
    public static final Setting<Boolean> raytrace = new Setting<>(crystalPlace, "Raytrace", "Allow raytracing for placements", true);

    public static final NumberSetting<Double> placeRange = new NumberSetting<>(crystalPlace, "Range", "The range to place crystals in", 0.0, 5.5, 10.0, 1);
    public static final NumberSetting<Double> minDamage = new NumberSetting<>(crystalPlace, "Min Damage", "Minimum enemy damage for a place", 0.0, 7.0, 36.0, 1);
    public static final NumberSetting<Double> maxSelfDamage = new NumberSetting<>(crystalPlace, "Max Self Damage", "Maximum self damage for a place", 0.0, 8.0, 36.0, 1);
    public static final NumberSetting<Double> faceplaceHP = new NumberSetting<>(crystalPlace, "Faceplace HP", "Enemy health to faceplace at", 0.0, 8.0, 36.0, 1);

    public static final NumberSetting<Integer> placeDelay = new NumberSetting<>(crystalPlace, "Delay", "The delay between crystal places", 0, 2, 20, 0);

    /**
     * Calculation Settings
     */

    public static final Setting<Boolean> crystalCalculations = new Setting<>("Calculations", "Controls how AutoCrystal calculates", true);

    public static final Setting<LogicModes> logicMode = new Setting<>(crystalCalculations, "Logic", "The order to perform AutoCrystal functions", LogicModes.Breakplace);
    public static final Setting<BlockLogicModes> blockLogicMode = new Setting<>(crystalCalculations, "Block Logic", "The game version to check for blocks to place crystals on", BlockLogicModes.OneTwelve);

    public static final Setting<Boolean> rotate = new Setting<>(crystalCalculations, "Rotate", "Allow rotations to crystals and blocks", true);
    public static final Setting<Boolean> syncBreak = new Setting<>(crystalCalculations, "Sync Break", "Sets crystals to dead after breaking them", true);
    public static final Setting<Boolean> reloadCrystal = new Setting<>(crystalCalculations, "Reload Crystal", "Reloads world entities after a crystal break", true);
    public static final Setting<Boolean> antiDesync = new Setting<>(crystalCalculations, "Anti Desync", "Removes desynced crystals", true);

    public static final NumberSetting<Double> enemyRange = new NumberSetting<>("crystalCalculations, Enemy Range", "The range the target can be in", 1.0, 15.0, 50.0, 1);

    /**
     * Pause Settings
     */

    public static final Setting<Boolean> crystalPause = new Setting<>("Pause", "Controls when the AutoCrystal pauses", true);

    public static final NumberSetting<Double> pauseHealth = new NumberSetting<>(crystalPause, "Health", "Health to be at to pause the AutoCrystal", 0.0, 8.0, 36.0, 1);

    public static final Setting<Boolean> pauseWhileMining = new Setting<>(crystalPause, "While Mining", "Pauses the AutoCrystal while mining", true);
    public static final Setting<Boolean> pauseWhileEating = new Setting<>(crystalPause, "While Eating", "Pauses the AutoCrystal while eating", true);

    /**
     * Render Settings
     */

    public static final Setting<Boolean> crystalRender = new Setting<>("Render", "Allows the crystal placements to be rendered", true);

    public static final Setting<RenderModes> renderMode = new Setting<>(crystalRender, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final Setting<DamageRenderModes> damageRenderMode = new Setting<>(crystalRender, "Damage Render Mode", "How the crystal damage is rendered", DamageRenderModes.Both);

    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(crystalRender, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);

    public static final Setting<Color> renderColour = new Setting<>(crystalRender, "Render Colour", "The colour for the block placements", new Color(15, 60, 231, 201));

    public AutoCrystal() {
        this.addSettings(
                crystalBreak,
                crystalPlace,
                crystalCalculations,
                crystalPause,
                crystalRender
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

        implementLogic();
    }

    private void implementLogic() {
        switch (logicMode.getValue()) {
            case Breakplace:
                breakCrystal();
                placeCrystal();
                break;
            case Placebreak:
                placeCrystal();
                breakCrystal();
                break;
        }
    }

    private void breakCrystal() {
        if (crystalBreak.getValue()) {
            EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                    .filter(entity -> entity != null)
                    .filter(entity -> !entity.isDead)
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(entity -> mc.player.getDistance(entity) <= breakRange.getValue())
                    .min(Comparator.comparing(entity -> mc.player.getDistance(entity))) //TODO: Change for multiple types (e.g. OnlyOwn, Nearest)
                    .orElse(null);

            if (entityEnderCrystal != null && !entityEnderCrystal.isDead && breakTimer.passed(breakDelay.getValue() * 60)) {
                if (antiSuicide.getValue() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= antiSuicideHealth.getValue()) return;

                if (!mc.player.canEntityBeSeen(entityEnderCrystal) && !throughWalls.getValue()) return;

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
                    if (breakType.getValue() == BreakTypes.Swing) {
                        entityEnderCrystal.setDead();
                    }
                }

                if (reloadCrystal.getValue()) {
                    mc.world.removeAllEntities();
                    mc.world.getLoadedEntityList();
                }

                breakTimer.reset();
            }

            if (!multiPlace.getValue()) return;
        }
    }

    private void placeCrystal() {

    }

    @Listener
    public void onWorldRender(WorldRenderEvent event) {
        if (nullCheck()) return;

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (crystalRender.getValue()) {
            if (blockTarget != null) {
                switch (renderMode.getValue()) {
                    case Box:
                        RenderUtil.draw(RenderUtil.generateBB(blockTarget.getX(), blockTarget.getY(), blockTarget.getZ()), true, false, 0, 0, renderColour.getValue());
                        break;
                    case Outline:
                        RenderUtil.draw(RenderUtil.generateBB(blockTarget.getX(), blockTarget.getY(), blockTarget.getZ()), false, true, 0, 0, renderColour.getValue());
                        break;
                    case Full:
                        RenderUtil.draw(RenderUtil.generateBB(blockTarget.getX(), blockTarget.getY(), blockTarget.getZ()), true, true, 0, 0, renderColour.getValue());
                        break;
                }
            }
        }
    }

    @Listener
    public void onPacketSend(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) event.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK && cPacketUseEntity.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal && breakType.getValue() == BreakTypes.Packet) {
                cPacketUseEntity.getEntityFromWorld(mc.world).setDead();
            }
        }
    }

    public enum BreakModes {
        Nearest
        //OnlyOwn
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

    public enum LogicModes {
        Breakplace,
        Placebreak
    }

    public enum BlockLogicModes {
        OneTwelve,
        OneThirteen
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }

    public enum DamageRenderModes {
        Target,
        Self,
        Both
    }
}

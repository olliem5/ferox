package me.olliem5.ferox.impl.modules.combat;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.math.CooldownUtil;
import me.olliem5.ferox.api.util.module.CrystalPosition;
import me.olliem5.ferox.api.util.module.CrystalUtil;
import me.olliem5.ferox.api.util.packet.RotationUtil;
import me.olliem5.ferox.api.util.player.InventoryUtil;
import me.olliem5.ferox.api.util.player.TargetUtil;
import me.olliem5.ferox.api.util.render.draw.DrawUtil;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.api.util.world.BlockUtil;
import me.olliem5.ferox.impl.events.PacketEvent;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author olliem5
 *
 * - I plan to update this module frequently and make as efficient as possible
 * - Credit to Momentum for the utils and ideas (ily linus) (ily too ollie :3)
 *
 * TODO: Settings to add
 *
 * Break
 * - Sequential
 *
 * Place
 * - Walls Range
 * - Range for block to target
 *
 * Targeting
 * - Different logic for selecting a target
 *
 *
 * TODO: Better prediction, based on target as well...?
 * TODO: Fix compatibility with AutoSwitch & AntiWeakness.
 * TODO: Actual rotation system!
 * TODO: Better MultiPlace.
 * TODO: Other Place Calculations.
 * TODO: AutoSwitch override when holding gaps or pickaxe.
 * TODO: 'Both' Text render mode.
 * TODO: Better delays!
 */

@FeroxModule(name = "AutoCrystal", description = "Places and destroys end crystals to kill enemies", category = Category.COMBAT)
public final class AutoCrystal extends Module {
    /**
     * Break Settings
     *
     * Done
     */

    public static final Setting<Boolean> crystalBreak = new Setting<>("Break", "Allows for crystals to be broken", true);

    public static final Setting<BreakModes> breakType = new Setting<>(crystalBreak, "Type", "The mode for how the crystal is broken", BreakModes.Packet);
    public static final Setting<SwingModes> swingMode = new Setting<>(crystalBreak, "Swing", "The mode for how the player swings at the crystal", SwingModes.Mainhand);
    public static final Setting<SyncModes> syncMode = new Setting<>(crystalBreak, "Sync", "The way the crystal is synced", SyncModes.None);

    public static final Setting<Boolean> antiWeakness = new Setting<>(crystalBreak, "Anti Weakness", "Allow switching to a sword or tool when you have weakness", false);
    public static final Setting<Boolean> antiSuicide = new Setting<>(crystalBreak, "Anti Suicide", "Stops crystals from doing too much damage to you", true);
    public static final Setting<Boolean> throughWalls = new Setting<>(crystalBreak, "Through Walls", "Allows the AutoCrystal to break through walls", true);
    public static final Setting<Boolean> rotate = new Setting<>(crystalBreak, "Rotate", "Allow rotations to crystals and blocks", false);

    public static final NumberSetting<Double> antiSuicideHealth = new NumberSetting<>(crystalBreak, "Anti Suicide HP", "Health to be at to stop you killing yourself", 1.0, 8.0, 36.0, 1);
    public static final NumberSetting<Double> breakRange = new NumberSetting<>(crystalBreak, "Range", "The range to break crystals in", 0.0, 5.0, 7.0, 1);

    public static final NumberSetting<Integer> breakAttempts = new NumberSetting<>(crystalBreak, "Break Attempts", "How many times to swing at the crystal", 1, 1, 5, 0);
    public static final NumberSetting<Integer> breakDelay = new NumberSetting<>(crystalBreak, "Delay", "The delay between crystal breaks", 0, 2, 20, 0);

    /**
     * Place Settings
     *
     * Done
     */

    public static final Setting<Boolean> crystalPlace = new Setting<>("Place", "Allows for crystals to be placed", true);

    public static final Setting<PlaceModes> placeType = new Setting<>(crystalPlace, "Type", "The mode for how the crystal is placed", PlaceModes.Packet);

    public static final Setting<Boolean> autoSwitch = new Setting<>(crystalPlace, "Auto Switch", "Switches to crystals before placing", false);
    public static final Setting<Boolean> raytrace = new Setting<>(crystalPlace, "Raytrace", "Allow raytracing for placements", true);
    public static final Setting<Boolean> multiPlace = new Setting<>(crystalPlace, "Multi Place", "Place multiple crystals before a break", false);
    public static final Setting<Boolean> predictPlace = new Setting<>(crystalPlace, "Predict", "Takes movement into account for placements", true);
    public static final Setting<Boolean> verifyPlace = new Setting<>(crystalPlace, "Verify", "Verifies the eligibility for a place", false);

    public static final NumberSetting<Double> placeRange = new NumberSetting<>(crystalPlace, "Range", "The range to place crystals in", 0.0, 5.0, 7.0, 1);
    public static final NumberSetting<Double> minDamage = new NumberSetting<>(crystalPlace, "Min Target Damage", "Minimum target damage for a place", 0.0, 7.0, 36.0, 1);
    public static final NumberSetting<Double> maxSelfDamage = new NumberSetting<>(crystalPlace, "Max Self Damage", "Maximum self damage for a place", 0.0, 8.0, 36.0, 1);

    public static final NumberSetting<Integer> placeDelay = new NumberSetting<>(crystalPlace, "Delay", "The delay between crystal places", 0, 2, 20, 0);

    /**
     * Calculation Settings
     *
     * Done
     */

    public static final Setting<Boolean> crystalCalculations = new Setting<>("Calculations", "Controls how AutoCrystal calculates", true);

    public static final Setting<LogicModes> logicMode = new Setting<>(crystalCalculations, "Logic", "The order to perform AutoCrystal functions", LogicModes.Breakplace);
    public static final Setting<BlockLogicModes> blockLogicMode = new Setting<>(crystalCalculations, "Block Logic", "The game version to check for blocks to place crystals on", BlockLogicModes.Twelve);

    public static final NumberSetting<Double> targetRange = new NumberSetting<>(crystalCalculations, "Target Range", "The range the target can be in", 1.0, 10.0, 15.0, 1);

    /**
     * Pause Settings
     *
     * Done
     */

    public static final Setting<Boolean> crystalPause = new Setting<>("Pause", "Controls when the AutoCrystal pauses", true);

    public static final Setting<Boolean> pauseHealthAllow = new Setting<>(crystalPause, "At Specified Health", "Pauses the AutoCrystal at a specified health", true);

    public static final NumberSetting<Double> pauseHealth = new NumberSetting<>(crystalPause, "Health", "Health to be at to pause the AutoCrystal", 0.0, 8.0, 36.0, 1);

    public static final Setting<Boolean> pauseWhileMining = new Setting<>(crystalPause, "While Mining", "Pauses the AutoCrystal while mining", true);
    public static final Setting<Boolean> pauseWhileEating = new Setting<>(crystalPause, "While Eating", "Pauses the AutoCrystal while eating", true);

    /**
     * Render Settings
     */

    public static final Setting<Boolean> crystalRender = new Setting<>("Render", "Allows the crystal placements to be rendered", true);

    public static final Setting<BlockRenderModes> blockRenderMode = new Setting<>(crystalRender, "Block Mode", "The type of box to render", BlockRenderModes.Full);
    public static final Setting<TextRenderModes> textRenderMode = new Setting<>(crystalRender, "Text Mode", "How the crystal damage is rendered", TextRenderModes.Both);

    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(crystalRender, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);

    public static final Setting<Color> renderColour = new Setting<>(crystalRender, "Render Colour", "The colour for the block placements", new Color(231, 15, 101, 180));

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

    private EntityPlayer playerTarget = null;
    private CrystalPosition crystalTarget = new CrystalPosition(BlockPos.ORIGIN, 0, 0);

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        playerTarget = null;
        crystalTarget = null;

        RotationUtil.resetRotation();
    }

    public void onUpdate() {
        if (nullCheck()) return;

        playerTarget = TargetUtil.getClosestPlayer(targetRange.getValue());

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
        if (handlePause()) return;

        if (crystalBreak.getValue()) {
            EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
                    .filter(entity -> entity != null)
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .filter(entity -> !entity.isDead)
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
                    if (breakType.getValue() == BreakModes.Packet) {
                        CrystalUtil.breakCrystal(entityEnderCrystal, true);
                    } else {
                        CrystalUtil.breakCrystal(entityEnderCrystal, false);
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

                if (syncMode.getValue() == SyncModes.Attack) {
                    entityEnderCrystal.setDead();
                }

                breakTimer.reset();
            }
        }
    }

    private void placeCrystal() {
        if (handlePause()) return;

        if (crystalPlace.getValue()) {
            List<CrystalPosition> crystalPositions = new ArrayList<>();

            CrystalPosition tempPosition;

            for (BlockPos blockPos : crystalBlocks(mc.player, placeRange.getValue(), predictPlace.getValue(), !multiPlace.getValue(), getBlockLogic())) {
                if (verifyPlace.getValue() && mc.player.getDistanceSq(blockPos) > Math.pow(breakRange.getValue(), 2)) continue;

                double calculatedTargetDamage = CrystalUtil.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, playerTarget);
                double calculatedSelfDamage = mc.player.capabilities.isCreativeMode ? 0 : CrystalUtil.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, mc.player);

                if (calculatedTargetDamage < minDamage.getValue()) continue;

                if (calculatedSelfDamage > maxSelfDamage.getValue()) continue;

                crystalPositions.add(new CrystalPosition(blockPos, calculatedTargetDamage, calculatedSelfDamage));
            }

            tempPosition = crystalPositions.stream()
                    .max(Comparator.comparing(finalPlacePosition -> finalPlacePosition.getTargetDamage())) //TODO: Different calculation algorithms
                    .orElse(null);

            if (tempPosition == null) {
                crystalTarget = null;

                RotationUtil.resetRotation();

                return;
            }

            crystalTarget = tempPosition;

            if (autoSwitch.getValue()) {
                InventoryUtil.switchToSlot(ItemEndCrystal.class);
            }

            if (crystalTarget.getPosition() != BlockPos.ORIGIN && placeTimer.passed(placeDelay.getValue() * 60)) {
                if (rotate.getValue()) {
                    RotationUtil.lookAtPacket(crystalTarget.getPosition().getX(), crystalTarget.getPosition().getY(), crystalTarget.getPosition().getZ(), mc.player);
                }

                CrystalUtil.placeCrystal(crystalTarget.getPosition(), raytrace.getValue() ? CrystalUtil.getEnumFacing(raytrace.getValue(), crystalTarget.getPosition()) : EnumFacing.UP, placeType.getValue() == PlaceModes.Packet);

                placeTimer.reset();
            }
        }
    }

    private boolean handlePause() {
        if ((mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= pauseHealth.getValue() && pauseHealthAllow.getValue()) {
            return true;
        } else if (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE || mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.isHandActive() && pauseWhileEating.getValue()) {
            return true;
        } else if (mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE || mc.player.getHeldItemOffhand().getItem() == Items.DIAMOND_PICKAXE && mc.player.isHandActive() && pauseWhileMining.getValue()) {
            return true;
        }

        return false;
    }

    private int getBlockLogic() {
        if (blockLogicMode.getValue() == BlockLogicModes.Twelve) {
            return 0;
        }

        return 1;
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (crystalRender.getValue()) {
            if (crystalTarget != null) {
                switch (blockRenderMode.getValue()) {
                    case Box:
                        RenderUtil.draw(crystalTarget.getPosition(), true, false, 0, 0, renderColour.getValue());
                        break;
                    case Outline:
                        RenderUtil.draw(crystalTarget.getPosition(), false, true, 0, 0, renderColour.getValue());
                        break;
                    case Full:
                        RenderUtil.draw(crystalTarget.getPosition(), true, true, 0, 0, renderColour.getValue());
                        break;
                }

                String targetDamageRounded = String.format("%.1f", crystalTarget.getTargetDamage());
                String selfDamageRounded = String.format("%.1f", crystalTarget.getSelfDamage());

                switch (textRenderMode.getValue()) {
                    case Target:
                        DrawUtil.drawTextCenterBlockPos(crystalTarget.getPosition(), "Target: " + targetDamageRounded);
                        break;
                    case Self:
                        DrawUtil.drawTextCenterBlockPos(crystalTarget.getPosition(), "Self: " + selfDamageRounded);
                        break;
                    case Both:
                        break;
                }
            }
        }
    }

    @PaceHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();

            if (RotationUtil.isSpoofingAngles) {
                cPacketPlayer.yaw = (float) RotationUtil.yaw;
                cPacketPlayer.pitch = (float) RotationUtil.pitch;
            }
        }

        if (syncMode.getValue() == SyncModes.Packet) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) event.getPacket();

                if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK && cPacketUseEntity.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal && breakType.getValue() == BreakModes.Packet) {
                    cPacketUseEntity.getEntityFromWorld(mc.world).setDead();
                }
            }
        }
    }

    @PaceHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (nullCheck()) return;

        //TODO: Make this better
        if (syncMode.getValue() == SyncModes.Sound) {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();

                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (Entity entity : mc.world.loadedEntityList) {
                        if (entity instanceof EntityEnderCrystal) {
                            if (entity.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                                entity.setDead();
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<BlockPos> crystalBlocks(EntityPlayer entityPlayer, double placeRange, boolean prediction, boolean multiPlace, int blockLogic) {
        return BlockUtil.getNearbyBlocks(entityPlayer, placeRange, prediction).stream()
                .filter(blockPos -> CrystalUtil.canPlaceCrystal(blockPos, multiPlace, blockLogic == 1))
                .collect(Collectors.toList());
    }

    public enum BreakModes {
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

    public enum PlaceModes {
        Standard,
        Packet
    }

    public enum SyncModes {
        None,
        Attack,
        Packet,
        Sound
    }

    public enum LogicModes {
        Breakplace,
        Placebreak
    }

    public enum BlockLogicModes {
        Twelve,
        Thirteen
    }

    public enum BlockRenderModes {
        Box,
        Outline,
        Full,
        None
    }

    public enum TextRenderModes {
        Target,
        Self,
        Both,
        None
    }
}

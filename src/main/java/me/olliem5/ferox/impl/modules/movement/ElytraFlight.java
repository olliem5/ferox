package me.olliem5.ferox.impl.modules.movement;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.player.MotionUtil;
import me.olliem5.ferox.impl.modules.movement.elytraflight.ElytraMode;
import me.olliem5.ferox.impl.modules.movement.elytraflight.modes.*;
import net.minecraft.network.play.client.CPacketEntityAction;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

@FeroxModule(name = "ElytraFlight", description = "Allows you to fly faster on an elytra", category = Category.MOVEMENT)
public final class ElytraFlight extends Module {
    public static final Setting<FlyModes> mode = new Setting<>("Mode", "The mode of flight to use", FlyModes.Control);

    public static final NumberSetting<Double> horizontalSpeed = new NumberSetting<>("Glide Speed", "The speed of gliding", 0.0, 2.0, 5.0, 1);
    public static final NumberSetting<Double> verticalSpeed = new NumberSetting<>("Rise Speed", "The speed of rising", 0.0, 1.0, 5.0, 1);
    public static final NumberSetting<Double> yOffset = new NumberSetting<>("Vertical Offset", "Vertical movement for freezing", 0.0, 0.009, 0.1, 3);

    public static final Setting<Boolean> takeOffTimer = new Setting<>("Takeoff Timer", "Allows timer to be used on takeoff", false);
    public static final NumberSetting<Float> ticks = new NumberSetting<>("Ticks", "The ticks to use for timer", 0.1f, 0.5f, 0.8f, 2);

    public static final Setting<Boolean> lockRotation = new Setting<>("Lock Rotation", "Locks your rotation", false);
    public static final NumberSetting<Float> ncpRotations = new NumberSetting<>("NCP Rotation", "Rotations for NCP", 0.0f, 35.0f, 90.0f, 1);

    public static final Setting<Boolean> infiniteFly = new Setting<>("Infinite", "Allows for infinite flight", false);

    public static final Setting<Boolean> checks = new Setting<>("Checks", "Various checks for stopping flight", true);
    public static final Setting<Boolean> liquidDisable = new Setting<>(checks, "In Liquid", "Stops when you are in liquid", true);
    public static final Setting<Boolean> onCollision = new Setting<>(checks, "On Collision", "Stops when you collide with something", false);
    public static final Setting<Boolean> onUpward = new Setting<>(checks, "Upward Motion", "Stops on upwards motion", false);
    public static final Setting<Boolean> belowY = new Setting<>(checks, "Low Height", "Stops at low height", false);

    public ElytraFlight() {
        this.addSettings(
                mode,
                horizontalSpeed,
                verticalSpeed,
                yOffset,
                takeOffTimer,
                ticks,
                lockRotation,
                ncpRotations,
                infiniteFly,
                checks
        );
    }

    private ElytraMode elytraMode;

    @Override
    public void onEnable() {
        if (infiniteFly.getValue()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        switch (mode.getValue()) {
            case Control:
                elytraMode = new Control();
                break;
            case Boost:
                elytraMode = new Boost();
                break;
            case NCP:
                elytraMode = new NCP();
                break;
            case Firework:
                elytraMode = new Firework();
                break;
            case Frog:
                elytraMode = new Frog();
                break;
        }

        disableCheck();
        updateTicks();

        if (mc.player.isElytraFlying()) {
            if (!MotionUtil.isMoving()) {
                elytraMode.noMovement();
            }

            elytraMode.onVerticalMovement();
            elytraMode.onAscendingMovement();
            elytraMode.onHorizontalMovement();
            elytraMode.onRotation();
        }
    }

    @Override
    public void onDisable() {
        mc.timer.tickLength = 50f;
    }

    public void disableCheck() {
        if ((mc.player.isInWater() || mc.player.isInLava()) && liquidDisable.getValue()) return;

        if (mc.player.rotationPitch >= ncpRotations.getValue() && onUpward.getValue()) return;

        if (mc.player.posY < 10 && belowY.getValue()) return;

        if (mc.player.collidedHorizontally && onCollision.getValue()) return;
    }

    public void updateTicks() {
        if (!mc.player.isElytraFlying() && takeOffTimer.getValue()) {
            mc.timer.tickLength = 50.0f / ticks.getValue();
        }

        if (mc.player.isElytraFlying()) {
            mc.timer.tickLength = 50;
        }
    }

    public enum FlyModes {
        Control,
        Boost,
        NCP,
        Firework,
        Frog
    }
}

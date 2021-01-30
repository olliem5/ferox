package us.ferox.client.impl.modules.movement;

import net.minecraft.network.play.client.CPacketEntityAction;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.module.MotionUtil;
import us.ferox.client.impl.modules.movement.elytra.ElytraMode;
import us.ferox.client.impl.modules.movement.elytra.mode.*;

/**
 * @author linustouchtips
 * @since 12/29/2020
 */

@ModuleInfo(name = "ElytraFlight", description = "Allows you to fly faster on an elytra", category = Category.MOVEMENT)
public class ElytraFlight extends Module {
    public static Setting<FlyMode> mode = new Setting<>("Mode", FlyMode.Control);

    public static NumberSetting<Double> horizontalSpeed = new NumberSetting<>("Glide Speed", 0.0, 2.0, 5.0, 1);
    public static NumberSetting<Double> verticalSpeed = new NumberSetting<>("Rise Speed", 0.0, 1.0, 5.0, 1);
    public static NumberSetting<Double> yOffset = new NumberSetting<>("Vertical Offset", 0.0, 0.009, 0.1, 3);

    public static Setting<Boolean> takeOffTimer = new Setting<>("Takeoff Timer", false);
    public static NumberSetting<Float> ticks = new NumberSetting<>("Ticks", 0.1f, 0.5f, 0.8f, 2);

    public static Setting<Boolean> lockRotation = new Setting<>("Lock Rotation", false);
    public static NumberSetting<Float> ncpRotations = new NumberSetting<>("NCP Rotation", 0.0f, 35.0f, 90.0f, 1);

    public static Setting<Boolean> infiniteFly = new Setting<>("Infinite", false);

    public static Setting<Boolean> checks = new Setting<>("Checks", true);
    public static Setting<Boolean> liquidDisable = new Setting<>(checks, "In Liquid", true);
    public static Setting<Boolean> onCollision = new Setting<>(checks, "On Collision", false);
    public static Setting<Boolean> onUpward = new Setting<>(checks, "Upward Motion", false);
    public static Setting<Boolean> belowY = new Setting<>(checks, "Low Height", false);

    public ElytraFlight() {
        this.addSetting(mode);
        this.addSetting(horizontalSpeed);
        this.addSetting(verticalSpeed);
        this.addSetting(yOffset);
        this.addSetting(takeOffTimer);
        this.addSetting(ticks);
        this.addSetting(lockRotation);
        this.addSetting(ncpRotations);
        this.addSetting(infiniteFly);
        this.addSetting(checks);
        this.addSetting(liquidDisable);
        this.addSetting(onCollision);
        this.addSetting(onUpward);
        this.addSetting(belowY);
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

    public void updateTicks() {
        if (!mc.player.isElytraFlying() && takeOffTimer.getValue()) {
            mc.timer.tickLength = 50.0f / ticks.getValue();
        }

        if (mc.player.isElytraFlying()) {
            mc.timer.tickLength = 50;
        }
    }

    public void disableCheck() {
        if ((mc.player.isInWater() || mc.player.isInLava()) && liquidDisable.getValue()) return;

        if (mc.player.rotationPitch >= ncpRotations.getValue() && onUpward.getValue()) return;

        if (mc.player.posY < 10 && belowY.getValue()) return;

        if (mc.player.collidedHorizontally && onCollision.getValue()) return;
    }

    public enum FlyMode {
        Control,
        Boost,
        NCP,
        Firework,
        Frog,
        Packet
    }
}

package me.olliem5.ferox.impl.modules.movement;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.PlayerMoveEvent;
import me.olliem5.ferox.impl.modules.movement.speed.SpeedMode;
import me.olliem5.ferox.impl.modules.movement.speed.modes.OnGround;
import me.olliem5.ferox.impl.modules.movement.speed.modes.Strafe;
import me.olliem5.ferox.impl.modules.movement.speed.modes.YPort;
import me.olliem5.pace.annotation.PaceHandler;

/**
 * @author olliem5
 */

@FeroxModule(name = "Speed", description = "Allows you to move faster", category = Category.MOVEMENT)
public final class Speed extends Module {
    public static final Setting<SpeedModes> selectedSpeedMode = new Setting<>("Mode", "The way of acquiring speed", SpeedModes.Strafe);

    public static final Setting<Boolean> strafeSettings = new Setting<>("Strafe", "Settings for Strafe speed", true);
    public static final Setting<StrafeModes> strafeMode = new Setting<>(strafeSettings, "Mode", "Mode of strafe to use", StrafeModes.OnGround);
    public static final Setting<Boolean> strafeSprint = new Setting<>(strafeSettings, "Sprint", "Automatically sprints for you", true);
    public static final Setting<Boolean> strafeSmoothJump = new Setting<>(strafeSettings, "Smooth Jump", "Smooth strafe jump", false);
    public static final Setting<Boolean> strafeJumpPotionEffect = new Setting<>(strafeSettings, "Jump Potion Effect", "Gives you the jump boost potion effect", true);
    public static final Setting<Boolean> strafeSpeedPotionEffect = new Setting<>(strafeSettings, "Speed Potion Effect", "Gives you the speed potion effect", true);
    public static final NumberSetting<Double> strafeSpeed = new NumberSetting<>(strafeSettings, "Speed", "Speed of the strafe", 0.0, 0.0, 100.0, 1);

    public static final Setting<Boolean> yPortSettings = new Setting<>("YPort", "Settings for YPort speed", true);
    public static final NumberSetting<Double> yPortSpeed = new NumberSetting<>(yPortSettings, "Speed", "YPort speed", 0.06, 0.01, 0.15, 2);

    public static final Setting<Boolean> onGroundSettings = new Setting<>("On Ground", "Settings for OnGround speed", true);
    public static final NumberSetting<Double> onGroundSpeedOne = new NumberSetting<>(onGroundSettings, "Speed One", "The first speed value for OnGround speed", 0.10, 0.15, 0.20, 2);
    public static final NumberSetting<Double> onGroundSpeedTwo = new NumberSetting<>(onGroundSettings, "Speed Two", "The second speed value for OnGround speed", 0.060, 0.065, 0.070, 3);

    public Speed() {
        this.addSettings(
                selectedSpeedMode,
                strafeSettings,
                yPortSettings,
                onGroundSettings
        );
    }

    private SpeedMode speedMode;

    public void onUpdate() {
        if (nullCheck()) return;

        switch (selectedSpeedMode.getValue()) {
            case Strafe:
                speedMode = new Strafe();
                break;
            case YPort:
                speedMode = new YPort();
                break;
            case OnGround:
                speedMode = new OnGround();
                break;
        }

        speedMode.onUpdate();
    }

    @PaceHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (nullCheck()) return;

        speedMode.onPlayerMove(event);
    }

    public enum SpeedModes {
        Strafe,
        YPort,
        OnGround
    }

    public enum StrafeModes {
        OnGround,
        AutoJump
    }
}

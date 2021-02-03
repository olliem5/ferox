package me.olliem5.ferox.impl.modules.movement;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;

@ModuleInfo(name = "Sprint", description = "Automatically makes you sprint", category = Category.MOVEMENT)
public class Sprint extends Module {
    public static Setting<SprintModes> sprintMode = new Setting<>("Mode", "The type of sprint to perform", SprintModes.Rage);

    public Sprint() {
        this.addSettings(
                sprintMode
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        try {
            if (keyCheck() && logicCheck()) {
                mc.player.setSprinting(true);
            }
        } catch (Exception e) {}
    }

    private boolean keyCheck() {
        switch (sprintMode.getValue()) {
            case Legit:
                if (mc.gameSettings.keyBindForward.isKeyDown()) {
                    return true;
                }

                break;

            case Rage:
                if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                    return true;
                }

                break;
        }

        return false;
    }

    private boolean logicCheck() {
        switch (sprintMode.getValue()) {
            case Legit:
                if (!mc.player.collidedHorizontally && !mc.player.isSneaking() && !mc.player.isHandActive() && mc.player.getFoodStats().getFoodLevel() > 6f) {
                    return true;
                }

                break;

            case Rage:
                if (!mc.player.collidedHorizontally && !mc.player.isSneaking() && mc.player.getFoodStats().getFoodLevel() > 6f) {
                    return true;
                }

                break;
        }

        return false;
    }

    public enum SprintModes {
        Legit,
        Rage
    }
}

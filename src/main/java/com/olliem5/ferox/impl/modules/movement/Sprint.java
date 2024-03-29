package com.olliem5.ferox.impl.modules.movement;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Sprint", description = "Automatically makes you sprint", category = Category.Movement)
public final class Sprint extends Module {
    public static final Setting<SprintModes> sprintMode = new Setting<>("Mode", "The type of sprint to perform", SprintModes.Rage);

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
        } catch (Exception ignored) {}
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

    @Override
    public String getArraylistInfo() {
        return sprintMode.getValue().toString();
    }

    public enum SprintModes {
        Legit,
        Rage
    }
}

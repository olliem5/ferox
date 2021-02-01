package us.ferox.client.impl.modules.movement;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;

@ModuleInfo(name = "Sprint", description = "Automatically makes you sprint", category = Category.MOVEMENT)
public class Sprint extends Module {
    public static Setting<SprintModes> sprintMode = new Setting<>("Mode", SprintModes.Rage);

    public Sprint() {
        this.addSetting(sprintMode);
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

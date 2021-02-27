package com.olliem5.ferox.impl.modules.movement;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "WebTeleport", description = "Allows you to fall through webs faster", category = Category.Movement)
public final class WebTeleport extends Module {
    public static final Setting<CancelModes> cancelMode = new Setting<>("Mode", "The method to use to let you fall through webs faster", CancelModes.Collision);

    public WebTeleport() {
        this.addSettings(
                cancelMode
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.isInWeb) {
            switch (cancelMode.getValue()) {
                case MotionY:
                    mc.player.motionY--;
                    break;
                case Vanilla:
                    mc.player.isInWeb = false;
                    break;
            }
        }
    }

    @Override
    public String getArraylistInfo() {
        return cancelMode.getValue().toString();
    }

    public enum CancelModes {
        Collision,
        MotionY,
        Vanilla
    }
}

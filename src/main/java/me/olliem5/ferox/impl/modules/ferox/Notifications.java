package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Notifications", description = "Handles notifications client-wide", category = Category.FEROX)
public final class Notifications extends Module {
    public static final Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", "Notifies you when a module is toggled", true);

    public Notifications() {
        this.addSetting(moduleToggle);
    }
}

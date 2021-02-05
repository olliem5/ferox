package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;

@FeroxModule(name = "Notifier", description = "Notifies you on certain events", category = Category.FEROX)
public class Notifier extends Module {
    public static final Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", "Notifies you when a module is toggled", true);

    public Notifier() {
        this.addSetting(moduleToggle);
    }
}

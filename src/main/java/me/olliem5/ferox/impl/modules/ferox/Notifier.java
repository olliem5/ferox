package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;

@ModuleInfo(name = "Notifier", description = "Notifies you on certain events", category = Category.FEROX)
public class Notifier extends Module {
    public static Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", true);

    public Notifier() {
        this.addSetting(moduleToggle);
    }
}

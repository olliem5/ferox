package us.ferox.client.impl.modules.ferox;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;

@ModuleInfo(name = "Notifier", description = "Notifies you on certain events", category = Category.FEROX)
public class Notifier extends Module {
    public static Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", true);

    public Notifier() {
        this.addSetting(moduleToggle);
    }
}

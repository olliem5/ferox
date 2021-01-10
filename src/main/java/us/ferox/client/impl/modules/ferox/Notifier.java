package us.ferox.client.impl.modules.ferox;

import git.littledraily.eventsystem.Listener;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.impl.events.TotemPopEvent;

import java.util.HashMap;

@ModuleInfo(name = "Notifier", description = "Notifies you on certain events", category = Category.FEROX)
public class Notifier extends Module {
    public static Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", true);
    public static Setting<Boolean> totemPop = new Setting<>("Totem Pop", true);

    public Notifier() {
        this.addSetting(moduleToggle);
    }

    private HashMap<String, Integer> totemPops = new HashMap<>();

    @Listener
    public void onTotemPop(TotemPopEvent event) {

    }
}

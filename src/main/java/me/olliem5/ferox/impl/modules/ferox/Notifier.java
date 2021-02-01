package me.olliem5.ferox.impl.modules.ferox;

import git.littledraily.eventsystem.Listener;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.TotemPopEvent;

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

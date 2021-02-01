package me.olliem5.ferox.api.module;

import me.olliem5.ferox.impl.modules.combat.*;
import me.olliem5.ferox.impl.modules.ferox.DiscordRPC;
import me.olliem5.ferox.impl.modules.ferox.Font;
import me.olliem5.ferox.impl.modules.ferox.Friends;
import me.olliem5.ferox.impl.modules.ferox.Notifier;
import me.olliem5.ferox.impl.modules.misc.ChatSuffix;
import me.olliem5.ferox.impl.modules.misc.FakePlayer;
import me.olliem5.ferox.impl.modules.misc.FastUse;
import me.olliem5.ferox.impl.modules.movement.ElytraFlight;
import me.olliem5.ferox.impl.modules.movement.Sprint;
import me.olliem5.ferox.impl.modules.movement.Velocity;
import me.olliem5.ferox.impl.modules.render.Brightness;
import me.olliem5.ferox.impl.modules.render.HoleESP;
import me.olliem5.ferox.impl.modules.ui.ClickGUIModule;
import me.olliem5.ferox.impl.modules.ui.ConsoleModule;
import me.olliem5.ferox.impl.modules.ui.HudEditorModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static ArrayList<Module> modules = new ArrayList<>();

    public static void init() {
        modules.add(new ClickGUIModule());
        modules.add(new DiscordRPC());
        modules.add(new Font());
        modules.add(new AutoCrystal());
        modules.add(new ElytraFlight());
        modules.add(new Surround());
        modules.add(new AutoTrap());
        modules.add(new FastUse());
        modules.add(new ChatSuffix());
        modules.add(new HoleESP());
        modules.add(new Offhand());
        modules.add(new Friends());
        modules.add(new Velocity());
        modules.add(new Brightness());
        modules.add(new HudEditorModule());
        modules.add(new AntiCrystal());
        modules.add(new Notifier());
        modules.add(new ConsoleModule());
        modules.add(new Sprint());
        modules.add(new FakePlayer());
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static List<Module> getModulesInCategory(Category category) {
        List<Module> modulesInCategory = new ArrayList<>();

        for (Module module : modules) {
            if (module.getCategory().equals(category)) {
                modulesInCategory.add(module);
            }
        }

        return modulesInCategory;
    }

    public static Module getModuleByName(String name) {
        return modules.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

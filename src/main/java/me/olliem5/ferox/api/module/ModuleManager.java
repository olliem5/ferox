package me.olliem5.ferox.api.module;

import me.olliem5.ferox.impl.modules.combat.*;
import me.olliem5.ferox.impl.modules.exploit.Blink;
import me.olliem5.ferox.impl.modules.ferox.DiscordRPC;
import me.olliem5.ferox.impl.modules.ferox.Font;
import me.olliem5.ferox.impl.modules.ferox.Friends;
import me.olliem5.ferox.impl.modules.ferox.Notifications;
import me.olliem5.ferox.impl.modules.misc.ChatSuffix;
import me.olliem5.ferox.impl.modules.misc.FakePlayer;
import me.olliem5.ferox.impl.modules.misc.FastUse;
import me.olliem5.ferox.impl.modules.movement.ElytraFlight;
import me.olliem5.ferox.impl.modules.movement.Speed;
import me.olliem5.ferox.impl.modules.movement.Sprint;
import me.olliem5.ferox.impl.modules.movement.Velocity;
import me.olliem5.ferox.impl.modules.render.Brightness;
import me.olliem5.ferox.impl.modules.render.HoleESP;
import me.olliem5.ferox.impl.modules.ui.ClickGUI;
import me.olliem5.ferox.impl.modules.ui.Console;
import me.olliem5.ferox.impl.modules.ui.HUDEditor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author olliem5
 */

public final class ModuleManager {
    private static final ArrayList<Module> modules = new ArrayList<>();

    public static void init() {
        modules.addAll(Arrays.asList(
                //Combat
                new AntiCrystal(),
                new AutoCrystal(),
                new AutoTrap(),
                new Offhand(),
                new Surround(),
                new SelfTrap(),

                //Movement
                new ElytraFlight(),
                new Sprint(),
                new Velocity(),
                new Speed(),

                //Miscellaneous
                new ChatSuffix(),
                new FakePlayer(),
                new FastUse(),

                //Exploit
                new Blink(),

                //Render
                new Brightness(),
                new HoleESP(),

                //Ferox
                new DiscordRPC(),
                new Font(),
                new Friends(),
                new Notifications(),

                //Interface
                new ClickGUI(),
                new Console(),
                new HUDEditor()
        ));
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesInCategory(Category category) {
        ArrayList<Module> modulesInCategory = new ArrayList<>();

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

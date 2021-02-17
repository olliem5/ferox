package com.olliem5.ferox.api.module;

import com.olliem5.ferox.impl.modules.combat.*;
import com.olliem5.ferox.impl.modules.exploit.*;
import com.olliem5.ferox.impl.modules.ferox.*;
import com.olliem5.ferox.impl.modules.miscellaneous.AutoLog;
import com.olliem5.ferox.impl.modules.miscellaneous.ChatSuffix;
import com.olliem5.ferox.impl.modules.miscellaneous.FakePlayer;
import com.olliem5.ferox.impl.modules.miscellaneous.FastUse;
import com.olliem5.ferox.impl.modules.movement.ElytraFlight;
import com.olliem5.ferox.impl.modules.movement.NoSlow;
import com.olliem5.ferox.impl.modules.movement.Sprint;
import com.olliem5.ferox.impl.modules.movement.Velocity;
import com.olliem5.ferox.impl.modules.render.*;
import com.olliem5.ferox.impl.modules.ui.ClickGUI;
import com.olliem5.ferox.impl.modules.ui.Console;
import com.olliem5.ferox.impl.modules.ui.HUDEditor;

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
                new Aura(),
                new AutoCreeper(),
                new AutoCrystal(),
                new AutoTrap(),
                new AutoWeb(),
                new HoleFill(),
                new Offhand(),
                new SelfTrap(),
                new Surround(),

                //Movement
                new ElytraFlight(),
                new NoSlow(),
                new Sprint(),
                new Velocity(),

                //Miscellaneous
                new AutoLog(),
                new ChatSuffix(),
                new FakePlayer(),
                new FastUse(),

                //Exploit
                new Blink(),
                new BuildHeight(),
                new Burrow(),
                new Criticals(),
                new LiquidInteract(),
                new NoEntityTrace(),
                new Portals(),
                new Reach(),
                new Timer(),
                new XCarry(),

                //Render
                new BlockHighlight(),
                new Brightness(),
                new BurrowESP(),
                new Conditions(),
                new CustomFOV(),
                new HoleESP(),
                new ItemTooltips(),
                new ViewModel(),

                //Ferox
                new Colours(),
                new DiscordRPC(),
                new ClientFont(),
                new HUD(),
                new Notifications(),
                new Social(),
                new Target(),

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

package com.olliem5.ferox.api.module;

import com.olliem5.ferox.impl.modules.combat.*;
import com.olliem5.ferox.impl.modules.exploit.*;
import com.olliem5.ferox.impl.modules.ferox.*;
import com.olliem5.ferox.impl.modules.miscellaneous.*;
import com.olliem5.ferox.impl.modules.movement.*;
import com.olliem5.ferox.impl.modules.render.*;
import com.olliem5.ferox.impl.modules.ui.ClickGUI;
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
                new NoRotate(),
                new NoSlow(),
                new Sprint(),
                new Step(),
                new Velocity(),

                //Miscellaneous
                new AntiPacketKick(),
                new AutoLog(),
                new AutoRespawn(),
                new ChatSuffix(),
                new FakePlayer(),
                new FastUse(),
                new Freecam(),
                new WebTeleport(),

                //Exploit
                new Blink(),
                new BuildHeight(),
                new Burrow(),
                new Criticals(),
                new LiquidInteract(),
                new NoEntityTrace(),
                new Portals(),
                new Reach(),
                new ThunderHack(),
                new Timer(),
                new XCarry(),

                //Render
                new Australia(),
                new BlockHighlight(),
                new Brightness(),
                new BurrowESP(),
                new CameraClip(),
                new Chams(),
                new Conditions(),
                new CustomFOV(),
                new ESP(),
                new HoleESP(),
                new ItemTooltips(),
                new SkyColour(),
                new ThrowTrails(),
                new ViewModel(),
                new VoidESP(),

                //Ferox
                new Baritone(),
                new Colours(),
                new DiscordRPC(),
                new ClientFont(),
                new HUD(),
                new Notifications(),
                new Social(),
                new Waypoints(),

                //Interface
                new ClickGUI(),
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

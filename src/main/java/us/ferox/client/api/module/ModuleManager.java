package us.ferox.client.api.module;

import us.ferox.client.impl.modules.combat.AutoCrystal;
import us.ferox.client.impl.modules.combat.AutoTrap;
import us.ferox.client.impl.modules.combat.Offhand;
import us.ferox.client.impl.modules.combat.Surround;
import us.ferox.client.impl.modules.ferox.ClickGUIModule;
import us.ferox.client.impl.modules.ferox.DiscordRPC;
import us.ferox.client.impl.modules.ferox.Font;
import us.ferox.client.impl.modules.ferox.Friends;
import us.ferox.client.impl.modules.misc.ChatSuffix;
import us.ferox.client.impl.modules.misc.FastUse;
import us.ferox.client.impl.modules.movement.ElytraFlight;
import us.ferox.client.impl.modules.render.HoleESP;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
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
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

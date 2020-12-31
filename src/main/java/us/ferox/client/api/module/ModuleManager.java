package us.ferox.client.api.module;

import us.ferox.client.impl.modules.combat.AutoCrystal;
import us.ferox.client.impl.modules.ferox.ClickGUIModule;
import us.ferox.client.impl.modules.ferox.DiscordRPC;
import us.ferox.client.impl.modules.ferox.Font;
import us.ferox.client.impl.modules.movement.ElytraFlight;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new ClickGUIModule());
        modules.add(new DiscordRPC());
        modules.add(new Font());
        modules.add(new AutoCrystal());
        modules.add(new ElytraFlight());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

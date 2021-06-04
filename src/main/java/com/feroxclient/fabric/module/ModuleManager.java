package com.feroxclient.fabric.module;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.event.events.KeyPressEvent;
import com.feroxclient.fabric.module.modules.ClickGUIModule;
import meteordevelopment.orbit.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    List<Module> modules = new ArrayList<>();

    public ModuleManager(){
        modules.add(new ClickGUIModule());
        FeroxMod.EVENT_BUS.subscribe(this);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @EventHandler
    public void onKey(KeyPressEvent event){
        modules.stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> {
            module.toggle();
        });
    }
}

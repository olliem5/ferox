package com.feroxclient.fabric;

import com.feroxclient.fabric.module.ModuleManager;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class FeroxMod implements ModInitializer {

    public static final Logger logger = Logger.getLogger("ferox");
    public static IEventBus EVENT_BUS = new EventBus();

    //objects
    public static ModuleManager moduleManager;

    @Override
    public void onInitialize() {
        logger.info("Starting ferox");
        moduleManager = new ModuleManager();
    }
}

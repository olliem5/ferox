package com.feroxclient.fabric;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class FeroxMod implements ModInitializer {

    public static final Logger logger = Logger.getLogger("ferox");

    @Override
    public void onInitialize() {
        logger.info("Starting ferox");
    }
}

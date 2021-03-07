package com.olliem5.ferox;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.pace.handler.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

/**
 * @author olliem5
 */

@Mod(modid = Ferox.MOD_ID, name = Ferox.MOD_NAME, version = Ferox.MOD_VERSION)
public final class Ferox implements Minecraft {
    public static final String MOD_ID = "ferox";
    public static final String MOD_NAME = "Ferox";
    public static final String MOD_VERSION = "1.1";
    public static final String APP_ID = "792247134677762088";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;
    public static String CHAT_PREFIX = "%";

    public static final Logger LOGGER = LogManager.getLogger(NAME_VERSION);
    public static final EventHandler EVENT_BUS = new EventHandler();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StartupHelper.startupFerox();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConfigUtil.saveConfig();
            log("Config Saved!");
        }));

        ConfigUtil.loadConfig();
        log("Config Loaded!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(NAME_VERSION);
        log("Ferox Initialized!");
    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}
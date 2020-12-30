package us.ferox.client;

import git.littledraily.eventsystem.bus.EventBus;
import git.littledraily.eventsystem.bus.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import us.ferox.client.api.event.EventProcessor;
import us.ferox.client.api.module.ModuleManager;
import us.ferox.client.api.util.font.FeroxFontRenderer;

@Mod(modid = Ferox.MOD_ID, name = Ferox.MOD_NAME, version = Ferox.MOD_VERSION)
public class Ferox {
    public static final String MOD_ID = "ferox";
    public static final String MOD_NAME = "Ferox";
    public static final String MOD_VERSION = "1.0";
    public static final String APP_ID = "792247134677762088";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;

    public static final Logger LOGGER = LogManager.getLogger(NAME_VERSION);
    public static final EventBus EVENT_BUS = new EventManager();

    public static FeroxFontRenderer latoFont = new FeroxFontRenderer("Lato", 18.0f);
    public static FeroxFontRenderer ubuntuFont = new FeroxFontRenderer("Ubuntu", 18.0f);
    public static FeroxFontRenderer verdanaFont = new FeroxFontRenderer("Verdana", 18.0f);
    public static FeroxFontRenderer comfortaaFont = new FeroxFontRenderer("Comfortaa", 18.0f);
    public static FeroxFontRenderer subtitleFont = new FeroxFontRenderer("Subtitle", 18.0f);

    public EventProcessor eventProcessor;

    public static ModuleManager moduleManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Pre Initialize!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        log("Events Initialized");

        moduleManager = new ModuleManager();
        log("Modules Initialized!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(NAME_VERSION);
    }

    public static void log(String message) {
        LOGGER.info("[" + NAME_VERSION + "] " + message);
    }
}

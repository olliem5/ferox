package me.olliem5.ferox;

import git.littledraily.eventsystem.bus.EventBus;
import git.littledraily.eventsystem.bus.EventManager;
import me.olliem5.ferox.api.mixin.MixinLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = Ferox.MOD_ID, name = Ferox.MOD_NAME, version = Ferox.MOD_VERSION)
public final class Ferox {
    public static final String MOD_ID = "ferox";
    public static final String MOD_NAME = "Ferox";
    public static final String MOD_VERSION = "1.0";
    public static final String CHAT_PREFIX = "%";
    public static final String APP_ID = "792247134677762088";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;

    public static final Logger LOGGER = LogManager.getLogger(NAME_VERSION);
    public static final EventBus EVENT_BUS = new EventManager();

    public static MixinLoader mixinLoader;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mixinLoader = new MixinLoader();
        log("Mixins Initialized!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StartupHelper.startupFerox();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(NAME_VERSION);
        log("Ferox Initialized!");
    }

    public static void log(String message) {
        LOGGER.info("[" + NAME_VERSION + "] " + message);
    }
}

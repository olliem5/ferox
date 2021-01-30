package us.ferox.client;

import git.littledraily.eventsystem.bus.EventBus;
import git.littledraily.eventsystem.bus.EventManager;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import us.ferox.client.api.event.EventProcessor;
import us.ferox.client.api.friend.FriendManager;
import us.ferox.client.api.hud.HudManager;
import us.ferox.client.api.mixin.MixinLoader;
import us.ferox.client.api.module.ModuleManager;
import us.ferox.client.api.util.font.FeroxFontRenderer;
import us.ferox.client.impl.command.BindCommand;
import us.ferox.client.impl.command.EchoCommand;
import us.ferox.client.impl.command.FriendCommand;
import us.ferox.client.impl.command.ToggleCommand;
import us.ferox.client.impl.gui.click.main.Window;
import us.ferox.client.impl.gui.click.theme.Theme;

@Mod(modid = Ferox.MOD_ID, name = Ferox.MOD_NAME, version = Ferox.MOD_VERSION)
public class Ferox {
    public static final String MOD_ID = "ferox";
    public static final String MOD_NAME = "Ferox";
    public static final String MOD_VERSION = "1.0";
    public static final String CHAT_PREFIX = "%";
    public static final String APP_ID = "792247134677762088";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;

    public static final Logger LOGGER = LogManager.getLogger(NAME_VERSION);
    public static final EventBus EVENT_BUS = new EventManager();

    public static FeroxFontRenderer latoFont = new FeroxFontRenderer("Lato", 18.0f);
    public static FeroxFontRenderer ubuntuFont = new FeroxFontRenderer("Ubuntu", 18.0f);
    public static FeroxFontRenderer verdanaFont = new FeroxFontRenderer("Verdana", 18.0f);
    public static FeroxFontRenderer comfortaaFont = new FeroxFontRenderer("Comfortaa", 18.0f);
    public static FeroxFontRenderer subtitleFont = new FeroxFontRenderer("Subtitle", 18.0f);

    public static EventProcessor eventProcessor;
    public static ModuleManager moduleManager;
    public static MixinLoader mixinLoader;
    public static FriendManager friendManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mixinLoader = new MixinLoader();
        LOGGER.info("Mixins Initialized!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        log("Events Initialized!");

        moduleManager = new ModuleManager();
        log("Modules Initialized!");

        Window.initGui();
        Theme.initThemes();
        log("GUI Initialized!");

        HudManager.init();
        log("Hud initialized!");

        initCommandManager();
        log("Commands initialized!");

        friendManager = new FriendManager();
        log("Friends Initialized!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(NAME_VERSION);
        log("Ferox Initialized!");
    }

    public static void log(String message) {
        LOGGER.info("[" + NAME_VERSION + "] " + message);
    }

    private void initCommandManager() {
        CommandManager.addCommands(
                new EchoCommand(),
                new FriendCommand(),
                new ToggleCommand(),
                new BindCommand()
        );
    }
}

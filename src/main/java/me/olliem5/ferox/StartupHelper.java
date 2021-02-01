package me.olliem5.ferox;

import me.olliem5.ferox.api.event.EventProcessor;
import me.olliem5.ferox.api.hud.HudManager;
import me.olliem5.ferox.api.mixin.MixinLoader;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.impl.command.EchoCommand;
import me.olliem5.ferox.impl.gui.click.main.Window;
import me.olliem5.ferox.impl.gui.click.theme.Theme;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.common.MinecraftForge;

public class StartupHelper {
    public static void startupFerox() {
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        Ferox.EVENT_BUS.subscribe(new EventProcessor());
        Ferox.log("Event Processor Initialized!");

        ModuleManager.init();
        Ferox.log("Modules Initialized!");

        Window.initGui();
        Theme.initThemes();
        Ferox.log("GUI Initialized!");

        HudManager.init();
        Ferox.log("Hud initialized!");

        initCommandManager();
        Ferox.log("Commands initialized!");
    }

    private static void initCommandManager() {
        CommandManager.addCommands(
                new EchoCommand()
        );
    }
}

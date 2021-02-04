package me.olliem5.ferox;

import me.olliem5.ferox.api.event.EventProcessor;
import me.olliem5.ferox.api.hud.HudManager;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.theme.ThemeManager;
import me.olliem5.ferox.impl.gui.screens.click.Window;
import me.yagel15637.venture.command.ICommand;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.common.MinecraftForge;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class StartupHelper {
    public static void startupFerox() {
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        Ferox.EVENT_BUS.subscribe(new EventProcessor());
        Ferox.log("Event Processor Initialized!");

        ModuleManager.init();
        Ferox.log("Modules Initialized!");

        Window.initGui();
        ThemeManager.init();
        Ferox.log("GUI Initialized!");

        HudManager.init();
        Ferox.EVENT_BUS.subscribe(new HudManager());
        Ferox.log("Hud initialized!");

        initCommandManager();
        Ferox.log("Commands initialized!");
    }

    private static void initCommandManager() {
        Reflections reflections = new Reflections("me.olliem5.ferox.impl.command");

        reflections.getSubTypesOf(ICommand.class).forEach(clazz -> {

            try {
                ICommand iCommand = clazz.getConstructor().newInstance();
                CommandManager.addCommands(iCommand);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }
}

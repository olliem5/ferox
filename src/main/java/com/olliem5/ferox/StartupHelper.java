package com.olliem5.ferox;

import com.olliem5.ferox.api.component.ComponentManager;
import com.olliem5.ferox.api.event.EventProcessor;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.theme.ThemeManager;
import com.olliem5.ferox.api.waypoint.WaypointManager;
import com.olliem5.ferox.impl.commands.*;
import com.olliem5.ferox.impl.gui.screens.click.ClickGUIWindow;
import com.olliem5.ferox.impl.gui.screens.editor.HUDEditorWindow;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author olliem5
 */

public final class StartupHelper {
    public static void startupFerox() {
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        Ferox.EVENT_BUS.register(new EventProcessor());
        Ferox.log("Event Processor Initialized!");

        ModuleManager.init();
        Ferox.log("Modules Initialized!");

        ComponentManager.init();
        Ferox.EVENT_BUS.register(new ComponentManager());
        Ferox.log("HUD Initialized!");

        ClickGUIWindow.initGui();
        Ferox.log("ClickGUI Windows Initialized!");

        HUDEditorWindow.initGui();
        Ferox.log("HUDEditor Windows Initialized!");

        ThemeManager.init();
        Ferox.log("Global GUI Themes Initialized!");

        WaypointManager.init();
        Ferox.log("Waypoints Initialized!");

        initCommandManager();
        Ferox.log("Commands Initialized!");
    }

    private static void initCommandManager() {
        CommandManager.addCommands(
                new BindCommand(),
                new ConfigCommand(),
                new DrawnCommand(),
                new EchoCommand(),
                new FolderCommand(),
                new ModulesCommand(),
                new PrefixCommand(),
                new ToggleCommand()
        );
    }
}

package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.impl.gui.screens.console.Console;

@ModuleInfo(name = "Console", description = "Opens Ferox's Console", category = Category.UI)
public class ConsoleModule extends Module {
    public static Console console;

    @Override
    public void onEnable() {
        if (console == null) {
            console = new Console();
        }

        mc.displayGuiScreen(console);

        toggle();
    }
}

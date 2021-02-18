package com.olliem5.ferox.impl.modules.ui;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.impl.gui.screens.console.ConsoleScreen;

/**
 * @author olliem5
 */

@FeroxModule(name = "Console", description = "Opens Ferox's Console", category = Category.Interface)
public final class Console extends Module {
    public static ConsoleScreen console;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (console == null) {
            console = new ConsoleScreen();
        }

        mc.displayGuiScreen(console);

        this.toggle();
    }
}
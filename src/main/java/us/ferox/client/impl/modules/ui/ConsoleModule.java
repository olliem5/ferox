package us.ferox.client.impl.modules.ui;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.impl.gui.console.Console;

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

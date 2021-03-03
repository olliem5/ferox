package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "MainMenu", description = "Allows Ferox's custom main menu GUI to function", category = Category.Ferox)
public final class MainMenu extends Module {
    public static final Setting<Boolean> logo = new Setting<>("Logo", "Renders the logo on the main menu", true);
    public static final Setting<Boolean> changelog = new Setting<>("Changelog", "Renders the changelog on the main menu", true);
    public static final Setting<Boolean> mouseBackground = new Setting<>("Mouse Background", "Makes the background render based on your mouse position", true);

    public MainMenu() {
        this.addSettings(
                logo,
                changelog,
                mouseBackground
        );

        this.setEnabled(true);
        this.setDrawn(false);
    }
}

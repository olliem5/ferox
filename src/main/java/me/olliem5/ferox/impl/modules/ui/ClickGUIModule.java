package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.screens.click.BaseGui;
import org.lwjgl.input.Keyboard;

@FeroxModule(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.UI, key = Keyboard.KEY_P)
public final class ClickGUIModule extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", "The theme to use for the ClickGUI", ThemeModes.Default);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", "Allows GUI windows to go over the screen", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", "Speed to scroll the gui at", 0, 10, 20, 0);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", "Controls how the game is paused when the GUI is open", PauseModes.Continue);

    public ClickGUIModule() {
        this.addSettings(
                theme,
                windowOverflow,
                scrollSpeed,
                pauseGame
        );
    }

    private BaseGui clickGUI;

    @Override
    public void onEnable() {
        if (clickGUI == null) {
            clickGUI = new BaseGui();
        }

        mc.displayGuiScreen(clickGUI);

        toggle();
    }

    public enum ThemeModes {
        Default
    }

    public enum PauseModes {
        Pause,
        Continue
    }
}

package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.screens.click.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

/**
 * @author olliem5
 */

@FeroxModule(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.Interface, key = Keyboard.KEY_P)
public final class ClickGUI extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", "The theme to use for the ClickGUI", ThemeModes.Default);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", "Allows windows to go over the screen", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", "Speed to scroll the windows at", 0, 10, 20, 0);
    public static final Setting<NameModes> nameMode = new Setting<>("Names", "Changes how the names function", NameModes.Shrink);
    public static final Setting<IndicatorModes> indicatorMode = new Setting<>("Indicators", "Changes how the indicators function", IndicatorModes.Shrink);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", "Controls how the game is paused when the ClickGUI is open", PauseModes.Continue);

    public ClickGUI() {
        this.addSettings(
                theme,
                windowOverflow,
                scrollSpeed,
                nameMode,
                indicatorMode,
                pauseGame
        );
    }

    private ClickGUIScreen clickGUI;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (clickGUI == null) {
            clickGUI = new ClickGUIScreen();
        }

        mc.displayGuiScreen(clickGUI);
    }

    public enum ThemeModes {
        Default
    }

    public enum NameModes {
        Shrink,
        Stay
    }

    public enum IndicatorModes {
        Shrink,
        Stay
    }

    public enum PauseModes {
        Pause,
        Continue
    }
}

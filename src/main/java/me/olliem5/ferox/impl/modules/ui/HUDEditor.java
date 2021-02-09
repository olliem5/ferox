package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.screens.editor.HUDEditorScreen;

/**
 * @author olliem5
 */

@FeroxModule(name = "HUDEditor", description = "Opens Ferox's HUDEditor", category = Category.UI)
public final class HUDEditor extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", "The theme to use for the HUDEditor", ThemeModes.Default);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", "Allows windows to go over the screen", false);
    public static final Setting<Boolean> componentOverflow = new Setting<>("Component Overflow", "Allows components to go over the screen", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", "Speed to scroll the windows at", 0, 10, 20, 0);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", "Controls how the game is paused when the HUDEditor is open", PauseModes.Continue);

    public HUDEditor() {
        this.addSettings(
                theme,
                windowOverflow,
                componentOverflow,
                scrollSpeed,
                pauseGame
        );
    }

    private HUDEditorScreen hudEditor;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (hudEditor == null) {
            hudEditor = new HUDEditorScreen();
        }

        mc.displayGuiScreen(hudEditor);
    }

    public enum ThemeModes {
        Default
    }

    public enum PauseModes {
        Pause,
        Continue
    }
}

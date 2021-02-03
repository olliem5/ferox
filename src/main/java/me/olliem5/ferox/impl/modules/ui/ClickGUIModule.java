package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.click.main.BaseGui;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.UI, key = Keyboard.KEY_P)
public class ClickGUIModule extends Module {
    public static Setting<ThemeModes> theme = new Setting<>("Theme", ThemeModes.Default);
    public static Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", false);
    public static NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", 0, 10, 20, 0);
    public static Setting<PauseModes> pauseGame = new Setting<>("Pause Game", PauseModes.Continue);

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

package us.ferox.client.impl.modules.ui;

import org.lwjgl.input.Keyboard;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.impl.gui.click.main.BaseGui;

@ModuleInfo(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.UI, key = Keyboard.KEY_P)
public class ClickGUIModule extends Module {
    public static Setting<ThemeModes> theme = new Setting<>("Theme", ThemeModes.Default);
    public static Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", false);
    public static NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", 0, 10, 20, 0);
    public static Setting<PauseModes> pauseGame = new Setting<>("Pause Game", PauseModes.Continue);

    private BaseGui clickGUI;

    public ClickGUIModule() {
        this.addSetting(theme);
        this.addSetting(windowOverflow);
        this.addSetting(scrollSpeed);
        this.addSetting(pauseGame);
    }

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

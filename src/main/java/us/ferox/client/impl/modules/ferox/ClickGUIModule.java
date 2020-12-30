package us.ferox.client.impl.modules.ferox;

import org.lwjgl.input.Keyboard;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.impl.gui.ClickGUI;

@ModuleInfo(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.FEROX, key = Keyboard.KEY_P)
public class ClickGUIModule extends Module {
    public static Setting<Boolean> rainbow = new Setting("Rainbow", true);

    private ClickGUI clickGUI;

    @Override
    public void onEnable() {
        if (clickGUI == null) clickGUI = new ClickGUI();
        mc.displayGuiScreen(clickGUI);
        toggle();
    }
}

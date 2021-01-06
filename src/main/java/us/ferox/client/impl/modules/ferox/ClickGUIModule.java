package us.ferox.client.impl.modules.ferox;

import org.lwjgl.input.Keyboard;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.impl.gui.click.ClickGUI;

@ModuleInfo(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.FEROX, key = Keyboard.KEY_P)
public class ClickGUIModule extends Module {
    private ClickGUI clickGUI;

    @Override
    public void onEnable() {
        if (clickGUI == null) {
            clickGUI = new ClickGUI();
        }
        mc.displayGuiScreen(clickGUI);

        toggle();
    }
}

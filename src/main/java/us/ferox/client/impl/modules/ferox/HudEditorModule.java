package us.ferox.client.impl.modules.ferox;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.impl.gui.editor.HudEditor;

@ModuleInfo(name = "HudEditor", description = "Opens Ferox's HudEditor", category = Category.FEROX)
public class HudEditorModule extends Module {
    private HudEditor hudEditor;

    @Override
    public void onEnable() {
        if (hudEditor == null) {
            hudEditor = new HudEditor();
        }
        mc.displayGuiScreen(hudEditor);

        toggle();
    }
}

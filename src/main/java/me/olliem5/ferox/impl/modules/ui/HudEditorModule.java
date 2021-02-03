package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.editor.HudEditor;

@ModuleInfo(name = "HudEditor", description = "Opens Ferox's HudEditor", category = Category.UI)
public class HudEditorModule extends Module {
    public static Setting<Boolean> componentOverflow = new Setting<>("Component Overflow", false);

    public HudEditorModule() {
        this.addSettings(
                componentOverflow
        );
    }

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

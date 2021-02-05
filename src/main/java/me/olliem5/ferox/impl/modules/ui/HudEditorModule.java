package me.olliem5.ferox.impl.modules.ui;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.gui.screens.editor.HudEditor;

@FeroxModule(name = "HudEditor", description = "Opens Ferox's HudEditor", category = Category.UI)
public final class HudEditorModule extends Module {
    public static final Setting<Boolean> componentOverflow = new Setting<>("Component Overflow", "Allows components to go over the screen", false);

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

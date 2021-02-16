package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

import java.awt.*;

@FeroxModule(name = "Colours", description = "Controls Ferox's global client colours", category = Category.Ferox)
public final class Colours extends Module {
    public static final Setting<Boolean> clientColour = new Setting<>("Client Colour", "The global colour for Ferox", true);
    public static final Setting<Color> clientColourPicker = new Setting<>(clientColour, "Client Colour Picker", "The global colour for Ferox", new Color(98, 35, 188, 255));

    public Colours() {
        this.addSettings(
                clientColour
        );

        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        this.setEnabled(true);
    }
}

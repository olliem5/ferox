package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Font", description = "Changes the font that Ferox uses", category = Category.Ferox)
public final class Font extends Module {
    public static final Setting<FontModes> font = new Setting<>("Font", "The style of font to render", FontModes.Ubuntu);
    public static final Setting<Boolean> shadow = new Setting<>("Shadow", "Allows the font to have a shadow", true);
    public static final Setting<Boolean> lowercase = new Setting<>("Lowercase", "Sets the font to all lowercase", false);

    public Font() {
        this.addSettings(
                font,
                shadow,
                lowercase
        );

        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        this.setEnabled(true);
    }

    public String getArraylistInfo() {
        return font.getValue().toString();
    }

    public enum FontModes {
        Ubuntu,
        Lato,
        Verdana,
        Comfortaa,
        Subtitle,
        Minecraft
    }
}

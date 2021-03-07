package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Font", description = "Changes the font that Ferox uses", category = Category.Ferox)
public final class ClientFont extends Module {
    public static final Setting<FontModes> font = new Setting<>("Font", "The style of font to render", FontModes.Ubuntu);
    public static final Setting<Boolean> overrideMinecraft = new Setting<>("Override Minecraft", "Makes Ferox's font replace the Minecraft font", false);
    public static final Setting<Boolean> shadow = new Setting<>("Shadow", "Allows the font to have a shadow", true);
    public static final Setting<Boolean> lowercase = new Setting<>("Lowercase", "Sets the font to all lowercase", false);

    public ClientFont() {
        this.addSettings(
                font,
                overrideMinecraft,
                shadow,
                lowercase
        );

        this.setEnabled(true);
        this.setDrawn(false);
    }

    @Override
    public String getArraylistInfo() {
        return font.getValue().toString();
    }

    public enum FontModes {
        Ubuntu,
        Lato,
        Verdana,
        Comfortaa,
        Subtitle,
        ComicSans
    }
}

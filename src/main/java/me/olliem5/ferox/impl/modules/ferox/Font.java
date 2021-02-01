package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;

@ModuleInfo(name = "Font", description = "Changes the font that Ferox uses", category = Category.FEROX)
public class Font extends Module {
    public static Setting<FontModes> font = new Setting<>("Font", FontModes.Ubuntu);
    public static Setting<Boolean> shadow = new Setting<>("Shadow", true);
    public static Setting<Boolean> lowercase = new Setting<>("Lowercase", false);

    public Font() {
        this.addSetting(font);
        this.addSetting(shadow);
        this.addSetting(lowercase);

        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
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

package me.olliem5.ferox.impl.hud;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.hud.FeroxComponent;
import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.setting.Setting;

@FeroxComponent(name = "Watermark")
public class WatermarkComponent extends HudComponent {
    public static final Setting<WatermarkModes> watermarkMode = new Setting<>("Mode", "The watermark mode", WatermarkModes.NameVersion);

    public WatermarkComponent() {
        setHeight(10);
        setWidth(10);

        this.addSettings(
                watermarkMode
        );
    }

    @Override
    public void render() {
        switch (watermarkMode.getValue()) {
            case Name:
                drawString(Ferox.MOD_NAME);
                break;
            case NameVersion:
                drawString(Ferox.NAME_VERSION);
                break;
        }
    }

    public enum WatermarkModes {
        Name,
        NameVersion
    }
}

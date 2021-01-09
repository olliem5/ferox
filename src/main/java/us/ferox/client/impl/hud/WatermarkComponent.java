package us.ferox.client.impl.hud;

import us.ferox.client.Ferox;
import us.ferox.client.api.hud.ComponentInfo;
import us.ferox.client.api.hud.HudComponent;
import us.ferox.client.api.setting.Setting;

@ComponentInfo(name = "Watermark")
public class WatermarkComponent extends HudComponent {
    public static Setting<WatermarkModes> mode = new Setting<>("Mode", WatermarkModes.NameVersion);

    public WatermarkComponent() {
        setHeight(10);
        setWidth(10);

        this.addSetting(mode);

        visible = true;
    }

    @Override
    public void render() {
        switch (mode.getValue()) {
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

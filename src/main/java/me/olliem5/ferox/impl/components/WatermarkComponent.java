package me.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.component.FeroxComponent;
import me.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Watermark", description = "Shows client information")
public final class WatermarkComponent extends Component {
    public static final Setting<WatermarkModes> watermarkMode = new Setting<>("Mode", "The watermark mode", WatermarkModes.NameVersion);

    public WatermarkComponent() {
        this.setWidth(10);
        this.setHeight(10);

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
                drawString(Ferox.MOD_NAME + " " + ChatFormatting.WHITE + Ferox.MOD_VERSION);
                break;
        }
    }

    public enum WatermarkModes {
        Name,
        NameVersion
    }
}

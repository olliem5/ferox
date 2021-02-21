package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Watermark", description = "Shows client information")
public final class WatermarkComponent extends Component {
    public static final Setting<WatermarkModes> watermarkMode = new Setting<>("Mode", "The watermark mode", WatermarkModes.NameVersion);

    public WatermarkComponent() {
        this.addSettings(
                watermarkMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (watermarkMode.getValue()) {
            case Name:
                renderString = Ferox.MOD_NAME;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case NameVersion:
                renderString = Ferox.MOD_NAME + " " + ChatFormatting.WHITE + Ferox.MOD_VERSION;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    public enum WatermarkModes {
        Name,
        NameVersion
    }
}

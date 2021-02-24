package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "FPS", description = "Shows the amount of fps you have")
public class FPSComponent extends Component {
    public static final Setting<FPSModes> fpsMode = new Setting<>("Mode", "Mode", FPSModes.Normal);

    public FPSComponent() {
        this.addSettings(
                fpsMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (fpsMode.getValue()) {
            case Normal:
                renderString = "FPS " + ChatFormatting.WHITE + mc.getDebugFPS();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case OnlyNumber:
                renderString = "" + ChatFormatting.WHITE + mc.getDebugFPS();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    public enum FPSModes {
        Normal,
        OnlyNumber
    }
}

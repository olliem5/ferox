package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.Minecraft;

@FeroxComponent(name = "FPS", description = "Shows the amount of fps you have")
public class FPSComponent extends Component {

    Setting<FPSModes> fpsmodes = new Setting("Mode","Mode",FPSModes.FPS);

    public FPSComponent() {
        addSettings(
                fpsmodes
        );

    }
    @Override
    public void render() {
        String fps;

        switch ((FPSModes) fpsmodes.getValue()) {

            case FPS: {
                fps = "FPS " + ChatFormatting.WHITE + Minecraft.getDebugFPS();
                drawString(fps);
                this.setHeight((int) FontUtil.getStringHeight(fps));
                this.setWidth((int) FontUtil.getStringWidth(fps));
                break;
            }
            case OnlyNumber: {
                fps = "" + ChatFormatting.WHITE + Minecraft.getDebugFPS();
                drawString(fps);
                this.setWidth((int) FontUtil.getStringWidth(fps));
                this.setHeight((int) FontUtil.getStringHeight(fps));
                break;
            }

        }
    }

    public enum FPSModes {
        FPS,OnlyNumber,
    }
}

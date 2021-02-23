package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;


@FeroxComponent(name = "Ping", description = "Shows your ping on screen")
public class PingComponent extends Component {

    Setting<PingModes> pingmode = new Setting("Mode","Mode",PingModes.Ping);

    public PingComponent() {
        addSettings(
                pingmode
        );
    }
    @Override
    public void render() {
        String pingText;

        switch ((PingModes) pingmode.getValue()) {
            case Ping: {
                pingText = "Ping " + getPing() + "ms";
                drawString(pingText);
                this.setWidth((int) FontUtil.getStringWidth(pingText));
                this.setHeight((int) FontUtil.getStringHeight(pingText));
                break;
            }
            case OnlyNumber: {
                pingText = "" + getPing();
                drawString(pingText);
                this.setWidth((int) FontUtil.getStringWidth(pingText));
                this.setHeight((int) FontUtil.getStringHeight(pingText));
                break;
            }
        }
    }
    public int getPing() {
        if (this.mc.player != null && this.mc.getConnection() != null && this.mc.getConnection().getPlayerInfo(this.mc.player.getName()) != null) {
            return this.mc.getConnection().getPlayerInfo(this.mc.player.getName()).getResponseTime();
        } else {
            return -1;
        }
    }

    public enum PingModes {
        Ping,OnlyNumber
    }
}

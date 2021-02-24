package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 * hihihihi
 */


@FeroxComponent(name = "Server IP", description = "Displays the ip of the server you are on, on screen")
public class ServerIPComponent extends Component {

    Setting<IPModes> ipmode = new Setting("Mode","Mode",IPModes.Normal);

    public ServerIPComponent() {
        addSettings(
                ipmode
        );
    }

    @Override
    public void render() {
        String ServerIPText;
        if (mc.integratedServerIsRunning) {
            switch ((IPModes) ipmode.getValue()) {
                case Normal: {
                    ServerIPText = "Server IP " + ChatFormatting.WHITE + "SinglePlayer";
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
                case IP: {
                    ServerIPText = "IP " + ChatFormatting.WHITE + "SinglePlayer";
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
                case OnlyIP: {
                    ServerIPText = "" + ChatFormatting.WHITE + "SinglePlayer";
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
            }
        } else {
            switch ((IPModes) ipmode.getValue()) {
                case Normal: {
                    ServerIPText = "Server IP " + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
                case IP: {
                    ServerIPText = "IP " + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
                case OnlyIP: {
                    ServerIPText = "" + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    drawString(ServerIPText);
                    this.setHeight((int) FontUtil.getStringHeight(ServerIPText));
                    this.setWidth((int) FontUtil.getStringWidth(ServerIPText));
                    break;
                }
            }
        }
    }

    public enum IPModes {
        Normal,IP,OnlyIP
    }
}

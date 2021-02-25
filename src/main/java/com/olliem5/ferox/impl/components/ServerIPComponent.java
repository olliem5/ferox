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

@FeroxComponent(name = "Server IP", description = "Displays the ip of the server you are on, on screen")
public class ServerIPComponent extends Component {
    public static final Setting<IPModes> ipMode = new Setting<>("Mode", "The way of displaying the server IP", IPModes.Normal);

    public ServerIPComponent() {
        this.addSettings(
                ipMode
        );
    }

    @Override
    public void render() {
        String renderString;

        if (mc.integratedServerIsRunning) {
            switch (ipMode.getValue()) {
                case Normal:
                    renderString = "Server IP " + ChatFormatting.WHITE + "Singleplayer";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case IP:
                    renderString = "IP " + ChatFormatting.WHITE + "Singleplayer";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + "Singleplayer";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
            }
        } else {
            switch (ipMode.getValue()) {
                case Normal:
                    renderString = "Server IP " + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case IP:
                    renderString = "IP " + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + mc.getCurrentServerData().serverIP;
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
            }
        }
    }

    public enum IPModes {
        Normal,
        IP,
        Short
    }
}

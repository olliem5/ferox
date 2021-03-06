package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

import java.util.Objects;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "ServerIP", description = "Displays the ip of the server you are on, on screen")
public final class ServerComponent extends Component {
    public static final Setting<IPModes> ipMode = new Setting<>("Mode", "The way of displaying the server IP", IPModes.Normal);

    public ServerComponent() {
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
                    renderString = "Server " + ChatFormatting.WHITE + "Singleplayer";
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
                    renderString = "Server " + ChatFormatting.WHITE + Objects.requireNonNull(mc.getCurrentServerData()).serverIP;
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case IP:
                    renderString = "IP " + ChatFormatting.WHITE + Objects.requireNonNull(mc.getCurrentServerData()).serverIP;
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + Objects.requireNonNull(mc.getCurrentServerData()).serverIP;
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

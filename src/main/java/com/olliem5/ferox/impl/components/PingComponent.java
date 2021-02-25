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

@FeroxComponent(name = "Ping", description = "Shows your ping on screen")
public final class PingComponent extends Component {
    public static final Setting<PingModes> pingMode = new Setting<>("Mode", "The way of displaying the ping", PingModes.Normal);

    public PingComponent() {
        this.addSettings(
                pingMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (pingMode.getValue()) {
            case Normal:
                renderString = "Ping " + ChatFormatting.WHITE + getPing() + "ms";
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "" + ChatFormatting.WHITE + getPing() + "ms";
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    private int getPing() {
        if (mc.player != null && mc.getConnection() != null && mc.getConnection().getPlayerInfo(mc.player.getName()) != null) {
            return mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }

        return -1;
    }

    public enum PingModes {
        Normal,
        Short
    }
}

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

@FeroxComponent(name = "Welcomer", description = "Shows a nice welcome message")
public final class WelcomerComponent extends Component {
    public static final Setting<WelcomerModes> welcomerMode = new Setting<>("Mode", "The welcomer mode", WelcomerModes.Welcome);

    public WelcomerComponent() {
        this.addSettings(
                welcomerMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (welcomerMode.getValue()) {
            case Welcome:
                renderString = ChatFormatting.WHITE + "Welcome, " + ChatFormatting.RESET + mc.player.getName();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Client:
                renderString = ChatFormatting.WHITE + "Welcome to " + ChatFormatting.RESET + Ferox.MOD_NAME + ChatFormatting.WHITE + " " + Ferox.MOD_VERSION + ", " + ChatFormatting.RESET + mc.player.getName();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    public enum WelcomerModes {
        Welcome,
        Client
    }
}

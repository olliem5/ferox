package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Welcomer", description = "Shows a nice welcome message")
public final class WelcomerComponent extends Component {
    public static final Setting<WelcomerModes> welcomerMode = new Setting<>("Mode", "The welcomer mode", WelcomerModes.Welcome);

    public WelcomerComponent() {
        this.setWidth(10);
        this.setHeight(10);

        this.addSettings(
                welcomerMode
        );
    }

    @Override
    public void render() {
        switch (welcomerMode.getValue()) {
            case Welcome:
                drawString(ChatFormatting.WHITE + "Welcome, " + ChatFormatting.RESET + mc.player.getName());
                break;
            case Client:
                drawString(ChatFormatting.WHITE + "Welcome to " + ChatFormatting.RESET + Ferox.MOD_NAME + ChatFormatting.WHITE + " " + Ferox.MOD_VERSION + ", " + ChatFormatting.RESET + mc.player.getName());
                break;
        }
    }

    public enum WelcomerModes {
        Welcome,
        Client
    }
}

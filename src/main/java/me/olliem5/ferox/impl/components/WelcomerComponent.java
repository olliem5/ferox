package me.olliem5.ferox.impl.components;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.component.FeroxComponent;
import me.olliem5.ferox.api.setting.Setting;

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
                drawString("Welcome, " + mc.player.getName());
                break;
            case Client:
                drawString("Welcome to " + Ferox.NAME_VERSION + ", " + mc.player.getName());
                break;
        }
    }

    public enum WelcomerModes {
        Welcome,
        Client
    }
}

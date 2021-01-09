package us.ferox.client.impl.hud;

import us.ferox.client.Ferox;
import us.ferox.client.api.hud.ComponentInfo;
import us.ferox.client.api.hud.HudComponent;
import us.ferox.client.api.setting.Setting;

@ComponentInfo(name = "Welcomer")
public class WelcomerComponent extends HudComponent {
    public static Setting<WelcomerModes> mode = new Setting<>("Mode", WelcomerModes.Welcome);

    public WelcomerComponent() {
        setHeight(10);
        setWidth(10);

        this.addSetting(mode);
    }

    @Override
    public void render() {
        switch (mode.getValue()) {
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

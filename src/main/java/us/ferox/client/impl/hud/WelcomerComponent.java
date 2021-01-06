package us.ferox.client.impl.hud;

import us.ferox.client.Ferox;
import us.ferox.client.api.hud.HudComponent;

public class WelcomerComponent extends HudComponent {
    public WelcomerComponent() {
        super("Welcomer", 2, 2);
        setWidth(10);
        setHeight(10);
        visible = true;
    }

    @Override
    public void render() {
        drawString("Welcome to " + Ferox.NAME_VERSION + ", " + mc.player.getName());
    }
}

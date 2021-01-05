package us.ferox.client.impl.hud;

import us.ferox.client.Ferox;
import us.ferox.client.api.hud.HudComponent;

public class WelcomerComponent extends HudComponent {
    public WelcomerComponent() {
        super("Welcomer", 10, 10);
        visible = true;
    }

    @Override
    public void render() {
        drawString("Welcome, " + mc.player.getName() + " to " + Ferox.MOD_NAME);
    }
}

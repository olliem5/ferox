package us.ferox.client.impl.hud;

import us.ferox.client.Ferox;
import us.ferox.client.api.hud.ComponentInfo;
import us.ferox.client.api.hud.HudComponent;
import us.ferox.client.api.util.font.FontUtil;

@ComponentInfo(name = "Welcomer")
public class WelcomerComponent extends HudComponent {
    public WelcomerComponent() {
        setHeight(10);
        setWidth(10);
        visible = true;
    }

    @Override
    public void render() {
        if(getWidth() == 10) setWidth((int) FontUtil.getStringWidth("Welcome to " + Ferox.NAME_VERSION + ", " + mc.player.getName()));
        drawString("Welcome to " + Ferox.NAME_VERSION + ", " + mc.player.getName());
    }
}

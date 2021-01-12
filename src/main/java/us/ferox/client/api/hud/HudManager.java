package us.ferox.client.api.hud;

import git.littledraily.eventsystem.Listener;
import git.littledraily.eventsystem.event.Priority;
import us.ferox.client.Ferox;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.api.util.colour.RainbowUtil;
import us.ferox.client.impl.events.GameOverlayRenderEvent;
import us.ferox.client.impl.hud.InventoryComponent;
import us.ferox.client.impl.hud.PlayerComponent;
import us.ferox.client.impl.hud.WatermarkComponent;
import us.ferox.client.impl.hud.WelcomerComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HudManager implements Minecraft {
    private static final HudManager INSTANCE = new HudManager();

    private final List<HudComponent> components = new ArrayList<>();
    private boolean rainbow = false;
    private Color color = new Color(255, 255, 255, 255);

    public static void init() {
        Ferox.EVENT_BUS.subscribe(INSTANCE);

        INSTANCE.components.addAll(Arrays.asList(
                new WelcomerComponent(),
                new InventoryComponent(),
                new WatermarkComponent(),
                new PlayerComponent()
        ));
    }

    public static List<HudComponent> getComponents() {
        return INSTANCE.components;
    }

    public static boolean isRainbow() {
        return INSTANCE.rainbow;
    }

    public static void setRainbow(boolean rainbow) {
        INSTANCE.rainbow = rainbow;
    }

    public static int getColor() {
        return INSTANCE.rainbow ? RainbowUtil.getRainbow().getRGB() : INSTANCE.color.getRGB();
    }

    public void setColor(int r, int g, int b, int a) {
        color = new Color(r, g, b, a);
    }

    @Listener(priority = Priority.LOWEST)
    public void onGameOverlayRender(GameOverlayRenderEvent event) {
        if (mc.world == null || mc.player == null) return;

        for (HudComponent component : components) {
            if (component.isVisible()) component.render();
        }
    }
}

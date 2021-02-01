package me.olliem5.ferox.api.hud;

import git.littledraily.eventsystem.Listener;
import git.littledraily.eventsystem.event.Priority;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.impl.events.GameOverlayRenderEvent;
import me.olliem5.ferox.impl.hud.InventoryComponent;
import me.olliem5.ferox.impl.hud.PlayerComponent;
import me.olliem5.ferox.impl.hud.WatermarkComponent;
import me.olliem5.ferox.impl.hud.WelcomerComponent;
import me.olliem5.ferox.Ferox;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HudManager implements Minecraft {
    private static final HudManager INSTANCE = new HudManager();

    private final List<HudComponent> components = new ArrayList<>();

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

    @Listener(priority = Priority.LOWEST)
    public void onGameOverlayRender(GameOverlayRenderEvent event) {
        if (mc.world == null || mc.player == null) return;

        for (HudComponent component : components) {
            if (component.isVisible()) {
                component.render();
            }
        }
    }
}

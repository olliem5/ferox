package me.olliem5.ferox.api.hud;

import git.littledraily.eventsystem.Listener;
import git.littledraily.eventsystem.event.Priority;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.events.GameOverlayRenderEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class HudManager implements Minecraft {
    private static ArrayList<HudComponent> components = new ArrayList<>();

    public static void init() {
        Reflections reflections = new Reflections("me.olliem5.ferox.impl.hud");

        reflections.getSubTypesOf(HudComponent.class).forEach(clazz -> {

            try {
                HudComponent hudComponent = clazz.getConstructor().newInstance();
                components.add(hudComponent);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static ArrayList<HudComponent> getComponents() {
        return components;
    }

    @Listener(priority = Priority.LOWEST)
    public void onGameOverlayRender(GameOverlayRenderEvent event) {
        if (mc.world == null || mc.player == null) return;

        for (HudComponent hudComponent : components) {
            if (hudComponent.isVisible()) {
                hudComponent.render();
            }
        }
    }
}

package me.olliem5.ferox.api.component;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.components.*;
import me.olliem5.pace.annotation.PaceHandler;
import me.olliem5.pace.modifier.EventPriority;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author olliem5
 */

public final class ComponentManager implements Minecraft {
    private static final ArrayList<Component> components = new ArrayList<>();

    public static void init() {
        components.addAll(Arrays.asList(
                new InventoryComponent(),
                new PlayerComponent(),
                new WatermarkComponent(),
                new WelcomerComponent(),
                new NotificationComponent()
        ));
    }

    public static ArrayList<Component> getComponents() {
        return components;
    }

    @PaceHandler(priority = EventPriority.LOWEST)
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        if (mc.world == null || mc.player == null) return;

        for (Component hudComponent : components) {
            if (hudComponent.isVisible()) {
                hudComponent.render();
            }
        }
    }
}

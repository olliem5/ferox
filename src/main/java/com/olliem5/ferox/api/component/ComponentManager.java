package com.olliem5.ferox.api.component;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.components.*;
import com.olliem5.pace.annotation.PaceHandler;
import com.olliem5.pace.modifier.EventPriority;
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
                new ArraylistComponent(),
                new BiomeComponent(),
                new CoordinatesComponent(),
                new CrystalCountComponent(),
                new DimensionComponent(),
                new DirectionComponent(),
                new DurabilityComponent(),
                new EXPCountComponent(),
                new FPSComponent(),
                new GamemodeComponent(),
                new GappleCountComponent(),
                new InventoryComponent(),
                new LogoComponent(),
                new NotificationComponent(),
                new PingComponent(),
                new PlayerComponent(),
                new PlayerCountComponent(),
                new PvPInfoComponent(),
                new ServerComponent(),
                new SpeedComponent(),
                new TabGUIComponent(),
                new TimeComponent(),
                new TotemCountComponent(),
                new TPSComponent(),
                new WatermarkComponent(),
                new WelcomerComponent()
        ));

        components.sort(ComponentManager::alphabetize);
    }

    private static int alphabetize(Component component1, Component component2) {
        return component1.getName().compareTo(component2.getName());
    }

    public static ArrayList<Component> getComponents() {
        return components;
    }

    @PaceHandler(priority = EventPriority.LOWEST)
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        if (mc.world == null || mc.player == null) return;

        for (Component hudComponent : components) {
            if (hudComponent.isVisible() && ModuleManager.getModuleByName("HUD").isEnabled()) {
                hudComponent.render();
            }
        }
    }
}

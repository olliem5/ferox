package me.olliem5.ferox.impl.events;

import net.minecraft.client.renderer.RenderGlobal;
import me.olliem5.ferox.api.event.Event;

public class WorldRenderEvent extends Event {
    private final RenderGlobal context;
    private final float partialTicks;

    public WorldRenderEvent(RenderGlobal context, float partialTicks) {
        this.context = context;
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public RenderGlobal getContext() {
        return context;
    }
}
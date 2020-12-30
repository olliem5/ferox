package us.ferox.client.impl.events;

import net.minecraft.entity.Entity;
import us.ferox.client.api.event.Event;

public class TotemPopEvent extends Event {
    public final Entity entity;

    public TotemPopEvent(Entity entity) {
        this.entity = entity;
    }
}
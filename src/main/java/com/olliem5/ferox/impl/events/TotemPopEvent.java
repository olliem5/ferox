package com.olliem5.ferox.impl.events;

import me.olliem5.pace.event.Event;
import net.minecraft.entity.Entity;

/**
 * @author olliem5
 */

public final class TotemPopEvent extends Event {
    public final Entity entity;

    public TotemPopEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
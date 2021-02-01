package me.olliem5.ferox.impl.events;

import net.minecraft.entity.Entity;
import me.olliem5.ferox.api.event.Event;

public class TotemPopEvent extends Event {
    public final Entity entity;

    public TotemPopEvent(Entity entity) {
        this.entity = entity;
    }
}
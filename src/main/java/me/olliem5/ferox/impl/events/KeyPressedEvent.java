package me.olliem5.ferox.impl.events;

import me.olliem5.ferox.api.event.Event;

public final class KeyPressedEvent extends Event {
    final int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }
}
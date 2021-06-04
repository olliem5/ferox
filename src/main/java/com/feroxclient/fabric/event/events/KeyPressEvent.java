package com.feroxclient.fabric.event.events;

import com.feroxclient.fabric.event.Event;

public final class KeyPressEvent extends Event {
    private final int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
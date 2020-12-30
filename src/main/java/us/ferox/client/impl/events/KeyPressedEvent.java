package us.ferox.client.impl.events;

import us.ferox.client.api.event.Event;

public class KeyPressedEvent extends Event {
    final int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }
}
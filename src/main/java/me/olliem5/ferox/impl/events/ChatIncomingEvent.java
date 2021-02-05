package me.olliem5.ferox.impl.events;

import me.olliem5.ferox.api.event.Event;

public final class ChatIncomingEvent extends Event {
    private String message;

    public ChatIncomingEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

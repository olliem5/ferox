package us.ferox.client.impl.events;

import us.ferox.client.api.event.Event;

public class ChatIncomingEvent extends Event {
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

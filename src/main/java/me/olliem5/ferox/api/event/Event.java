package me.olliem5.ferox.api.event;

import git.littledraily.eventsystem.cancellable.Cancellable;
import git.littledraily.eventsystem.event.EventState;

public abstract class Event extends Cancellable {
    private EventState eventState = EventState.PRE;

    public Event() {}

    public Event(EventState eventState) {
        this.eventState = eventState;
    }

    public EventState getEventState() {
        return eventState;
    }
}

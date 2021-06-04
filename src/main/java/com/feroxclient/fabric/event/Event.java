package com.feroxclient.fabric.event;

public abstract class Event {
    private final EventEra eventEra;

    public Event(EventEra eventEra) {
        this.eventEra = eventEra;
    }

    public Event() {
        this.eventEra = EventEra.PRE;
    }

    private boolean cancelled;

    public EventEra getEventEra() {
        return eventEra;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
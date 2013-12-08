package com.github.jamesnorris.flow.event.responder;

public abstract class EventResponder {
    private Class<org.bukkit.event.Event> eventClass;

    @SuppressWarnings("unchecked") public <T extends org.bukkit.event.Event> EventResponder(Class<T> eventClass) {
        this.eventClass = (Class<org.bukkit.event.Event>) eventClass;
    }

    public abstract void respond(org.bukkit.event.Event evt);

    public Class<org.bukkit.event.Event> getEventClass() {
        return eventClass;
    }
}

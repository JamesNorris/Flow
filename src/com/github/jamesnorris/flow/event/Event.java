package com.github.jamesnorris.flow.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.jamesnorris.flow.event.responder.EventResponder;

public class Event implements Listener {
    private List<EventResponder> responders = new ArrayList<EventResponder>();

    public Event() {}

    public Event(EventResponder[] responders) {
        this.responders.addAll(Arrays.asList(responders));
    }

    public Event(Collection<EventResponder> responders) {
        this.responders.addAll(responders);
    }

    @EventHandler(priority = EventPriority.HIGHEST) protected void ALL_EVENTS(org.bukkit.event.Event event) {
        for (EventResponder responder : responders) {
            if (responder.getEventClass().equals(event.getClass())) {
                responder.respond(event);
            }
        }
    }

    public void addResponder(EventResponder responder) {
        responders.add(responder);
    }

    public void removeResponder(EventResponder responder) {
        responders.remove(responder);
    }

    public void clearResponders() {
        responders.clear();
    }

    public EventResponder getResponder(int index) {
        return responders.get(index);
    }
}

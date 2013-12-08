package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.jamesnorris.flow.event.responder.EventResponder;

public class PlayerInteractResponder extends EventResponder {
    public PlayerInteractResponder() {
        // not added to data via Flow.java
        super(PlayerInteractEvent.class);
    }

    @Override public void respond(Event evt) {
        // overridden in CommandBase.java for the height command
    }
}

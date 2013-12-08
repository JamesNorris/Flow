package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFormEvent;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class BlockFormResponder extends EventResponder {
    public BlockFormResponder() {
        super(BlockFormEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockFormEvent event = (BlockFormEvent) evt;
        Block block = event.getBlock();
        if (block.getType() == Material.ICE && !block.isLiquid() && (Boolean) Setting.PREVENT_WATER_FREEZE.get()) {
            event.setCancelled(true);
        }
    }
}

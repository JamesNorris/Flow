package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.github.jamesnorris.flow.event.responder.EventResponder;
import com.github.jamesnorris.flow.util.FlowUtility;

public class BlockPhysicsResponder extends EventResponder {
    public BlockPhysicsResponder() {
        super(BlockPhysicsEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockPhysicsEvent event = (BlockPhysicsEvent) evt;
        Block block = event.getBlock();
        for (BlockFace face : FlowUtility.BLOCKFACES_NSEW) {
            Block relative = block.getRelative(face);
            FlowUtility.proximityChange(block, relative);
        }
    }
}

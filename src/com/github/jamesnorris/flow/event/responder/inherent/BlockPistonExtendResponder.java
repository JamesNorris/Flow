package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPistonExtendEvent;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.event.responder.EventResponder;
import com.github.jamesnorris.flow.util.FlowUtility;

public class BlockPistonExtendResponder extends EventResponder {
    public BlockPistonExtendResponder() {
        super(BlockPistonExtendEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockPistonExtendEvent event = (BlockPistonExtendEvent) evt;
        int length = event.getLength();
        Block block = event.getBlock();
        if ((Boolean) Setting.ENABLE_REDSTONE_LIQUID_CONTROL.get() && (Boolean) Setting.ENABLE_PISTONS_PUSHING_LIQUIDS.get()) {
            FlowUtility.pushLiquid(block, event.getDirection(), true, length);
        }
    }
}

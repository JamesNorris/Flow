package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFadeEvent;

import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.block.IceBlock;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class BlockFadeResponder extends EventResponder {
    public BlockFadeResponder() {
        super(BlockFadeEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockFadeEvent event = (BlockFadeEvent) evt;
        Block block = event.getBlock();
        if (block.getType() == Material.ICE) {
            DerivedBlock derived = DerivedBlock.fromBlock(block, true);
            IceBlock ice = derived instanceof IceBlock ? (IceBlock) derived : new IceBlock(block);
            ice.melt();
        }
    }
}

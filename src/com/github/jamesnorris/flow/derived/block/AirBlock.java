package com.github.jamesnorris.flow.derived.block;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.github.jamesnorris.flow.derived.DerivedBlock;

public class AirBlock extends DerivedBlock {
    public AirBlock(Location loc) {
        this(loc.getBlock());
    }

    public AirBlock(Block block) {
        super(block);
    }
}

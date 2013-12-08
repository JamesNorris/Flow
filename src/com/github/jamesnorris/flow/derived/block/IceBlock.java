package com.github.jamesnorris.flow.derived.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.derived.DerivedBlock;

public class IceBlock extends DerivedBlock {
    public IceBlock(Location loc) {
        this(loc.getBlock());
    }

    public IceBlock(Block block) {
        super(block);
    }

    public DerivedBlock melt() {
        if ((Boolean) Setting.CONTROL_MELTING.get() && (Boolean) Setting.PREVENT_ICE_MELT.get()) {
            block.setType(Material.AIR);
            return new AirBlock(block);
        }
        block.setType(Material.WATER);
        return new LiquidBlock(block);
    }

    public DerivedBlock freeze() {
        return null;// TODO
    }
}

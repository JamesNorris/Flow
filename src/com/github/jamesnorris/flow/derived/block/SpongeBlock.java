package com.github.jamesnorris.flow.derived.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.Stat;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.selection.Cube;
import com.github.jamesnorris.flow.util.LiquidUtility;

public class SpongeBlock extends DerivedBlock {
    private int effectRange;
    private Cube inner, outer;

    public SpongeBlock(Location loc, int effectRange) {
        this(loc.getBlock(), effectRange);
    }

    public SpongeBlock(Block block, int effectRange) {
        super(block);
        this.effectRange = effectRange;
        this.inner = new Cube(block.getLocation(), effectRange);
        this.outer = new Cube(block.getLocation(), effectRange + 1);
    }

    public int getEffectRange() {
        return effectRange;
    }

    public Cube getInnerCube() {
        return inner;
    }

    public Cube getOuterCube() {
        return outer;
    }

    public boolean effectContains(Block other) {
        return effectContains(other.getLocation());
    }

    public boolean effectContains(Location other) {
        Location loc = block.getLocation();
        boolean containedX = loc.getX() + effectRange >= other.getX() && loc.getX() - effectRange <= other.getX();
        boolean containedY = loc.getY() + effectRange >= other.getY() && loc.getY() - effectRange <= other.getY();
        boolean containedZ = loc.getZ() + effectRange >= other.getZ() && loc.getZ() - effectRange <= other.getZ();
        return containedX && containedY && containedZ;
    }

    public void removeWater() {
        if ((Boolean) Setting.ENABLE_SPONGES.get()) {
            for (Location loc : inner.getLocations()) {
                Block relative = loc.getBlock();
                if (LiquidUtility.isLiquid(relative)) {
                    relative.setType(Material.AIR);
                }
            }
            Stat.SPONGE_REMOVE_WATER_COUNT.increment();
        }
    }

    public void replaceWater() {
        if ((Boolean) Setting.ENABLE_SPONGES.get()) {
            for (Location loc : outer.getLocations()) {
                Block relative = loc.getBlock();
                if (LiquidUtility.isLiquid(relative)) {// if removeWater() is called, this will be the very outside layer
                    relative.setData(LiquidBlock.NEXT_HIGHEST, true/* applyPhysics */);
                }
            }
            Stat.SPONGE_REPLACE_WATER_COUNT.increment();
        }
    }
}

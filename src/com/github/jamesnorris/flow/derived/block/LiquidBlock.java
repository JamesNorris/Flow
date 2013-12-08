package com.github.jamesnorris.flow.derived.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.selection.Cube;
import com.github.jamesnorris.flow.selection.Sphere;
import com.github.jamesnorris.flow.util.LiquidUtility;

public class LiquidBlock extends DerivedBlock {
    public static final byte HIGHEST = 0x0, NEXT_HIGHEST = 0x1, HIGH = 0x2, MEDIUM_HIGH = 0x3, MEDIUM_LOW = 0x4, LOW = 0x5, NEXT_LOWEST = 0x6, LOWEST = 0x7, FALLING = 0x8;

    public LiquidBlock(Location loc) {
        this(loc.getBlock());
    }

    public LiquidBlock(Block block) {
        super(block);
    }

    public Cube getCubeAround(int radius) {
        return new Cube(block.getLocation(), radius);
    }

    @Override public void setFalling(boolean falling) {
        setHeight(falling ? FALLING : getHeight());
    }

    public void setHeight(byte height) {
        if (height < FALLING || height > HIGHEST) {
            return;
        }
        block.setData(height);
    }

    public byte getHeight() {
        return block.getData();
    }

    public void setSource() {
        setHeight(HIGHEST);
    }

    public void increaseHeight() {
        if (getHeight() == HIGHEST) {
            return;
        }
        setHeight((byte) (getHeight() - 1));
    }

    public void decreaseHeight() {
        if (isAtLowestHeight()) {
            return;
        }
        setHeight((byte) (getHeight() + 1));
    }

    public boolean isAtLowestHeight() {
        return getHeight() == getLowestHeight();
    }

    public byte getLowestHeight() {
        return isLava() ? MEDIUM_LOW : LOWEST;
    }

    public boolean isWater() {
        return LiquidUtility.isWater(block);
    }

    public boolean isLava() {
        return LiquidUtility.isLava(block);
    }

    public boolean isStationary() {
        return LiquidUtility.isStationaryLiquid(block);
    }

    public boolean isMobile() {
        return LiquidUtility.isMobileLiquid(block);
    }

    protected Sphere getMovementCircle(int moveDist) {// I TURNED A SPHERE INTO A CIRCLE!!!
        return new Sphere(block.getLocation(), moveDist) {
            @Override public boolean isInside(double x, double y, double z) {
                if (Location.locToBlock(y) != block.getLocation().getBlockY()) {
                    y = Double.NaN;
                }
                return super.isInside(x, y, z);
            }

            public Sphere recalculateSphere() {
                super.recalculate();
                return this;
            }
        }.recalculateSphere();
    }

    public boolean isSameLiquid(Block other) {
        return (isWater() && LiquidUtility.isWater(other)) || (isLava() && LiquidUtility.isLava(other));
    }

    public boolean canSpread() {
        if (isAtLowestHeight()) {
            return false;
        }
        Sphere sphere = getMovementCircle(getLowestHeight() - getHeight());
        for (Location location : sphere.getLocations()) {
            if (location.clone().subtract(0, 1, 0).getBlock().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public List<Block> getAttachedSources() {
        List<Block> sources = new ArrayList<Block>();
        Sphere sphere = getMovementCircle(getHeight());
        for (Location location : sphere.getLocations()) {
            Block block = location.getBlock();
            if (isSameLiquid(block) && LiquidUtility.isSourceLiquid(block)) {
                sources.add(block);
            }
        }
        return sources;
    }

    public boolean isAttachedToSource() {
        return getAttachedSources().isEmpty();
    }
}

package com.github.jamesnorris.flow.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.Stat;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.block.LiquidBlock;
import com.github.jamesnorris.flow.selection.Sphere;

public class FlowUtility {
    public static final BlockFace[] BLOCKFACES_NSEW = new BlockFace[] {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
    private static final ProbabilityMap<Material, Integer> GRAVEL_MAP = new ProbabilityMap<Material, Integer>();
    private static final ProbabilityMap<Material, Integer> SAND_MAP = new ProbabilityMap<Material, Integer>();
    private static final ProbabilityMap<Material, Integer> SANDSTONE_MAP = new ProbabilityMap<Material, Integer>();
    static {
        GRAVEL_MAP.put((Boolean) Setting.ENABLE_GRAVEL_TO_CLAY_CHANGE.get() ? Material.CLAY : null, (Integer) Setting.GRAVEL_TO_CLAY_CHANGE_PROBABILITY.get());
        SAND_MAP.put((Boolean) Setting.ENABLE_SAND_TO_GLASS_CHANGE.get() ? Material.GLASS : null, (Integer) Setting.SAND_TO_GLASS_CHANGE_PROBABILITY.get());
        SAND_MAP.put((Boolean) Setting.ENABLE_SAND_TO_OBSIDIAN_CHANGE.get() ? Material.OBSIDIAN : null, (Integer) Setting.SAND_TO_OBSIDIAN_CHANGE_PROBABILITY.get());
        SAND_MAP.put((Boolean) Setting.ENABLE_SAND_TO_STONE_CHANGE.get() ? Material.STONE : null, (Integer) Setting.SAND_TO_STONE_CHANGE_PROBABILITY.get());
        SAND_MAP.put((Boolean) Setting.ENABLE_SAND_TO_QUARTZ_CHANGE.get() ? Material.QUARTZ_BLOCK : null, (Integer) Setting.SAND_TO_QUARTZ_CHANGE_PROBABILITY.get());
        SANDSTONE_MAP.put((Boolean) Setting.ENABLE_SANDSTONE_TO_QUARTZ_CHANGE.get() ? Material.QUARTZ_BLOCK : null, (Integer) Setting.SANDSTONE_TO_QUARTZ_CHANGE_PROBABILITY.get());
    }

    public static boolean fixWaterStream(Block fix) {
        if (!(Boolean) Setting.ENABLE_AUTOMATIC_FIX.get()) {
            return false;
        }
        if (!LiquidUtility.isWater(fix)) {
            return false;
        }
        int nearbySourceCounter = 0;
        for (BlockFace face : BLOCKFACES_NSEW) {
            Block relative = fix.getRelative(face);
            if (LiquidUtility.isSourceLiquid(relative)) {
                nearbySourceCounter++;
            }
        }
        if (nearbySourceCounter < 2) {
            return false;
        }
        Stat.STREAM_FIX_COUNT.increment();
        fix.setData((byte) 0);
        return true;
    }
    
    public static void fixAreaWaterStreams(Block fix, int radius) {
        new Sphere(fix.getLocation(), radius) {
            @Override public boolean isInside(double x, double y, double z) {
                if ((Boolean) Setting.LIMIT_FIX_TO_BELOW_PLAYER.get()) {
                    if (y >= getCenter().getY()) {
                        y = Double.NaN;
                    }
                } else if ((Boolean) Setting.LIMIT_FIX_TO_BELOW_PLAYER_ONE_BLOCK.get()) {
                    if (Location.locToBlock(y) != getCenter().getBlockY() - 1) {
                        y = Double.NaN;
                    }
                }
                return super.isInside(x, y, z);
            }

            public Sphere recalculateAndFixLiquids() {
                super.recalculate();
                recursivelyFixLiquids();
                return this;
            }

            private void recursivelyFixLiquids() {// WARNING: Recursion!
                int notFixed = 0;
                for (Location location : getLocations()) {
                    boolean fixed = fixWaterStream(location.getBlock());
                    if (!fixed) {
                        notFixed++;
                    }
                }
                if (notFixed > 0) {
                    recursivelyFixLiquids();
                }
            }
        }.recalculateAndFixLiquids();
    }

    public static void proximityChange(Block from, Block relative) {
        Material changeType = getProximityChangeType(from, relative);
        if (changeType != relative.getType()) {
            relative.setType(changeType);
            Stat.PROXIMITY_CHANGE_COUNT.increment();
        }
    }

    public static Material getProximityChangeType(Block from, Block relative) {
        if (!(Boolean) Setting.ENABLE_PROXIMITY_BLOCK_CHANGE.get()) {
            return relative.getType();
        }
        if (LiquidUtility.isWater(from) && (Boolean) Setting.ENABLE_WATER.get()) {
            switch (relative.getType()) {
                case GRAVEL:
                    return (Material) GRAVEL_MAP.getProbableKey();
                default:
                    return relative.getType();
            }
        }
        if (LiquidUtility.isLava(from) && (Boolean) Setting.ENABLE_LAVA.get()) {
            switch (relative.getType()) {
                case SAND:
                    return (Material) SAND_MAP.getProbableKey();
                case SANDSTONE:
                    return (Material) SANDSTONE_MAP.getProbableKey();
                default:
                    return relative.getType();
            }
        }
        return relative.getType();
    }

    public static boolean extendStream(Block from, Block poweredRelative) {
        if (!isAllowedLiquid(from)) {
            return false;
        }
        from.setData((byte) 1);
        return true;
    }

    // returns a list of blocks that were pushed
    public static List<Block> pushLiquid(Block block, BlockFace direction, boolean keepBlock) {
        return pushLiquid(block, direction, keepBlock, -1);
    }

    public static List<Block> pushLiquid(Block block, BlockFace direction, boolean keepBlock, int distance) {
        List<Block> blocks = new ArrayList<Block>();
        if (!isAllowedLiquid(block) || distance == 0 || distance < -1) {
            return blocks;
        }
        Block relative = block.getRelative(direction);
        if (relative.isEmpty()) {
            relative.setType(block.getType());
            // relative.setData(block.getData(), true);
        } else if (LiquidUtility.isLiquid(relative)) {
            blocks.addAll(pushLiquid(relative, direction, keepBlock, distance--));
            relative.setType(block.getType());
        } else {
            int xDif = relative.getX() - block.getX();
            int yDif = relative.getY() - block.getY();
            int zDif = relative.getZ() - block.getZ();
            Block[] moves = new Block[] {block.getRelative(Math.abs(xDif - 1), Math.abs(yDif - 1), 0), block.getRelative(0, Math.abs(yDif - 1), Math.abs(zDif - 1)), block.getRelative(0, 0, Math.abs(zDif - 1)), block.getRelative(0, Math.abs(yDif - 1), 0), block.getRelative(Math.abs(xDif - 1), 0, 0), block.getRelative(Math.abs(xDif - 1), 0, Math.abs(zDif - 1))};
            List<Integer> indexes = new ArrayList<Integer>();
            double heightEach = 0;
            for (int index = 0; index < moves.length; index++) {
                Block move = moves[index];
                if (move.isEmpty()) {
                    indexes.add(index);
                    heightEach += LiquidUtility.isWater(block) ? 1.5 : 1;
                }
            }
            for (int index : indexes) {
                Block moveBlock = moves[index];
                moveBlock.setType(block.getType());
                ((LiquidBlock) DerivedBlock.fromBlock(moveBlock, true)).setHeight((byte) (int) Math.round(heightEach));
            }
        }
        if (!keepBlock) {
            block.setType(Material.AIR);
        }
        blocks.add(block);
        return blocks;
    }

    public static boolean isAllowedLiquid(Block block) {
        return (LiquidUtility.isWater(block) && (Boolean) Setting.ENABLE_WATER.get()) || (LiquidUtility.isLava(block) && (Boolean) Setting.ENABLE_LAVA.get());
    }

    public static boolean makeLiquidFinite(DerivedBlock derived/* , boolean allowEvaporation */) {
        LiquidBlock liquid = ((LiquidBlock) derived);
        if (LiquidUtility.isStationaryLiquid(derived.getBlock())) {
            liquid.setHeight(LiquidBlock.NEXT_HIGHEST);
            Stat.FINITE_LIQUID_COUNT.increment();
            return true;
        }
        // if (allowEvaporation && liquid.isAtLowestPossibleHeight()) {
        // liquid.getBlock().setType(Material.AIR);
        // }
        return false;
    }
}

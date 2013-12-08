package com.github.jamesnorris.flow.util;

import org.bukkit.block.Block;

public class LiquidUtility {
    public static boolean isLiquid(Block block) {
        return isWater(block) || isLava(block);
    }

    public static boolean isWater(Block block) {
        switch (block.getType()) {
            case WATER:
            case STATIONARY_WATER:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSourceWater(Block block) {
        return isWater(block) && block.getData() == 0x0;
    }

    public static boolean isLava(Block block) {
        switch (block.getType()) {
            case LAVA:
            case STATIONARY_LAVA:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSourceLava(Block block) {
        return isLava(block) && block.getData() == 0x0;
    }

    public static boolean isStationaryLiquid(Block block) {
        switch (block.getType()) {
            case STATIONARY_WATER:
            case STATIONARY_LAVA:
                return true;
            default:
                return false;
        }
    }

    public static boolean isMobileLiquid(Block block) {
        return isLiquid(block) && !isStationaryLiquid(block);
    }

    public static boolean isSourceLiquid(Block block) {
        return isSourceWater(block) || isSourceLava(block);
    }
}

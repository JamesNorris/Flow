package com.github.jamesnorris.flow.derived;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

public class DerivedWorld {
    private static Map<UUID, DerivedWorld> serverDerivedWorlds = new HashMap<UUID, DerivedWorld>();
    private List<DerivedBlock> worldDerivedBlocks = new ArrayList<DerivedBlock>();
    private World world;

    public static DerivedWorld getDerivedWorld(World world) {
        return serverDerivedWorlds.get(world.getUID());
    }

    public DerivedWorld(World world) {
        this.world = world;
        serverDerivedWorlds.put(world.getUID(), this);
    }

    public World getWorld() {
        return world;
    }

    public List<DerivedBlock> getDerivedBlocks() {
        return worldDerivedBlocks;
    }

    public DerivedBlock getDerivedBlockAt(Location loc) {
        for (DerivedBlock block : worldDerivedBlocks) {
            if (loc.equals(block.getBlock().getLocation())) {
                return block;
            }
        }
        return null;
    }

    public void addWorldDerivedBlock(DerivedBlock block) {
        worldDerivedBlocks.add(block);
    }

    public void removeDerivedBlock(DerivedBlock block) {
        worldDerivedBlocks.remove(block);
    }
}

package com.github.jamesnorris.flow.derived;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public class DerivedBlock {
    private static Map<DerivedWorld, List<DerivedBlock>> serverDerived = new HashMap<DerivedWorld, List<DerivedBlock>>();
    protected Block block;
    private boolean interactionAllowed = true;

    public static DerivedBlock fromLocation(Location loc, boolean force) {
        return fromBlock(loc.getBlock(), force);
    }

    public static DerivedBlock fromBlock(Block block, boolean force) {
        DerivedBlock derived = DerivedWorld.getDerivedWorld(block.getWorld()).getDerivedBlockAt(block.getLocation());
        if (derived == null && force) {
            derived = new DerivedBlock(block);
        }
        return derived;
    }

    public static List<DerivedBlock> fromDerivedWorld(DerivedWorld derivedWorld) {
        return serverDerived.get(derivedWorld);
    }

    public DerivedBlock(Location loc) {
        this(loc.getBlock());
    }

    public DerivedBlock(Block block) {
        this.block = block;
        DerivedWorld derivedWorld = DerivedWorld.getDerivedWorld(block.getWorld());
        derivedWorld.addWorldDerivedBlock(this);
        serverDerived.get(derivedWorld).add(this);
    }

    public void setFalling(boolean falling) {
        Location loc = block.getLocation();
        Material type = block.getType();
        byte data = block.getData();
        block.setType(Material.AIR);
        block = (Block) loc.getWorld().spawnFallingBlock(loc, type, data);
    }

    public Material getType() {
        return block.getType();
    }

    public Block getBlock() {
        return block;
    }

    public void remove() {
        block.setType(Material.AIR);
    }

    public void setInteractionAllowed(boolean allow) {
        interactionAllowed = allow;
    }

    public boolean isInteractionAllowed() {
        return interactionAllowed;
    }

    public Biome getBiome() {
        return block.getBiome();
    }
}

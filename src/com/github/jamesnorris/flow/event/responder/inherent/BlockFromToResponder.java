package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;

import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.DerivedWorld;
import com.github.jamesnorris.flow.derived.block.LiquidBlock;
import com.github.jamesnorris.flow.derived.block.SpongeBlock;
import com.github.jamesnorris.flow.event.responder.EventResponder;
import com.github.jamesnorris.flow.util.FlowUtility;
import com.github.jamesnorris.flow.util.LiquidUtility;

public class BlockFromToResponder extends EventResponder {
    public BlockFromToResponder() {
        super(BlockFromToEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockFromToEvent event = (BlockFromToEvent) evt;
        Block from = event.getBlock();
        Block to = event.getToBlock();
        for (DerivedBlock derived : DerivedBlock.fromDerivedWorld(DerivedWorld.getDerivedWorld(from.getWorld()))) {
            if (!(derived instanceof SpongeBlock)) {
                continue;
            }
            SpongeBlock sponge = (SpongeBlock) derived;
            if (sponge.effectContains(to)) {
                event.setCancelled(true);
            }
        }
        for (BlockFace face : FlowUtility.BLOCKFACES_NSEW) {
            Block relative = to.getRelative(face);
            if (relative.isBlockPowered() && (Boolean) Setting.ENABLE_REDSTONE_LIQUID_CONTROL.get() && (Boolean) Setting.ENABLE_REDSTONE_STREAM_EXTENSION.get()) {
                FlowUtility.extendStream(from, relative);
            }
            FlowUtility.proximityChange(from, relative);
            if (LiquidUtility.isLiquid(relative)) {
                FlowUtility.fixWaterStream(to);
            }
        }
        if (LiquidUtility.isLiquid(from) && (Boolean) Setting.ENABLE_FINITE_LIQUIDS.get()) {// TODO this may require a bunch of work and testing
            LiquidBlock dFrom = (LiquidBlock) DerivedBlock.fromBlock(from, true);
            LiquidBlock dTo = (LiquidBlock) DerivedBlock.fromBlock(to, true);
            FlowUtility.makeLiquidFinite(dFrom);
            if (dTo.canSpread()) {
                FlowUtility.makeLiquidFinite(dTo);// to must be a liquid, since from is
            } else if ((Boolean) Setting.ENABLE_FINITE_LIQUID_ACCUMULATION.get() && !dTo.isAttachedToSource()) {
                dTo.setSource();
            }
        }
    }
    /* Below is unused code originally used for BlockGroups, in the effort to evaporate entire patches of liquid when all were at lowest height, and moved an extra block
     * When I discovered that evaporating the blocks separately was more realistic, I gave up this effort. */
    // private boolean tryEvaporation(BlockGroup<DerivedBlock> group) {
    // if (isReadyForEvaporation(group)) {
    // for (DerivedBlock db : group) {
    // db.getBlock().setType(Material.AIR);
    // }
    // group.clear();
    // return true;
    // }
    // return false;
    // }
    //
    // private BlockGroup<DerivedBlock> mergeWithRelativesWithSameTag(Block key, BlockGroup<DerivedBlock> group) {
    // for (int i = 1; i <= 7; i++) {
    // for (BlockGroup<DerivedBlock> other : BlockGroup.getByChunk(getRelativeChunk(key.getChunk(), i))) {
    // if (other.getIDTag().equals("liquid") && isDirectlyTouching(group, other)) {
    // group = group.merge(other, -1, "liquid");
    // }
    // }
    // }
    // return group;
    // }
    //
    // private boolean isReadyForEvaporation(BlockGroup<DerivedBlock> group) {
    // for (DerivedBlock db : group) {
    // if (!(db instanceof LiquidBlock)) {
    // return false;
    // }
    // LiquidBlock liquid = (LiquidBlock) db;
    // if (liquid.getHeight() != LiquidBlock.LOWEST) {
    // return false;
    // }
    // }
    // return true;
    // }
    //
    // private boolean isDirectlyTouching(BlockGroup<DerivedBlock> group1, BlockGroup<DerivedBlock> group2) {
    // for (DerivedBlock db1 : group1) {
    // Block block1 = db1.getBlock();
    // for (DerivedBlock db2 : group2) {
    // Block block2 = db2.getBlock();
    // if (block1.getLocation().distance(block2.getLocation()) <= 1.5) {
    // return true;
    // }
    // }
    // }
    // return false;
    // }
    //
    // private Chunk getRelativeChunk(Chunk chunk, int direction) {//dir = 1-7
    // direction %= 7;
    // Location base = chunk.getBlock(7, 7, 0).getLocation().clone();
    // switch(direction) {
    // case 0: return base.add(16, 0, -16).getChunk();//northwest
    // case 1: return base.add(0, 0, -16).getChunk();//west
    // case 2: return base.add(-16, 0, -16).getChunk();//southwest
    // case 3: return base.add(-16, 0, 0).getChunk();//south
    // case 4: return base.add(-16, 0, 16).getChunk();//southeast
    // case 5: return base.add(0, 0, 16).getChunk();//east
    // case 6: return base.add(16, 0, 16).getChunk();//northeast
    // case 7: return base.add(16, 0, 0).getChunk();//north
    // default: return chunk;
    // }
    // }
}

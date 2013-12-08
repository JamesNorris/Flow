package com.github.jamesnorris.flow.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.github.jamesnorris.flow.util.DataList;
import com.github.jamesnorris.flow.derived.DerivedBlock;

@SuppressWarnings("serial") public class BlockGroup<B extends DerivedBlock> extends DataList<DerivedBlock> {
    protected static Map<World, List<BlockGroup<DerivedBlock>>> byWorld = new HashMap<World, List<BlockGroup<DerivedBlock>>>();
    protected static Map<Chunk, List<BlockGroup<DerivedBlock>>> byChunk = new HashMap<Chunk, List<BlockGroup<DerivedBlock>>>();
    protected static Map<Location, List<BlockGroup<DerivedBlock>>> byLocation = new HashMap<Location, List<BlockGroup<DerivedBlock>>>();

    public static List<BlockGroup<DerivedBlock>> getByWorld(World world) {
        return byWorld.get(world);
    }

    public static List<BlockGroup<DerivedBlock>> getByChunk(Chunk chunk) {
        return byChunk.get(chunk);
    }

    public static List<BlockGroup<DerivedBlock>> getByLocation(Location location) {
        return byLocation.get(simplifyLocation(location));
    }

    private String tag;

    public BlockGroup() {
        this(-1);
    }

    public BlockGroup(int maxSize) {
        this(null, maxSize);
    }

    public BlockGroup(List<B> blocks) {
        this(blocks, -1);
    }

    public BlockGroup(List<B> blocks, long maxSize) {
        this(blocks, maxSize, UUID.randomUUID().toString());
    }

    public BlockGroup(List<B> blocks, long maxSize, String tag) {
        super(maxSize);
        if (blocks != null) {
            addAll(blocks);
        }
        this.tag = tag;
    }

    public void setIDTag(String tag) {
        this.tag = tag;
    }

    public String getIDTag() {
        return tag;
    }

    public BlockGroup<DerivedBlock> merge(BlockGroup<B> other, String newId) {
        return merge(other, (getMaxSize() != -1 ? getMaxSize() : size()) + (other.getMaxSize() != -1 ? other.getMaxSize() : other.size()), newId);
    }

    public BlockGroup<DerivedBlock> merge(BlockGroup<B> other, long newMaxSize, String newId) {
        BlockGroup<DerivedBlock> newGroup = new BlockGroup<DerivedBlock>(this, newMaxSize, newId);
        newGroup.addAll(other);
        return newGroup;
    }

    @SuppressWarnings("unchecked") @Override public boolean add(DerivedBlock derived) {
        boolean added = super.add(derived);
        Block block = derived.getBlock();
        if (!byWorld.containsKey(block.getWorld())) {
            byWorld.put(block.getWorld(), new ArrayList<BlockGroup<DerivedBlock>>());
        }
        if (!byChunk.containsKey(block.getChunk())) {
            byChunk.put(block.getChunk(), new ArrayList<BlockGroup<DerivedBlock>>());
        }
        if (!byLocation.containsKey(simplifyLocation(block.getLocation()))) {
            byLocation.put(block.getLocation(), new ArrayList<BlockGroup<DerivedBlock>>());
        }
        byWorld.get(block.getWorld()).add((BlockGroup<DerivedBlock>) this);
        byChunk.get(block.getChunk()).add((BlockGroup<DerivedBlock>) this);
        byLocation.get(simplifyLocation(block.getLocation())).add((BlockGroup<DerivedBlock>) this);
        return added;
    }

    @SuppressWarnings("unchecked") @Override public boolean remove(Object obj) {
        boolean removed = super.remove(obj);
        if (!(obj instanceof DerivedBlock)) {
            return removed;
        }
        Block block = ((DerivedBlock) obj).getBlock();
        byWorld.get(block.getWorld()).remove((BlockGroup<DerivedBlock>) this);
        byChunk.get(block.getChunk()).remove((BlockGroup<DerivedBlock>) this);
        byLocation.get(simplifyLocation(block.getLocation())).remove((BlockGroup<DerivedBlock>) this);
        if (byWorld.get(block.getWorld()).isEmpty()) {
            byWorld.remove(block.getWorld());
        }
        if (byChunk.get(block.getChunk()).isEmpty()) {
            byChunk.remove(block.getChunk());
        }
        if (byLocation.get(simplifyLocation(block.getLocation())).isEmpty()) {
            byLocation.remove(simplifyLocation(block.getLocation()));
        }
        return removed;
    }

    protected static Location simplifyLocation(Location location) {
        Location loc = location.clone();
        loc.setX(Location.locToBlock(loc.getX()));
        loc.setY(Location.locToBlock(loc.getY()));
        loc.setZ(Location.locToBlock(loc.getZ()));
        loc.setPitch(0);
        loc.setYaw(0);
        return loc;
    }

    @SuppressWarnings("unchecked") public <T extends Object> List<T> runMethodOnGroup(Class<B> clazz, String methodName, Class<T> returnType, Object... args) {
        List<T> returned = new ArrayList<T>();
        for (DerivedBlock db : this) {
            try {
                returned.add((T) clazz.getDeclaredMethod(methodName, clazz).invoke((B) db, args));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return returned;
    }

    @SuppressWarnings("unchecked") public <T extends Object> List<T> runBlockMethodOnAllBlocks(String methodName, Class<T> returnType, Object... args) {
        List<T> returned = new ArrayList<T>();
        for (DerivedBlock db : this) {
            try {
                returned.add((T) Block.class.getDeclaredMethod(methodName, Block.class).invoke(db.getBlock(), args));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return returned;
    }

    @SuppressWarnings("unchecked") public <T extends Object> List<T> runDerivedBlockMethodOnAllDerivedBlocks(String methodName, Class<T> returnType, Object... args) {
        return runMethodOnGroup(((Class<B>) DerivedBlock.class), methodName, returnType, args);
    }
}

package com.github.JamesNorris.Flow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamPreventer implements Listener {
	private Flow plugin;
	public StreamPreventer(final Flow instance) { // connecting this class to the main class
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void BFTE(final BlockFromToEvent event) { // TODO include this way of ignoring waterfalls in the /flowfix command
		if (!event.isCancelled()) {
			if (plugin.getConfig().getBoolean("nearFix") == true) { // nearFix in the config
				final Block block = event.getToBlock();
				if (block.getTypeId() == 0) {
					final Block source = event.getBlock();
					final int sourcetype = source.getTypeId();
					for (final BlockFace f : new BlockFace[] {BlockFace.NORTH,
							BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
						final Block stream = block.getRelative(f);
						if (stream.getX() == source.getX()) {
							if (stream.getY() == source.getY()) {
								if (stream.getZ() == source.getZ()) {
									continue;
								}
							}
						}
						if (plugin.getConfig().getBoolean("enableWater") == true) { // enableWater in the config
							if (stream.getData() == 0) {
								final int streamtype = stream.getTypeId();
								if (streamtype == 8) {
									event.getToBlock().setTypeId(sourcetype);
									event.setCancelled(true);
									break;
								}
							}
						}
						if (plugin.getConfig().getBoolean("enableLava") == true) { // enableLava in the config
							if (stream.getData() == 0) {
								final int streamtype = stream.getTypeId();
								if (streamtype == 10) {
									event.getToBlock().setTypeId(sourcetype);
									event.setCancelled(true);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}

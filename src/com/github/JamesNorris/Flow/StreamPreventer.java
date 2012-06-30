package com.github.JamesNorris.Flow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamPreventer implements Listener {
	private static final HandlerList handlers = new HandlerList();
	private Flow plugin;
	public int counter = 0;

	public StreamPreventer(final Flow instance) { // connecting this class to
													// the main class
		plugin = instance;
		this.setPlugin(plugin);
	}

	public Flow getPlugin() {
		return plugin;
	}

	public void setPlugin(final Flow plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BFTE(final BlockFromToEvent event) { // TODO include this way of
														// avoiding waterfalls
														// into the /flowfix
														// command
		if (!event.isCancelled()) {
			if (plugin.getConfig().getBoolean("nearFix") == true) { // nearFix
																	// in the
																	// config
				Block block = event.getToBlock();
				if (block.getTypeId() == 0) {
					Block source = event.getBlock();
					int sourcetype = source.getTypeId();
					for (final BlockFace f : new BlockFace[]{BlockFace.NORTH,
							BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
						Block stream = block.getRelative(f);
						if (stream.getX() == source.getX()) {
							if (stream.getY() == source.getY()) {
								if (stream.getZ() == source.getZ()) {
									continue;
								}
							}
						}
						if (plugin.getConfig().getBoolean("enableWater") == true) { // enableWater
																					// in
																					// the
																					// config
							if (stream.getData() == 0) {
								int streamtype = stream.getTypeId();
								if (streamtype == 8) {
									event.getToBlock().setTypeId(sourcetype);
									event.setCancelled(true);
									break;
								}
							}
						}
						if (plugin.getConfig().getBoolean("enableLava") == true) { // enableLava
																					// in
																					// the
																					// config
							if (stream.getData() == 0) {
								int streamtype = stream.getTypeId();
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

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
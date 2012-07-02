package com.github.JamesNorris.Flow;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RainFill implements Runnable {
	private Flow plugin;
	public RainFill(final Flow instance) { // connecting this class to the main class
		plugin = instance;
		rainFillSeconds = plugin.getConfig().getInt("rainFillDelay");
		rainFillTicks = rainFillSeconds * 20;
	}
	int rainFillSeconds;
	int rainFillTicks; //use rainFillTicks in the code... rainFillSeconds is a number in the config.
	public Map<String, Integer>
	times = new HashMap<String, Integer>();
	private boolean isPlayerInRain(final Player player) {
		final World world = player.getWorld();
		if (world.hasStorm()) {
			final Location loc = player.getLocation();
			final Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());
			if (biome != Biome.DESERT && biome != Biome.DESERT_HILLS && world.getHighestBlockYAt(loc) <= loc.getBlockY()) {
				return true;
			}
		}
		return false;
	}
	public void run() {
		int rainFillWait = rainFillTicks;
		for (final World world : Bukkit.getWorlds()) {
			for (final Player player : world.getPlayers()) {
				if (isPlayerInRain(player)) {
					--rainFillWait;
					if (rainFillWait == 0){
						times.put(player.getName(), times.get(player.getName()) + 1);
						if (times.get(player.getName()) > 30) {
							final PlayerInventory inventory = player.getInventory();
							for (final Integer slot : inventory.all(Material.BUCKET).keySet()) {
								inventory.setItem(slot, new ItemStack(Material.WATER_BUCKET, 1));
								System.out.println("config="+plugin.getConfig());
							}
						}
					} else {
						times.put(player.getName(), 0);
					}
				}
			}
		}
	}
}

package com.github.JamesNorris.Flow;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FixCommand implements CommandExecutor {
	private static Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public boolean onCommand(final CommandSender flowplayer, final Command cmd,
			final String commandLabel, final String[] args) {
		int FixCounter = 0;
		if (!cmd.getName().equalsIgnoreCase("flowfix")
				|| !flowplayer.hasPermission("flow.fix")) {
			return false;
		}
		if (args.length > 1) {
			flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
			return false;
		}
		if (args.length < 1) {
			flowplayer.sendMessage(ChatColor.RED + "Not enough arguments!");
			return false;
		}
		final Player player = (Player) flowplayer;
		final World world = player.getWorld();
		final int radius = Math.abs(Integer.parseInt(args[0]));
		final int maxRadius = 30;
		if (radius > maxRadius) {
			flowplayer.sendMessage("Over the max fix limit. The limit is "
					+ maxRadius);
			return false;
		}
		flowplayer.sendMessage(ChatColor.AQUA
				+ "Flow is now fixing liquids within a radius of " + radius
				+ " blocks!");
		log.info("Player " + player + " has run /flowfix!");
		final Block block = new Location(player.getWorld(), player
				.getLocation().getX(), player.getLocation().getY() - 1, player
				.getLocation().getZ()).getBlock();
		final int x = block.getX();
		final int y = block.getY();
		final int z = block.getZ();
		final int minX = x - radius;
		final int minY = y - radius;
		final int minZ = z - radius;
		final int maxX = x + radius;
		final int maxY = y + radius;
		final int maxZ = z + radius;
		for (int counterX = minX; counterX <= maxX; counterX++) {
			for (int counterY = minY; counterY <= maxY; counterY++) {
				for (int counterZ = minZ; counterZ <= maxZ; counterZ++) {
					final Block stream = world.getBlockAt(counterX, counterY,
							counterZ);
					if (plugin.getConfig().getBoolean("enableWater") == true) {
						if (stream.getTypeId() == 9) {
							if (stream.getY() > y
									&& plugin.getConfig()
											.getBoolean("fixBelow") == true) {
								break;
							}
							stream.setType(Material.STATIONARY_WATER);
						}
					}
					if (plugin.getConfig().getBoolean("enableLava") == true) {
						if (stream.getTypeId() == 11) {
							if (stream.getY() > y
									&& plugin.getConfig()
											.getBoolean("fixBelow") == true) {
								break;
							}
							stream.setType(Material.STATIONARY_LAVA);
						}
					}
					if (stream.getTypeId() == 11 && plugin.getConfig().getBoolean("enableLava") == true){
						if (stream.getY() < y && plugin.getConfig().getBoolean("fixBelow") == true){
							++Flow.flowFixCount;
							++FixCounter;
						}else{
							++Flow.flowFixCount;
							++FixCounter;
						}
					}
					if (stream.getTypeId() == 9 && plugin.getConfig().getBoolean("enableLWater") == true){
						if (stream.getY() < y && plugin.getConfig().getBoolean("fixBelow") == true){
							++Flow.flowFixCount;
							++FixCounter;
						}else{
							++Flow.flowFixCount;
							++FixCounter;
						}
					}
				}
			}
		}
		flowplayer.sendMessage("Flow fixed " + FixCounter + " blocks!");
		return true;
	}
	
	private Flow plugin;
	
	public FixCommand(final Flow instance) {
		plugin = instance;
		setPlugin(plugin);
	}
	
	public Flow getPlugin() {
		return plugin;
	}
	
	public void setPlugin(final Flow plugin) {
		this.plugin = plugin;
	}
}

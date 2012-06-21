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

	public boolean onCommand(final CommandSender flowplayer, Command cmd, String commandLabel, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("flowfix") || !flowplayer.hasPermission("flow.fix")) {
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
		Player player = (Player) flowplayer;
		final World world = player.getWorld();
		int radius = Math.abs(Integer.parseInt(args[0]));
		flowplayer.sendMessage(ChatColor.AQUA + "Flow is now fixing liquids within a radius of " + radius + " blocks!");
		log.info("Player " + player + " has run /flowfix!");
		Block block = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()).getBlock();
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		int minX = x - radius;
		int minY = y - radius;
		int minZ = z - radius;
		int maxX = x + radius;
		int maxY = y + radius;
		int maxZ = z + radius;
		for (int counterX = minX; counterX <= maxX; counterX++) {
			for (int counterY = minY; counterY <= maxY;counterY++) {
				for (int counterZ = minZ; counterZ <= maxZ; counterZ++) {
					Block stream = world.getBlockAt(counterX,counterY,counterZ);
					if(plugin.getConfig().getBoolean("enableWater")) {
						if (stream.getTypeId() == 9) {
							if(stream.getY() != y && plugin.getConfig().getBoolean("fixBelow") == true){
								break;
							}
							stream.setType(Material.STATIONARY_WATER);
						}
					}
					if(plugin.getConfig().getBoolean("enableLava")) {
						if (stream.getTypeId() == 11) {
							if(stream.getY() != y && plugin.getConfig().getBoolean("fixBelow") == true){
								break;
							}
							stream.setType(Material.STATIONARY_LAVA);
						}
					}
				}
			}
		}
		return true;
	}
	private Flow plugin;
	public FixCommand(Flow instance) {
		plugin = instance;
		this.setPlugin(plugin);
	}
	public Flow getPlugin() {
		return plugin;
	}
	public void setPlugin(Flow plugin) {
		this.plugin = plugin;
	}
}

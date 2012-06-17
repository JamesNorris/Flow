package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class FixCommand implements CommandExecutor {

	@EventHandler(priority = EventPriority.HIGHEST)
	public boolean onCommand(CommandSender flowplayer, Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("flowfix") && flowplayer.hasPermission("flow.fix")){
			if (args.length > 1) {
				flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
				return false;
			}else{
				if (args.length < 1) {
					flowplayer.sendMessage(ChatColor.RED + "Not enough arguments!");
					return false;
				}else{
					flowplayer.sendMessage(ChatColor.AQUA + "Flow is now fixing your water!");
					Player p = (Player)flowplayer;
					World world = p.getWorld();
					Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getBlockY() - 1, p.getLocation().getBlockZ());
					Block block = location.getBlock();
					//int radius = 1; //TODO set this up in the config
					int radius = Integer.parseInt(args[0]);
					int x = block.getX();
					int y = block.getY();
					int z = block.getZ();
					int minX = x-radius;
					int minY = y-radius;
					int minZ = z-radius;
					int maxX = x+radius;
					int maxY = y+radius;
					int maxZ = z+radius;   
					for (int counterX = minX; counterX <= maxX; counterX ++)
					{
						for (int counterY =minY; counterY <= maxY;counterY ++)
						{
							for (int counterZ =minZ; counterZ <= maxZ; counterZ ++)
							{
								Block stream = world.getBlockAt(counterX,counterY,counterZ);
								if (stream.getTypeId() == 9) {
									stream.setType(Material.STATIONARY_WATER);
								}
							}
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	private Flow plugin;

	public FixCommand(Flow plugin) {
		this.setPlugin(plugin);
	}

	public Flow getPlugin() {
		return plugin;
	}

	public void setPlugin(Flow plugin) {
		this.plugin = plugin;
	}
}

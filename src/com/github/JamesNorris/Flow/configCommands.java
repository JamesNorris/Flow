package com.github.JamesNorris.Flow;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCommands implements CommandExecutor {
	private static Logger log = Logger.getLogger("Minecraft");
	private Flow plugin;
	
	public ConfigCommands(final Flow instance) {
		plugin = instance;
		setPlugin(plugin);
	}
	
	public Flow getPlugin() {
		return plugin;
	}
	
	public void setPlugin(final Flow plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender flowplayer, final Command cmd,
			final String commandLabel, final String[] args) {
		if (plugin.getConfig().getBoolean("configCommands") == false){
			flowplayer.sendMessage(ChatColor.RED + "configCommands are not enabled!");
			return true;
		}
		if (plugin.getConfig().getBoolean("configCommands") == true){
			if (!cmd.getName().equalsIgnoreCase("flowset")
					|| !flowplayer.hasPermission("flow.set")) {
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
			// enableLava
			if (args[0].equalsIgnoreCase("enableLava")) {
				if (plugin.getConfig().getBoolean("enableLava") == true) {
					plugin.getConfig().set("enableLava", false);
					log.info("Flow config.yml, 'enableLava' has been set to FALSE!");
					flowplayer.sendMessage("Flow config.yml, 'enableLava' has been set to FALSE!");
					plugin.saveConfig();
					return true;
				}
				if (plugin.getConfig().getBoolean("enableLava") == false) {
					plugin.getConfig().set("enableLava", true);
					log.info("Flow config.yml, 'enableLava' has been set to TRUE!");
					flowplayer.sendMessage("Flow config.yml, 'enableLava' has been set to TRUE!");
					plugin.saveConfig();
					return true;
				}
			}
			// enableWater
			if (args[0].equalsIgnoreCase("enableWater")) {
				if (plugin.getConfig().getBoolean("enableWater") == true) {
					plugin.getConfig().set("enableWater", false);
					log.info("Flow config.yml, 'enableWater' has been set to FALSE!");
					flowplayer.sendMessage("Flow config.yml, 'enableWater' has been set to FALSE!");
					plugin.saveConfig();
					return true;
				}
				if (plugin.getConfig().getBoolean("enableWater") == false) {
					plugin.getConfig().set("enableWater", true);
					log.info("Flow config.yml, 'enableWater' has been set to TRUE!");
					flowplayer.sendMessage("Flow config.yml, 'enableWater' has been set to TRUE!");
					plugin.saveConfig();
					return true;
				}
			}
			// fixBelow
			if (args[0].equalsIgnoreCase("fixBelow")) {
				if (plugin.getConfig().getBoolean("fixBelow") == true) {
					plugin.getConfig().set("fixBelow", false);
					log.info("Flow config.yml, 'fixBelow' has been set to FALSE!");
					flowplayer.sendMessage("Flow config.yml, 'fixBelow' has been set to FALSE!");
					plugin.saveConfig();
					return true;
				}
				if (plugin.getConfig().getBoolean("fixBelow") == false) {
					plugin.getConfig().set("fixBelow", true);
					log.info("Flow config.yml, 'fixBelow' has been set to TRUE!");
					flowplayer.sendMessage("Flow config.yml, 'fixBelow' has been set to TRUE!");
					plugin.saveConfig();
					return true;
				}
			}
			// nearFix
			if (args[0].equalsIgnoreCase("nearFix")) {
				if (plugin.getConfig().getBoolean("nearFix") == true) {
					plugin.getConfig().set("nearFix", false);
					log.info("Flow config.yml, 'nearFix' has been set to FALSE!");
					flowplayer.sendMessage("Flow config.yml, 'nearFix' has been set to FALSE!");
					plugin.saveConfig();
					return true;
				}
				if (plugin.getConfig().getBoolean("nearFix") == false) {
					plugin.getConfig().set("nearFix", true);
					log.info("Flow config.yml, 'nearFix' has been set to TRUE!");
					flowplayer.sendMessage("Flow config.yml, 'nearFix' has been set to TRUE!");
					flowplayer.sendMessage("The boolean 'nearFix' in config.yml has been set to TRUE. nearFix is currently in testing. If there are any problems, IMMEDIATLEY shut down, and tell me!");
					log.warning("The boolean 'nearFix' in config.yml has been set to TRUE. nearFix is currently in testing. If there are any problems, IMMEDIATLEY shut down, and tell me!");
					plugin.saveConfig();
					return true;
				}
			}
			// useBucketPerms
			if (args[0].equalsIgnoreCase("useBucketPerms")) {
				if (plugin.getConfig().getBoolean("useBucketPerms") == true) {
					plugin.getConfig().set("useBucketPerms", false);
					log.info("Flow config.yml, 'useBucketPerms' has been set to FALSE!");
					flowplayer.sendMessage("Flow config.yml, 'useBucketPerms' has been set to FALSE!");
					plugin.saveConfig();
					return true;
				}
				if (plugin.getConfig().getBoolean("useBucketPerms") == false) {
					plugin.getConfig().set("useBucketPerms", true);
					log.info("Flow config.yml, 'useBucketPerms' has been set to TRUE!");
					flowplayer.sendMessage("Flow config.yml, 'useBucketPerms' has been set to TRUE!");
					plugin.saveConfig();
					return true;
				}
			}
		}
		return false;
		
	}
}

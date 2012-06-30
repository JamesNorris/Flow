//MUST BE PUT INTO FLOWFIXEFFECT WHEN DONE
//THIS IS THE CLASS FOR THE FIX COMMAND

package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FlowCommand extends JavaPlugin implements CommandExecutor {
	private Flow plugin;

	public FlowCommand(final Flow plugin) {
		this.setPluginCommand(plugin);
	}

	@Override
	public boolean onCommand(final CommandSender flowplayer, final Command inf,
			final String flowLabel, final String[] args) {
		if (args.length > 1) {
			flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
			return false;
		} else {
			if (flowplayer.hasPermission("flow.info")) {
				final int page = Math.abs(Integer.parseInt(args[0]));
				if (page == 1) {
					if (inf.getName().equalsIgnoreCase("flow")) {
						flowplayer.sendMessage(ChatColor.AQUA
								+ "------------Flow Info [1]------------");
						flowplayer.sendMessage(ChatColor.AQUA + "Plugin:"
								+ ChatColor.BLUE + "Flow");
						flowplayer.sendMessage(ChatColor.AQUA
								+ "Created by: JamesNorris");
						flowplayer.sendMessage(ChatColor.AQUA
								+ "Use: To fix streams.");
						flowplayer.sendMessage(ChatColor.AQUA
								+ "-------Page 2 - /flow 2-------");
						return true;
					}
				}
				if (page == 2) {
					if (inf.getName().equalsIgnoreCase("flow")) {
						flowplayer.sendMessage(ChatColor.AQUA
								+ "------------Flow Info [2]------------");
						flowplayer.sendMessage(ChatColor.AQUA
								+ "/flow <#> = help pages");
						flowplayer
								.sendMessage(ChatColor.AQUA
										+ "/flowfix <radius> = Fix streams within radius");
						flowplayer
								.sendMessage(ChatColor.AQUA
										+ "/flowset <config value> = toggle a config value");
						flowplayer.sendMessage(ChatColor.AQUA
								+ "--------------------------------");
						return true;
					}
				}
				if (page > 2) {
					return false;
				}
			}
		}

		return false;
	}

	public Flow getPluginCommand() {
		return plugin;
	}

	public void setPluginCommand(final Flow plugin) {
		this.plugin = plugin;
	}
}

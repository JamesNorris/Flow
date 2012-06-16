package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FlowFixCommand implements CommandExecutor {
	
	//TODO below is the command, which should call out the onBlockFromTo event
	private Flow plugin;

	public FlowFixCommand(Flow plugin) {
		this.setPlugin(plugin);
	}

	public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
		if (args.length > 1) {
			flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
			return false;
		}else{
			if(flowplayer.hasPermission("flow.fix")) {
				if(inf.getName().equalsIgnoreCase("flowfix")){
					flowplayer.sendMessage(ChatColor.BLUE + "Flow is now fixing your water!");
					return true;
				}
			}else{
				return false;
			}
			return false;
		}
	}

	public Flow getPlugin() {
		return plugin;
	}

	public void setPlugin(Flow plugin) {
		this.plugin = plugin;
	}

}

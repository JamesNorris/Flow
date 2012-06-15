package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FlowCommandFix extends JavaPlugin implements CommandExecutor{
	private Flow plugin;
	public FlowCommandFix(Flow plugin){
		this.plugin = plugin;
	}
	
	@Override
		public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
			//if(flowplayer.hasPermission("flow.fix")) {
				if(inf.getName().equalsIgnoreCase("flowfix")){
					flowplayer.sendMessage(ChatColor.BLUE + "Flow is now fixing your water!");
					return true;
				}
			//}
			return false;
	}
	}

package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

	public class FlowCommandFix extends JavaPlugin{
		
		public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
			if(inf.getName().equalsIgnoreCase("flowfix")){
				if(flowplayer.hasPermission("flow.fix")) {
					flowplayer.sendMessage(ChatColor.BLUE + "Flow is now fixing your water!");
					return true;
				}
			}
			return false;
	}
	}

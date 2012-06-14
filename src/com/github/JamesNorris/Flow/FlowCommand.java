//MUST BE PUT INTO FLOWFIXEFFECT WHEN DONE
//THIS IS THE CLASS FOR THE FIX COMMAND

package com.github.JamesNorris.Flow;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FlowCommand extends JavaPlugin{
	
	public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
		if(inf.getName().equalsIgnoreCase("flow")){
			if(flowplayer.hasPermission("flow.info")) {
				flowplayer.sendMessage(ChatColor.BLUE + "Plugin: Flow - Created by: JamesNorris - Use - To fix streams.");
				return true;
			}
	}
			return false;
	}
}

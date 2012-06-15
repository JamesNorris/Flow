//MUST BE PUT INTO FLOWFIXEFFECT WHEN DONE
//THIS IS THE CLASS FOR THE FIX COMMAND

package com.github.JamesNorris.Flow;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FlowCommand extends JavaPlugin implements CommandExecutor{
	private Flow plugin;
	public FlowCommand(Flow plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
		//if(flowplayer.hasPermission("flow.info")) {
			if(inf.getName().equalsIgnoreCase("flow")){
				flowplayer.sendMessage(ChatColor.BLUE + "Plugin: Flow - Created by: JamesNorris - Use - To fix streams.");
				return true;
			}
	//}
			return false;
	}
}

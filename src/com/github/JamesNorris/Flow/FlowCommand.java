//MUST BE PUT INTO FLOWFIXEFFECT WHEN DONE
//THIS IS THE CLASS FOR THE FIX COMMAND

package com.github.JamesNorris.Flow;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FlowCommand extends JavaPlugin {
		public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
				if(args.length > 0 ){
					flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
					return false;
				}else{
			if(inf.getName().equalsIgnoreCase("flow")){
				if(flowplayer.hasPermission("flow.info")) {
					flowplayer.sendMessage(ChatColor.BLUE + "Flow is a plugin that helps keep water from forming into streams. Flow changes flowing water into water spawn blocks during downfall, but can also allows the use of - /flow fix - to initiate the fix.");
					return true;
				}else{
						if(inf.getName().equalsIgnoreCase("flowfix")){
							if(flowplayer.hasPermission("flow.fix")) {
								flowplayer.sendMessage(ChatColor.BLUE + "Flow is now fixing your water!");
									return true;
					}else{
					if(flowplayer.hasPermission("flow.info")){
						flowplayer.sendMessage(ChatColor.BLUE + "Correct syntax is /flow");
							return true;
					}else{
							if(flowplayer.hasPermission("flow.fix")){
								flowplayer.sendMessage(ChatColor.BLUE + "Correct syntax is /flowfix");
									return true;
							}
					}
					}
						}
				}
			}
				}
				return false;
		}
}


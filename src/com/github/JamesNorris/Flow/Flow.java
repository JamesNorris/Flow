package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class Flow extends JavaPlugin {
	 
	Logger log;
	private FlowCommand myExecutor;
	@Override
	public void onEnable(){
		
		myExecutor = new FlowCommand(this);
		getCommand("fix").setExecutor(myExecutor);
		
		log = this.getLogger();
		log.info("Flow has been enabled.");
	}
		
		public boolean onCommand(CommandSender info, Command inf, String commandLabel, String[] args){
			if(inf.getName().equalsIgnoreCase("flow info")){ 
				info.sendMessage(ChatColor.BLUE + "Flow is a lightweight plugin that helps keep water from forming into streams. Flow changes some flowing water into water spawn blocks during downfall.");
				return true;
			} 
			return false; 
		}
 
	public void onDisable(){
		log.info("Flow has been disabled.");
		
	}
}
//FLOW INFO COMMAND CLASS
//START CLASS

package com.github.JamesNorris.Flow;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Flow extends JavaPlugin implements CommandExecutor {
	Logger log;
	private StreamFinderCommand flowFixExecutor;
	private FlowCommand flowExecutor;
	@Override
	public void onEnable(){
		
		getConfig().options().copyDefaults(true);
		
		String version = "v1.0.7 TEST VERSION"; //TODO set this every time there's an update!
		this.getConfig().set("version", version); //setting config.yml version to: <version>
		
		this.getConfig().set("enable", true); //setting config.yml enable to <true/false>
		
		//this.getFlowConfig().set("enableLava", true); //TODO set lava to...
		
		this.saveConfig();
		
		flowFixExecutor = new StreamFinderCommand(this);
		getCommand("flowfix").setExecutor(flowFixExecutor);
		
		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
	}
	
	public void onDisable(){
		log.info("Flow has been disabled.");
	}
}

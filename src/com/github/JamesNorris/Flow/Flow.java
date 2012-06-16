//-----------------------------
//FLOW MAIN CLASS
//FOR: BUKKIT PLUGIN - "FLOW"
//CREATOR: JAMESNORRIS
//-----------------------------

package com.github.JamesNorris.Flow;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flow extends JavaPlugin implements CommandExecutor {

	private FlowFixCommand flowFixExecutor;
	private FlowCommand flowExecutor;
	private File config;
	private static Logger log = Logger.getLogger("Flow");
	@Override
	public void onEnable(){

		Logger.getLogger(JavaPlugin.class.getName()).log(Level.WARNING, "Flow is currently experimental. Please backup ALL files!");

		if(config == null){
			config = new File(getDataFolder(), "config.yml");
					String version = "v1.0.8 TEST VERSION"; //TODO set this every time there's an update! ex. vX.X.X
					this.getConfig().set("version", version); //setting config.yml version to: <version>

					getConfig().options().copyDefaults(true);

					this.getConfig().addDefault("enable", true); //TODO enable this plugin <true/false>

					//this.getFlowConfig().set("enableLava", true); //TODO set lava to...

					this.saveConfig();
				}else{
					Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "configUpdating is not set to enable or disable. Configuration cannot be loaded!");
				}


		flowFixExecutor = new FlowFixCommand(this);
		getCommand("flow").setExecutor(flowFixExecutor);
		
		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
	}

	public void onDisable(){
		log.info("Flow has been disabled.");
		this.saveConfig();
	}
}

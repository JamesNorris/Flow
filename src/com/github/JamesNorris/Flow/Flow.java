//-----------------------------
//FLOW MAIN CLASS
//FOR: BUKKIT PLUGIN - "FLOW"
//CREATOR: JAMESNORRIS
//-----------------------------

package com.github.JamesNorris.Flow;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class Flow extends JavaPlugin implements CommandExecutor {
	private FixCommand flowFixExecutor;
	private FlowCommand flowExecutor;
	private configCommands configExecutor;
	private File config;
	private static Logger log = Logger.getLogger("Minecraft");
	int configVersion = 0;
	int configVersionNumber = 3; //TODO add 1 every time there's an update to the config!
	String version = "v1.1.2"; //TODO set this every time there's an update! ex. vX.X.X
	@Override
	public void onEnable(){
		this.getConfig().set("configVersion", configVersionNumber);
		if(config == null || configVersion != configVersionNumber){
			config = new File(getDataFolder(), "config.yml");
			log.info("Loading config.yml...");
			this.getConfig().set("version", version);
			getConfig().options().copyDefaults(true);
			this.getConfig().addDefault("enabled", true);
			this.getConfig().addDefault("enableLava", true);
			this.getConfig().addDefault("enableWater", true);
			this.getConfig().addDefault("fixBelow", true);
		}	
		if(getConfig().getBoolean("enabled") == false){
			log.warning("Flow has been disabled in the config! Check the config.yml to change this!");
			this.setEnabled(false);
		}
		flowFixExecutor = new FixCommand(this);
		getCommand("flowfix").setExecutor(flowFixExecutor);
		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
		configExecutor = new configCommands(this);
		getCommand("flowset").setExecutor(configExecutor);
	}
	public void onDisable(){
		log.info("Flow has been disabled.");
		this.saveConfig();
	}
}

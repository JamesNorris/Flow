//-----------------------------
//FLOW MAIN CLASS
//FOR: BUKKIT PLUGIN - "FLOW"
//CREATOR: JAMESNORRIS
//-----------------------------

package com.github.JamesNorris.Flow;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// TODO use the same waterfall-avoiding technique that is used in StreamPreventer, in FixCommand
// TODO AFTER ALL OF THIS IS COMPLETED, START A NEW PLUGIN, AND MAINTAIN THIS ONE!

public class Flow extends JavaPlugin {
	public static int flowFixCount;
	File configFile;
	FileConfiguration config;
	private FixCommand flowFixExecutor;
	private FlowCommand flowExecutor;
	private ConfigCommands configExecutor;
	private static Logger log = Logger.getLogger("Minecraft");
	@Override
	public void onEnable() {
		flowFixCount = 0;
		File f = new File(this.getDataFolder(), "config.yml");
		if(!f.exists()){
			this.saveDefaultConfig();
		}
		if (getConfig().getBoolean("enabled") == false) {
			log.warning("Flow has been disabled in the config! Check the config.yml to change this!");
			setEnabled(false);
		}
		if (getConfig().getBoolean("nearFix") == true) {
			log.warning("The boolean nearFix in config.yml is set to true. nearFix is currently in testing. If there are any problems, IMMEDIATLEY shut down, and tell me!");
		}
		flowFixExecutor = new FixCommand(this);
		getCommand("flowfix").setExecutor(flowFixExecutor);
		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
		configExecutor = new ConfigCommands(this);
		getCommand("flowset").setExecutor(configExecutor);
		getServer().getPluginManager().registerEvents(new StreamPreventer(this), this);
		getServer().getPluginManager().registerEvents(new BucketControl(this), this);
		if (getConfig().getBoolean("rainFill") == true){
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new RainFill(this), 20, 20);
		}
	}
	@Override
	public void onDisable() {
		log.info("Flow has fixed " + flowFixCount + " blocks this session!");
	}
}

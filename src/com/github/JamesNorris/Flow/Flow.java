//-----------------------------
//FLOW MAIN CLASS
//FOR: BUKKIT PLUGIN - "FLOW"
//CREATOR: JAMESNORRIS
//-----------------------------

package com.github.JamesNorris.Flow;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

// TODO create a class to add permissions for using water/lava/milk buckets
// TODO use the same waterfall-avoiding technique that is used in StreamPreventer, in FixCommand
// TODO Tell how many blocks have been changed after /flowfix, and overall (at disable).
// TODO AFTER ALL OF THIS IS COMPLETED, START A NEW PLUGIN, AND MAINTAIN THIS ONE!

public class Flow extends JavaPlugin implements CommandExecutor, Listener {
	private FixCommand flowFixExecutor;
	private FlowCommand flowExecutor;
	private ConfigCommands configExecutor;
	private File config;
	private static Logger log = Logger.getLogger("Minecraft");
	int configVersion = 0;
	int configVersionNumber = 1; // TODO change every time there's a new value
									// in the config!
	String version = "v1.1.5"; // TODO set this every time there's an update!
								// ex. vX.X.X

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(
				new StreamPreventer(this), this);
		this.getConfig().set("configVersion", configVersionNumber);
		if (config == null || configVersion != configVersionNumber) {
			config = new File(getDataFolder(), "config.yml");
			log.info("Loading config.yml...");
			this.getConfig().set("version", version);
			getConfig().options().copyDefaults(true);
			this.getConfig().addDefault("enabled", true);
			this.getConfig().addDefault("enableLava", true);
			this.getConfig().addDefault("enableWater", true);
			this.getConfig().addDefault("fixBelow", true);
			this.getConfig().addDefault("nearFix", false);
		}
		if (getConfig().getBoolean("enabled") == false) {
			log.warning("Flow has been disabled in the config! Check the config.yml to change this!");
			this.setEnabled(false);
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
	}

	@Override
	public void onDisable() {
		log.info("Flow has been disabled.");
		this.saveConfig();
	}
}

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
	private File config;
	private static Logger log = Logger.getLogger("Minecraft");

	@Override
	public void onEnable(){

		if(config == null){
			config = new File(getDataFolder(), "config.yml");

			log.info("Loading config.yml...");


			String version = "v1.1.0 Development"; //TODO set this every time there's an update! ex. vX.X.X
			this.getConfig().set("version", version); //setting config.yml version to: <version>

			getConfig().options().copyDefaults(true);

			this.getConfig().addDefault("enabled", true);

			String enableLava = "true";
			this.getConfig().addDefault("enableLava", enableLava); //TODO enable lava to be fixed (see FixCommand.java)

			this.saveConfig();
		}

		if(getConfig().getBoolean("enabled") == false){
			log.warning("Flow has been disabled in the config! Check the config.yml to change this!");
		}

		log.warning("Flow is currently a development version. Make sure to backup all files!");


		flowFixExecutor = new FixCommand(this);
		getCommand("flowfix").setExecutor(flowFixExecutor);

		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
	}





	public void onDisable(){
		log.info("Flow has been disabled.");
		this.saveConfig();
	}
}

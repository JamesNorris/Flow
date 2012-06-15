//FLOW INFO COMMAND CLASS
//START CLASS

package com.github.JamesNorris.Flow;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flow extends JavaPlugin implements CommandExecutor {
	private FileConfiguration config = null;//Trying to create a config.yml... no success yet.
	private File flowConfigFile = null;
	Logger log;
	
	private FlowCommandFix flowFixExecutor;
	private FlowCommand flowExecutor;
	@Override
	public void onEnable(){
		
		flowFixExecutor = new FlowCommandFix(this);
		getCommand("flowfix").setExecutor(flowFixExecutor);
		flowExecutor = new FlowCommand(this);
		getCommand("flow").setExecutor(flowExecutor);
		
		    if (flowConfigFile == null) {
		    flowConfigFile = new File(getDataFolder(), "config.yml");
		    }
		    config = YamlConfiguration.loadConfiguration(flowConfigFile);
		 
		    InputStream defConfigStream = getResource("config.yml");
		    if (defConfigStream != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        config.setDefaults(defConfig);
		    }
		}

		public FileConfiguration getconfig() {
		    if (config == null) {
		        onEnable();
		    }
		    return config;
		}

		public void saveconfig() {
		    if (config == null || flowConfigFile == null) {
		    return;
		    }
		    try {
		        config.save(flowConfigFile);
		    } catch (IOException ex) {
		        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + flowConfigFile, ex);
		    }
		}
	public void onDisable(){
		log.info("Flow has been disabled.");
	}
}

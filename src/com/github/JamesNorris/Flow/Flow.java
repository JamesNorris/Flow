//FLOW INFO COMMAND CLASS
//START CLASS

package com.github.JamesNorris.Flow;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class Flow extends JavaPlugin {
	 
	Logger log;
	@Override
	public void onEnable(){
		
		log = this.getLogger();
		log.info("Flow has been enabled.");
	}
	public void onDisable(){
		log.info("Flow has been disabled.");
		
	}
}
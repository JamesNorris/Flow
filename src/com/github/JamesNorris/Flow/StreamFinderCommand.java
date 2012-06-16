package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamFinderCommand implements CommandExecutor { 
	public final class BlockId{
		public static final int WATER = 8;
		public static final int STATIONARY_WATER = 9;
		//public static final int LAVA = 10; //TODO use when lava works
		//public static final int STATIONARY_LAVA = 11; //TODO use when lava works
	}

	public void onBlockFromTo(BlockFromToEvent finder, Location getworld){//TODO get this working
		if(!finder.isCancelled()){
			Block stream = finder.getBlock();
			Block air = finder.getToBlock();
			if(air.getTypeId() == 0){
				int streamID = stream.getTypeId();
				if(streamID == 8){ //if it is flowing water.... then...
					stream.setTypeId(9);    // set the block to Type 9
				}
			}
		}
	}
	
	 //TODO below is the command, which should be added soon
	private Flow plugin;
 
	public StreamFinderCommand(Flow plugin) {
		this.setPlugin(plugin);
	}
 
	@Override
	public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
		if (args.length > 1) {
	        flowplayer.sendMessage(ChatColor.RED + "Too many arguments!");
	        return false;
		}else{
		if(flowplayer.hasPermission("flow.fix")) {
			if(inf.getName().equalsIgnoreCase("flowfix")){
				flowplayer.sendMessage(ChatColor.BLUE + "Flow is now fixing your water!");
				return true;
			}
		}else{
		return false;
		}
		return false;
	}
		}

	public Flow getPlugin() {
		return plugin;
	}

	public void setPlugin(Flow plugin) {
		this.plugin = plugin;
	}

	}
package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamListener { 
	
	public void onBlockFromTo(BlockFromToEvent finder, Location getworld){
		
		if(!finder.isCancelled()){
			Block stream = finder.getBlock();
			Block air = finder.getToBlock();
			if(air.getTypeId() == 0){
				int streamID = stream.getTypeId();
				if(stream.getData() == 0 && streamID == 8){
					stream.setTypeId(9);    // set the block to Type 9
				}
			}
		}
	}
	

public boolean onCommand(CommandSender flowplayer, Command inf, String flowLabel, String[] args){
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



package com.github.JamesNorris.Flow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamFinder implements Listener  { 	
	World world;
	int x;
	int y;
	int z;
	int radius;
	int minX = x-radius;
	int minY = y-radius;
	int minZ = z-radius;
	int maxX = x+radius;
	int maxY = y+radius;
	int maxZ = z+radius;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent finder, Location loc){//TODO get this working
		if(!finder.isCancelled()){
			Block streamBlock = finder.getBlock();
			System.out.println(streamBlock.getType());
			if(streamBlock.getType() == Material.WATER){
				for(int counterX = minX; counterX < maxX; counterX ++) {
					for (int counterY = minY; counterY < maxY;counterY ++) {
						for (int counterZ = minZ; counterZ < maxZ; counterZ ++){
							world.getBlockAt(counterX,counterY,counterZ).setType(Material.STATIONARY_WATER);
						}

					}

				}
			}
		}
	}
}



package com.github.JamesNorris.Flow;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockFromToEvent;

public class StreamFinderCommand { 
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
}


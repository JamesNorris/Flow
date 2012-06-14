//THIS IS THE CLASS FOR THE FIX EFFECT

package com.github.JamesNorris.Flow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

		public class FlowFixEffect extends BlockEvent implements Cancellable{//BlockFromToEvent
			private static final HandlerList fixhandlers = new HandlerList();
			protected Block fixblock;
			protected BlockFace face;
			protected boolean cancelfix;
			public FlowFixEffect(final Block source, final BlockFace face){
				super(source);
				this.face = face;
				this.cancelfix = false;
			}
			public FlowFixEffect(final Block source, final Block toBlock){
				super(source);
				this.fixblock = toBlock;
				this.face = BlockFace.SELF;
				this.cancelfix = false;
			}
			public BlockFace getFace(){
				return face;
			}
			public Block getToBlock(){
				if (fixblock == null){
					fixblock = block.getRelative(face);
				}
				return fixblock;
			}
			public boolean isCancelled() {
				return cancelfix;
			}
			public void setCancelled(boolean canceleffect) {
				this.cancelfix = canceleffect;	
			}
			@Override
			public HandlerList getHandlers() {
				return fixhandlers;
			}
			public static HandlerList getHandlerList(){
				return fixhandlers;
			}
		
		}
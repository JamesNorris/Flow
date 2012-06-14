package com.github.JamesNorris.Flow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

		public class FlowFixEffect extends BlockEvent implements Cancellable{//BlockFromToEvent
			private static final HandlerList handlers = new HandlerList();
			protected Block to;
			protected BlockFace face;
			protected boolean cancel;
			public FlowFixEffect(final Block block, final BlockFace face){
				super(block);
				this.face = face;
				this.cancel = false;
			}
			public FlowFixEffect(final Block block, final Block toBlock){
				super(block);
				this.to = toBlock;
				this.face = BlockFace.SELF;
				this.cancel = false;
			}
			public BlockFace getFace(){
				return face;
			}
			public Block getToBlock(){
				if (to == null){
					to = block.getRelative(face);
				}
				return to;
			}
			public boolean isCancelled() {
				return cancel;
			}
			public void setCancelled(boolean cancel) {
				this.cancel = cancel;	
			}
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			public static HandlerList getHandlerList(){
				return handlers;
			}
		
		}
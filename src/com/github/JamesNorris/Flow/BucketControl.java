package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BucketControl implements Listener {
	private Flow plugin;
	public BucketControl(final Flow instance) { // connecting this class to the main class
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PBEE(PlayerBucketEmptyEvent event){
		if (!event.isCancelled()){
			if(plugin.getConfig().getBoolean("useBucketPerms") == true){// useBucketPerms in config.yml
				if (!event.getPlayer().hasPermission("flow.bucket.*")){
					event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use any buckets!");
					event.setCancelled(true);
				}else{
					if (!event.getPlayer().hasPermission("flow.bucket")){
						if(event.getBucket().getId() == 325){
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use regular buckets!");
							event.setCancelled(true);
						}
					}
					if (!event.getPlayer().hasPermission("flow.waterbucket")){
						if(event.getBucket().getId() == 326){
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use water buckets!");
							event.setCancelled(true);
						}
					}
					if (!event.getPlayer().hasPermission("flow.lavabucket")){
						if(event.getBucket().getId() == 327){
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use lava buckets!");
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PBFE(PlayerBucketFillEvent event){
		if (!event.isCancelled()){
			final Block clicked = event.getBlockClicked();
			if(plugin.getConfig().getBoolean("useBucketPerms") == true){// useBucketPerms in config.yml
				if (!event.getPlayer().hasPermission("flow.bucket.*")){
					if (event.getBucket().getId() == 326){
						clicked.setType(Material.STATIONARY_WATER);
					}
					if (event.getBucket().getId() == 327){
						clicked.setType(Material.STATIONARY_LAVA);
					}
					event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use any buckets!");
					event.setCancelled(true);
				}else{
					if (!event.getPlayer().hasPermission("flow.waterbucket")){
						if(event.getBucket().getId() == 326){
							clicked.setType(Material.STATIONARY_WATER);
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use water buckets!");
							event.setCancelled(true);
						}
					}
					if (!event.getPlayer().hasPermission("flow.lavabucket")){
						if(event.getBucket().getId() == 327){
							clicked.setType(Material.STATIONARY_LAVA);
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to use lava buckets!");
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
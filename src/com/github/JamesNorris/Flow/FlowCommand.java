package com.github.JamesNorris.Flow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FlowCommand implements CommandExecutor {
	 
	private Flow plugin;
 
	public FlowCommand(Flow plugin) {
		this.setfix(plugin);
	}
 
		@Override
		public boolean onCommand(CommandSender fixer, Command fix, String commandLabel, String[] args){
			if(fix.getName().equalsIgnoreCase("flow fix")){ 
				fixer.sendMessage(ChatColor.BLUE + "Fixing server flow...");
				return true;
			} 
			return false; 
		}

		public Flow getfix() {
			return plugin;
		}

		public void setfix(Flow plugin) {
			this.plugin = plugin;
		}
	}
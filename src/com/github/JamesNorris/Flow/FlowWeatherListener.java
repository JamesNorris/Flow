package com.github.JamesNorris.Flow;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.weather.WeatherEvent;

public class FlowWeatherListener extends WeatherEvent implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	private final boolean to;
	private boolean canceled;
	
	public FlowWeatherListener(final World world, final boolean to){
		super(world);
		this.to = to;
	}
	public boolean isCancelled(){
		return canceled;
	}
	public void setCancelled(boolean cancel){
		canceled = cancel;
	}
	public boolean toWeatherState(){
		return to;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList(){
		return handlers;
	}
}

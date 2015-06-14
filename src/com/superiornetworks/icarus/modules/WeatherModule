package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WeatherModule extends IcarusModule implements Listener
{
    public WeatherModule(IcarusMod plugin)
    {
        super(plugin);
    }
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }
}

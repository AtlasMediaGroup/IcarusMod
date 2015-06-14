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
    
    public static void setInitialWeather() {
        for (World world : Bukkit.getServer().getWorlds()) {
            // Set time to day
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(0L);
            // Set weather to clear
            world.setStorm(false);
            world.setThundering(false);
        }
    }
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }
}

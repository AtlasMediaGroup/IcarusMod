/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherModule extends IcarusModule implements Listener
{
    public WeatherModule(IcarusMod plugin) 
    {
        super(plugin);
    }
    
    public static void setCorrectWeather()
    {
        for (World world : Bukkit.getWorlds())
        {
            world.setGameRuleValue("doDaylightCycle", "false"); //Prevent time from changing naturally.
            world.setTime(6000L); //Set the time to day.
            world.setStorm(false);
        }
    }
    
    //Detect when it starts to rain.
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        setCorrectWeather();
    }
}

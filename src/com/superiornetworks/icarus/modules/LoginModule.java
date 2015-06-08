package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginModule extends IcarusModule implements Listener
{
    public LoginModule(IcarusMod plugin)
    {
        super(plugin);
    }
    
    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event)
    {
        //can be used if needed
    }
}

package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModule extends IcarusModule implements Listener
{
    public ChatModule(IcarusMod plugin)
    {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage();
        event.setMessage(ICM_Utils.colour(message));
    }
}

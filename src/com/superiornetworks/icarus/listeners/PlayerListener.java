package com.superiornetworks.icarus.listeners;

import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerListener implements Listener
{
    
    private final IcarusMod plugin;
    
     public PlayerListener(IcarusMod plugin)
    {
        this.plugin = plugin;
    }
    
     @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        // Loading in the Famous Warning System Module
        plugin.famousWarning.onUncancelledPlayerJoin(event);
        
        //Turn on commandspy
        if (TFM_AdminList.isSuperAdmin(player))
        {
            TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
            playerdata.setCommandSpy(true);
        }
    }
    
}

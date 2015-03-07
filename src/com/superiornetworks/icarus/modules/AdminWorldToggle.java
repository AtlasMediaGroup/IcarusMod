package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AdminWorldToggle extends IcarusModule implements Listener
{

    public AdminWorldToggle(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (!ICM_Utils.MANAGERS.contains(player.getName()) && event.getTo().getWorld() == Bukkit.getWorld("adminworld") && !IcarusMod.config.getBoolean("toggles.AdminWorld"))
        {
            TFM_Util.playerMsg(player, "AdminWorld is currently disabled.", ChatColor.RED);
            event.setCancelled(true);
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }
    }

}

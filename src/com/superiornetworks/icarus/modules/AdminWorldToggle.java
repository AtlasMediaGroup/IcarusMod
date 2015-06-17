package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Utils;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Bukkit;
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
            playerMsg(player, "&cAdminWorld is currently disabled.");
            event.setCancelled(true);
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }
        }

    }

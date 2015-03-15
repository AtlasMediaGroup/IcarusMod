package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreativePVP extends IcarusModule implements Listener
{

    public CreativePVP(IcarusMod plugin)
    {
        super(plugin);
    }

    public void onEntityHit(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        if (!(event.getDamager() instanceof Player))
        {
            return;
        }

        Player player = (Player) event.getDamager();
        if (player.getGameMode() == GameMode.CREATIVE || ICM_Utils.GOD.contains(player.getName()))
        {
            event.setCancelled(true);
            ICM_Utils.playerMsg(player, "&cDon't try to PVP in Creative or God mode. ~ Thanks.");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        if (ICM_Utils.GOD.contains(player.getName()))
        {
            event.setCancelled(true);
        }
    }

}

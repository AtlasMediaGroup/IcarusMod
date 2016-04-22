package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Settings;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
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
        try
        {
            if (event.getTo().getWorld() == Bukkit.getWorld("adminworld") && !ICM_Settings.getBoolean("adminworld-toggled") && !ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.MANAGER))
            {
                playerMsg(player, "&cAdminWorld is currently disabled.");
                event.setCancelled(true);
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }

}

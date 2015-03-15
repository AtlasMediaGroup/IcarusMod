package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CommandSpyOverride extends IcarusModule implements Listener
{

    public CommandSpyOverride(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.SUPER))
        {
            try
            {
                ICM_SqlHandler.updateDatabase("UPDATE `icarus`.`players` SET `commandSpy` `1`");
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CommandSpyOverride.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

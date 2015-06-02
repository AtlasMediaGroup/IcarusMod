package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinModule extends IcarusModule implements Listener
{

    public JoinModule(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        try
        {
            if (!ICM_SqlHandler.playerExists(player.getName()))
            {
                ICM_SqlHandler.generateNewPlayer(player);
                Bukkit.broadcastMessage(ICM_Utils.getRandomChatColour() + player.getName() + " is a new player!");
            }
            else if (ICM_SqlHandler.getLoginMessage(player.getName()) != null && !"".equals(ICM_SqlHandler.getLoginMessage(player.getName())))
            {
                event.setJoinMessage(ICM_Utils.colour(ICM_SqlHandler.getLoginMessage(player.getName())));
            }
            else
            {
                event.setJoinMessage(ChatColor.AQUA + player.getName() + " is " + ICM_Utils.aOrAn(ICM_SqlHandler.getRank(player.getName())) + " " + ICM_SqlHandler.getRank(player.getName()));
            }
            if(ICM_Rank.getRank(event.getPlayer()).level == -1)
            {
                Bukkit.broadcastMessage(ChatColor.RED + "WARNING: " + event.getPlayer().getName() + " is an imposter. Admins, please deal with this in an appropriate manner.");
            }
            
            ICM_Utils.sendTitle(player, "&6&lWelcome back!", 1, 2, 1);
            ICM_Utils.sendSubtitle(player, "&f&lWe hope you enjoy your stay.", 2, 2, 2);
        }
        catch (SQLException ex)
        {
            plugin.getLogger().severe(ex.getLocalizedMessage());
        }
    }
}

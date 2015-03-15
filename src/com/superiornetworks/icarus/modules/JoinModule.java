package com.superiornetworks.icarus.modules;

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
                ICM_SqlHandler.generateNewPlayer(player.getName());
                Bukkit.broadcastMessage(ICM_Utils.getRandomChatColour() + player.getName() + " is a new player!");
            }
            else if (ICM_SqlHandler.getLoginMessage(player.getName()) != null)
            {
                event.setJoinMessage(ICM_Utils.colour(ICM_SqlHandler.getLoginMessage(player.getName())));
            }
            else
            {
                event.setJoinMessage(ChatColor.AQUA + player.getName() + "is " + ICM_Utils.aOrAn(ICM_SqlHandler.getRank(player.getName())) + ICM_SqlHandler.getRank(player.getName()));
            }
        }
        catch (SQLException ex)
        {
            plugin.getLogger().severe(ex.getLocalizedMessage());
        }
    }
}

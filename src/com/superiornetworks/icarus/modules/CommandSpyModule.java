package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_PanelLogger;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSpyModule extends IcarusModule implements Listener
{

    public CommandSpyModule(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        if(event.isCancelled())
        {
            return;
        }
        ICM_PanelLogger.log(ICM_PanelLogger.MessageType.CMD, event.getPlayer().getName(), event.getMessage());
        ChatColor colour = ChatColor.GRAY;
        List<String> alertCommands = Arrays.asList("//set", "//replace", "//remove", "/ci", "/clear");
        for(String alert : alertCommands)
        {
            if(event.getMessage().toLowerCase().startsWith(alert))
            {
                colour = ChatColor.RED;
            }
        }
        if(event.getMessage().toLowerCase().startsWith("/op"))
        {
            if(event.getMessage().split(" ").length > 1)
            {
                Player player = Bukkit.getPlayer(event.getMessage().split(" ")[1]);
                if(player != null && ICM_Rank.getRank(player) == ICM_Rank.Rank.NONOP)
                {
                    try
                    {
                        Connection c = ICM_SqlHandler.getConnection();
                        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `rank` = 'Op' WHERE `playerName` = ?");
                        statement.setString(1, player.getName());
                        statement.executeUpdate();
                    }
                    catch(SQLException ex)
                    {
                        IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
                    }
                }
            }
        }
        else if(event.getMessage().toLowerCase().startsWith("/deop"))
        {
            if(event.getMessage().split(" ").length > 1)
            {
                Player player = Bukkit.getPlayer(event.getMessage().split(" ")[1]);
                if(player != null && ICM_Rank.getRank(player) == ICM_Rank.Rank.OP)
                {
                    try
                    {
                        Connection c = ICM_SqlHandler.getConnection();
                        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `rank` = 'Non-Op' WHERE `playerName` = ?");
                        statement.setString(1, player.getName());
                        statement.executeUpdate();
                    }
                    catch(SQLException ex)
                    {
                        IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
                    }
                }
            }
        }
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if(ICM_Rank.getRank(event.getPlayer()).level < ICM_Rank.getRank(player).level)
            {
                player.sendMessage(colour + event.getPlayer().getName() + " - Command: " + event.getMessage().toLowerCase());
            }
        }
    }
}

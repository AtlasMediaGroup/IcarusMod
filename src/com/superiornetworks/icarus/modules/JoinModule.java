package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_PanelLogger;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Settings;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.ICM_Whitelist;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import space.paulcodes.otherapis.TitlesAPI;

public class JoinModule extends IcarusModule implements Listener
{

    public JoinModule(IcarusMod plugin)
    {
        super(plugin);
    }

    static List<String> noQuitMessage = new ArrayList<>();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        ICM_PanelLogger.log(ICM_PanelLogger.MessageType.CONNECT, event.getPlayer().getName(), "Attempted to login");
        try
        {
            if(!ICM_SqlHandler.playerExists(player.getName()))
            {
                //ICM_SqlHandler.generateNewPlayer(player, event.getRealAddress().getHostAddress());
                ICM_SqlHandler.generateNewPlayer(player, event.getAddress().getHostAddress());
            }
            
            if(ICM_Bans.isBanned(event.getPlayer()) && !ICM_Rank.isRankOrHigher(event.getPlayer(), ICM_Rank.Rank.SUPER))
            {
                event.disallow(Result.KICK_BANNED, "§c§lYou are banned!\nYou were banned for: §e" + ICM_Bans.getReason(player) + "\n§c§lBanned by: §e" + ICM_Bans.getBanner(player));
            }

            if(ICM_Whitelist.whitelist)
            {
                if(!ICM_SqlHandler.playerExists(player.getName()))
                {
                    event.disallow(Result.KICK_WHITELIST, "§f§lThe server is currently whitelisted. Please check back later.");
                    return;
                }
                else if(!ICM_Whitelist.isWhitelisted(event.getPlayer().getName()) && !ICM_Rank.isRankOrHigher(event.getPlayer(), ICM_Rank.Rank.SUPER))
                {
                    event.disallow(Result.KICK_WHITELIST, "§f§lThe server is currently whitelisted. Please check back later.");
                }
            }

            if(ICM_Rank.getRank(event.getPlayer()).level == -1)
            {
                Bukkit.broadcastMessage(ChatColor.RED + "WARNING: " + event.getPlayer().getName() + " is an imposter. Admins, please deal with this in an appropriate manner.");
            }

        }

        catch(SQLException ex)
        {
            plugin.getLogger().severe(ex.getMessage());
            plugin.getLogger().severe(ex.getSQLState());
        }
    }

    @EventHandler
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        try
        {
            Player player = event.getPlayer();

            if(ICM_SqlHandler.getLoginMessage(player.getName()) != null && !"".equals(ICM_SqlHandler.getLoginMessage(player.getName())))
            {
                event.setJoinMessage(ICM_Utils.colour(ICM_SqlHandler.getLoginMessage(player.getName())));
            }
            else
            {
                event.setJoinMessage(ChatColor.AQUA + player.getName() + " is " + ICM_Utils.aOrAn(ICM_SqlHandler.getRank(player.getName())) + " " + ICM_SqlHandler.getRank(player.getName()));
            }
            String title = ICM_Settings.getString("title-message-on-join");
            String subtitle = ICM_Settings.getString("subtitle-message-on-join");
            TitlesAPI.sendTitle(player, title, 20, 20, 20);
            TitlesAPI.sendSubtitle(player, subtitle, 20, 20, 20);
                        
            //TabAPI stuff, not currently in use.
            //API.setRankColor(player);
            //TabAPI.sendTabTitle(player, "&5&lWelcome to CJFreedom", "You are currently playing on " + IcarusMod.config.getString("serveridentifier"));
            
            //Log this user into the online players table.
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `onlineplayers` (`server`, `player`, `jointime`) VALUES (?, ?, ?)");
            statement.setString(1, IcarusMod.config.getString("serveridentifier"));
            statement.setString(2, player.getName());
            statement.setLong(3, System.currentTimeMillis());
            statement.executeUpdate();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(JoinModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        ICM_PanelLogger.log(ICM_PanelLogger.MessageType.DISCONNECT, event.getPlayer().getName(), event.getQuitMessage());
    }
}

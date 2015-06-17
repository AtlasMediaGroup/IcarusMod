package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Settings;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.ICM_Whitelist;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinModule extends IcarusModule implements Listener
    {

    public JoinModule(IcarusMod plugin)
    {
        super(plugin);
    }

    static List<String> noQuitMessage = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        try
        {
            if (ICM_Bans.isBanned(event.getPlayer().getName()) && !ICM_Rank.isRankOrHigher(event.getPlayer(), ICM_Rank.Rank.SUPER))
            {
                noQuitMessage.add(player.getName());
                player.kickPlayer("§c§lYou are banned!\nYou were banned for: §e" + ICM_Bans.getReason(player.getName()) + "\n§c§lBanned by: §e" + ICM_Bans.getBanner(player.getName()));
            }

            if (ICM_Whitelist.whitelist)
            {
                if (!ICM_Whitelist.isWhitelisted(event.getPlayer().getName()) && !ICM_Rank.isRankOrHigher(event.getPlayer(), ICM_Rank.Rank.SUPER))
                {
                    noQuitMessage.add(player.getName());
                    player.kickPlayer("§f§lThe server is currently whitelisted. Please check back later.");
                }
            }

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

            if (ICM_Rank.getRank(event.getPlayer()).level == -1)
            {
                Bukkit.broadcastMessage(ChatColor.RED + "WARNING: " + event.getPlayer().getName() + " is an imposter. Admins, please deal with this in an appropriate manner.");
            }

            String title = ICM_Settings.getString("settingName", "title-message-on-join", "string");
            String subtitle = ICM_Settings.getString("settingName", "subtitle-message-on-join", "string");
            ICM_Utils.sendTitle(player, title, 2, 2, 2);
            ICM_Utils.sendSubtitle(player, subtitle, 2, 2, 2);
        }

        catch (SQLException ex)
        {
            plugin.getLogger().severe(ex.getLocalizedMessage());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (noQuitMessage.contains(event.getPlayer().getName()))
        {
            event.setQuitMessage(null);
        }

    }
    }

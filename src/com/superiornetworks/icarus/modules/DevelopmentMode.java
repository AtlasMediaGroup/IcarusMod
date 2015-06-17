package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.IcarusMod;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DevelopmentMode extends IcarusModule implements Listener
{

    public DevelopmentMode(IcarusMod plugin)
    {
        super(plugin);
    }

    private static DevMode currentMode;

    public static enum DevMode
    {

        DEV_ONLY, ADMIN_ONLY, EVERYONE, OFF;
    }

    public static boolean isInMode(DevMode mode)
    {
        return DevelopmentMode.currentMode == mode;
    }

    public static void setMode(DevMode mode)
    {
        DevelopmentMode.currentMode = mode;
    }

    //The below list is used to detect who not to put a join message on.
    static List<String> noQuitMessage = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String playername = player.getName();

        if (DevelopmentMode.isInMode(DevMode.DEV_ONLY))
        {
            if (!ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.DEVELOPER))
            {
                noQuitMessage.add(playername);
                event.setJoinMessage(null);
                player.kickPlayer(ChatColor.RED + "We are in development mode, only developers are allowed online currently. Check back later.");
            }
        }
        if (DevelopmentMode.isInMode(DevMode.ADMIN_ONLY))
        {
            if (!ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.SUPER))
            {
                noQuitMessage.add(playername);
                event.setJoinMessage(null);
                player.kickPlayer(ChatColor.RED + "We are in development mode, only admins are allowed online currently. Check back later.");
            }
        }
        if (DevelopmentMode.isInMode(DevMode.EVERYONE))
        {
            player.sendMessage(ChatColor.DARK_RED + "We are currently in development mode. This could result in unexpected errors. Please report such errors to a developer.");
        }
        if (DevelopmentMode.isInMode(DevMode.OFF))
        {

        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        String playername = player.getName();
        if (noQuitMessage.contains(playername))
        {
            event.setQuitMessage(null);
            noQuitMessage.remove(playername);
        }
    }
}

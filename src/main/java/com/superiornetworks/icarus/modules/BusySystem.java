package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.IcarusMod;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BusySystem extends IcarusModule implements Listener
{

    public BusySystem(IcarusMod plugin)
    {
        super(plugin);
    }

    static List<String> busyAdmins = new ArrayList<>();

    public static void toggleBusyPlayer(Player player)
    {
        if (busyAdmins.contains(player.getName()))
        {
            busyAdmins.remove(player.getName());
            player.sendMessage(ChatColor.GREEN + "You have toggled busy status off.");
        }
        else
        {
            busyAdmins.add(player.getName());
            player.sendMessage(ChatColor.GREEN + "You have toggled busy status on.");
        }
    }

    public static boolean isBusy(Player player)
    {
        return busyAdmins.contains(player.getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        busyAdmins.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        final String[] words = event.getMessage().split(" ");
        for (final String word : words)
        {
            Player player = Bukkit.getServer().getPlayer(word);

            if (player == null)
            {
                return;
            }

            if (!ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.ADMINISTRATOR))
            {
                return;
            }

            if (BusySystem.isBusy(player))
            {
                event.getPlayer().sendMessage(ChatColor.RED + "The admin " + player.getName() + " is busy. If you were trying to get his/her attention, please try again later.");
            }
        }
    }

    @EventHandler
    public void commandEvent(PlayerCommandPreprocessEvent event)
    {
        final String[] words = event.getMessage().split(" ");
        for (final String word : words)
        {
            final Player player = server.getPlayer(word);
            if (player == null)
            {
                continue;
            }

            if (!ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.ADMINISTRATOR))
            {
                return;
            }

            if (BusySystem.isBusy(player))
            {
                event.getPlayer().sendMessage(ChatColor.RED + "The admin " + player.getName() + " is busy. If you were trying to get his/her attention, please try again later.");
            }
        }
    }
}

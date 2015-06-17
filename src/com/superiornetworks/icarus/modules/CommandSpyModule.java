package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.IcarusMod;
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
        if (event.isCancelled())
        {
            return;
        }
        ChatColor colour = ChatColor.GRAY;
        List<String> alertCommands = Arrays.asList("//set", "//replace", "//remove", "/ci", "/clear");
        for (String alert : alertCommands)
        {
            if (event.getMessage().toLowerCase().startsWith(alert))
            {
                colour = ChatColor.RED;
            }
        }
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (ICM_Rank.getRank(event.getPlayer()).level < ICM_Rank.getRank(player).level)
            {
                player.sendMessage(colour + event.getPlayer().getName() + ": " + event.getMessage().toLowerCase());
            }
        }
    }
}

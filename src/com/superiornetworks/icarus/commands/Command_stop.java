package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "stop", description = "Stop the server.", usage = "/stop", rank = ICM_Rank.Rank.SUPER)
public class Command_stop
{
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        ICM_Util.bcastMsg("IcarusMod is going offline.", ChatColor.YELLOW);

        for (Player player : server.getOnlinePlayers())
        {
            player.kickPlayer("IcarusMod is going offline, come back in about 20 seconds.");
        }

        server.shutdown();

        return true;
    }
}

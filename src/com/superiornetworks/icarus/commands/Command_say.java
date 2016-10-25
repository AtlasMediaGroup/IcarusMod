package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "say", description = "Broadcast a message to everyone on the current server.", usage = "/say <message>", rank = ICM_Rank.Rank.SUPER)
public class Command_say
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 1)
        {
            return false;
        }
        
        String message = ICM_Utils.buildMessage(args, 0);
        
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server:" + sender.getName() + "] " + ChatColor.stripColor(message));
        return true;
    }
}

package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "rawsay", description = "Broadcast a raw message to the server.", usage = "/rawsay <message>", rank = ICM_Rank.Rank.SENIOR)
public class Command_rawsay 
{
    //This is not a console only command, because it doesn't need to be...
    public boolean onCommand(CommandSender sender, Command cmd, String cL, String[] args)
    {
        if (args.length < 1)
        {
            return false;
        }
        
        String message = ICM_Utils.buildMessage(args, 0);
        
        Bukkit.broadcastMessage(message.replaceAll("&", "§"));
        return true;
    }
}

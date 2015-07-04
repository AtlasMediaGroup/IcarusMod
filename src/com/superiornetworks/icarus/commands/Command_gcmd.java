package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "gcmd", description = "Make another player use a command.", usage = "/gcmd <playername> <command>", rank = ICM_Rank.Rank.SUPER)
public class Command_gcmd 
{
    public boolean onCommand(CommandSender sender, Command cmd, String cL, String[] args)
    {
        if (args.length < 2)
        {
            return false;
        }
        
        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(ChatColor.DARK_RED + "Player not found.");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        
        if (ICM_Rank.getRank(sender).level <= ICM_Rank.getRank(target).level)
        {
            sender.sendMessage(ChatColor.DARK_RED + "You cannot gcmd a player equal to or higher in ranking than yourself.");
            return true;
        }
        
        String command = ICM_Utils.buildMessage(args, 1);
        
        try
        {
            if (Bukkit.getServer().dispatchCommand(target, command))
            {
                sender.sendMessage(ChatColor.GREEN + "Success!");
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "An unknown error occurred during this process.");
            }
        }
        catch (CommandException ex)
        {
            sender.sendMessage(ChatColor.RED + "Error: " + ex);
        }
        return true;
    }
}

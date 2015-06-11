package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="kick",description="Kicks a player",usage="/kick <player> <reason>",rank=ICM_Rank.Rank.SUPER)
public class Command_kick extends BukkitCommand
{   

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 2) {
            return showUsage();
        }
        
        final Player player = getPlayer(args[0]);
        
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }
        
        if (player.getName().equals(sender.getName()))
        {
            sender.sendMessage(ChatColor.DARK_RED + "Please do not attempt to kick yourself.");
            return true;
        }
        
        if (ICM_Rank.getRank(sender).level <= ICM_Rank.getRank(player).level)
        {
            sender.sendMessage(ChatColor.DARK_RED + "You cannot kick someone that is an equal or higher rank than yourself.");
            return true;
        }
        
        String kickReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        
        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Kicking: " + player.getName() + ", Reason: " + kickReason);
        
        player.kickPlayer(ChatColor.RED + "You have been kicked, Reason: " + kickReason + "." +  " Kicked by ~ " + sender.getName());
        
        
        return true;
    }
    
}

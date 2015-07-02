package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import org.bukkit.Bukkit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="smite",description="The most basic form of disciplinary action.",usage="/smite <player> <reason:optional>",rank=ICM_Rank.Rank.SUPER)
public class Command_smite
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 0)
        {
            return false;
        }
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(ChatColor.DARK_RED + "Player not found!");
            return true;
        }
        
        if (player.getName().equals(sender.getName()))
        {
            sender.sendMessage(ChatColor.DARK_RED + "Please do not attempt to smite yourself.");
            return true;
        }
        
        if (ICM_Rank.getRank(sender).level <= ICM_Rank.getRank(player).level)
        {
            sender.sendMessage(ChatColor.DARK_RED + "You cannot smite someone that is an equal or higher rank than yourself.");
            return true;
        }
        
        String smiteReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        
        World world = player.getWorld();
        world.strikeLightningEffect(player.getLocation());
        world.strikeLightningEffect(player.getLocation());
        world.strikeLightningEffect(player.getLocation());
        world.strikeLightningEffect(player.getLocation());
        world.strikeLightningEffect(player.getLocation());
        world.strikeLightningEffect(player.getLocation());
        player.getInventory().clear();
        player.setHealth(0D);
        player.setOp(false);
        player.setGameMode(GameMode.SURVIVAL);
        
        if (smiteReason == null) {
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has been very naughty, indeed.);
        player.sendMessage(ChatColor.RED + "You were smitten by: " + sender.getName());
        }
        
    } else {
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has been very naughty, indeed.\nReason - " + ChatColor.YELLOW + smiteReason);
        player.sendMessage(ChatColor.RED + "You were smitted by: " + sender.getName() + " Reason - " + ChatColor.YELLOW + smiteReason):
    }
        return true;
    }
}

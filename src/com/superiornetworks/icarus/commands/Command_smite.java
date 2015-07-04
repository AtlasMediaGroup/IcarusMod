package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "smite", description = "Someone being a pain? Take em' down.", usage = "/smite <playername> <reason>", rank = ICM_Rank.Rank.SUPER)
public class Command_smite
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 2)
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
            sender.sendMessage(ChatColor.DARK_RED + "You cannot smite someone that is equal or higher in ranking than yourself.");
            return true;
        }

        String reason = ICM_Utils.buildMessage(args, 1);
        
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
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has been very naughty, indeed.");
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " was smitten for: §e" + ChatColor.stripColor(reason));
        player.sendMessage(ChatColor.RED + "You were smitten by: " + sender.getName());
        return true;
    }
}

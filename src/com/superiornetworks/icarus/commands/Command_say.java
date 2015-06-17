package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "say", description = "Have a message? Use the power ot shout it loud with the say command.", usage = "/say", rank = ICM_Rank.Rank.SUPER)
public class Command_say
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0)
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
        player.sendMessage(ChatColor.RED + "You were smitten by: " + sender.getName());
        return true;
    }
}

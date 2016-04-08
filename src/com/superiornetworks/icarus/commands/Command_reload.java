package com.superiornetwork.icarus.commands;

import com.superiornetworks.icarus.ICM_Util;
import com.superiornetworks.icarus.ICM_Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "reload", description = "Reload the Server.", usage = "/reload <command>", rank = ICM_Rank.Rank.TELNET)
public class Command_reload
{
  {
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        ICM_Util.bcastMsg("[IcarusMod] is reloading. Please wait!", ChatColor.YELLOW);

        server.reload();
        ICM_Rank.adminAction(sender.getName(), "Disconected!", true);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.kickPlayer(ChatColor.RED + "IcarusMod: " + ChatColor.YELLOW + "You have been kicked by " + sender.getName() + " because of a reload. Please relogin in.");
        }
        return true;
    }
}

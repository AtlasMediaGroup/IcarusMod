package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@CommandParameters(name = "wetoggle", description = "Toggles the worldedit plugin between enabled and disabled.", usage = "/wetoggle", rank = ICM_Rank.Rank.SUPER)
public class Command_wetoggle
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!ICM_Rank.isRankOrHigher(sender, ICM_Rank.Rank.SUPER))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }
        PluginManager pm = Bukkit.getServer().getPluginManager();
        Plugin we = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        if (we == null)
        {
            return true;
        }

        if (we.isEnabled())
        {
            sender.sendMessage(ChatColor.GREEN + "WorldEdit disabled!");
            pm.disablePlugin(we);
            return true;
        }
        else
        {
            sender.sendMessage(ChatColor.GREEN + "WorldEdit enabled!");
            pm.enablePlugin(we);
        }
        return true;
    }
}

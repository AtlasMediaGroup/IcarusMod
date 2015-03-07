package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.modules.DevelopmentMode;
import com.superiornetworks.icarus.modules.DevelopmentMode.DevMode;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ANY, permission = "")
public class Command_devmode extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (!ICM_Utils.DEVELOPERS.contains(sender.getName()))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }

        if (args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /<command> <everyone:admins:developers:off>");
            return true;
        }
        if (args[0].equalsIgnoreCase("everyone"))
        {
            DevelopmentMode.setMode(DevMode.EVERYONE);
            sender.sendMessage(ChatColor.GREEN + "Developement mode has been enabled, anyone who joins will get a warning.");
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "Development mode has been enabled. This could result in unexpected errors. Please report such errors to a developer.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("admins"))
        {
            DevelopmentMode.setMode(DevMode.ADMIN_ONLY);
            for (Player p : Bukkit.getOnlinePlayers())
            {
                if (!TFM_AdminList.isSuperAdmin(p))
                {
                    p.kickPlayer(ChatColor.DARK_RED + "Developement mode has been enabled, only admins are allowed online.");
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Developement mode has been enabled, only admins can join.");
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "Development mode has been enabled. This could result in unexpected errors. Please report such errors to a developer.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("developers"))
        {
            DevelopmentMode.setMode(DevMode.DEV_ONLY);
            for (Player p : Bukkit.getOnlinePlayers())
            {
                if (!ICM_Utils.DEVELOPERS.contains(p.getName()))
                {
                    p.kickPlayer(ChatColor.DARK_RED + "Developement mode has been enabled, only developers are allowed online.");
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Developement mode has been enabled, only developers can join.");
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "Development mode has been enabled. This could result in unexpected errors.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("off"))
        {
            DevelopmentMode.setMode(DevMode.OFF);
            sender.sendMessage(ChatColor.GREEN + "Development mode has been disabled!");
            Bukkit.broadcastMessage(ChatColor.GREEN + "Development mode has been disabled. All players can now join.");
            return true;
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Usage: /<command> <everyone|admins|developers|off>");
        }
        return true;
    }

}

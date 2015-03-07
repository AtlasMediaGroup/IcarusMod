package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@CommandPermissions(source = SourceType.ANY, permission = "")
public class Command_wetoggle extends BukkitCommand<IcarusMod>
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String string, String[] args)
    {
        if (!TFM_AdminList.isSuperAdmin(sender))
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

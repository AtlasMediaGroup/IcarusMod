package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.command.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission="")
public class Command_icmtoggle extends BukkitCommand
{
    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(!ICM_Utils.MANAGERS.contains(player.getName()))
            {
                TFM_Util.playerMsg(sender, ICM_Utils.NO_PERMS_MESSAGE);
                return true;
            }
        }
        if(args.length == 0)
        {
            TFM_Util.playerMsg(sender, ChatColor.AQUA + "Possible toggles:");
            for(String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
            {
                TFM_Util.playerMsg(sender, " - " + (IcarusMod.config.getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
            }
            return true;
        }
        if(args.length == 1)
        {
            for(String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
            {
                if(args[0].equalsIgnoreCase(toggle))
                {
                    IcarusMod.config.set("toggles." + toggle, !IcarusMod.config.getBoolean("toggles." + toggle));
                    TFM_Util.playerMsg(sender, ChatColor.GOLD + "Toggled " + toggle + (IcarusMod.config.getBoolean("toggles." + toggle) ? " on." : " off."));
                    return true;
                }
            }
            TFM_Util.playerMsg(sender, ChatColor.AQUA + "Possible toggles:");
            for(String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
            {
                TFM_Util.playerMsg(sender, " - " + (IcarusMod.config.getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
            }
            return true;
        }
        return false;
    }
}

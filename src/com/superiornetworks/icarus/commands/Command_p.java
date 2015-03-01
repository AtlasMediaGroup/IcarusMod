package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.modules.SeniorAdminChat;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_p extends BukkitCommand 
{
    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (!TFM_AdminList.isSeniorAdmin(sender, true))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }
        
        if (args.length <1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /<command> <message>");
            return true;
        }
        SeniorAdminChat.chat(sender, StringUtils.join(args, " "));
        return true;        
    }    
}

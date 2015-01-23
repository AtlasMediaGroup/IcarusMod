package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.playerMsg;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.command.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission="")
public class Command_god extends BukkitCommand
{
    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            return true;
        }
        Player player = (Player) sender;
        if(ICM_Utils.GOD.contains(player.getName()))
        {
            playerMsg(sender, "Enabled god mode.", ChatColor.GREEN);
            ICM_Utils.GOD.remove(player.getName());
        }
        else
        {
            playerMsg(sender, "Disabled god mode.", ChatColor.RED);
            ICM_Utils.GOD.add(player.getName());
        }
        return true;
    }
}

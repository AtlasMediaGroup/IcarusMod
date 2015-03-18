package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_god extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            return true;
        }
        Player player = (Player) sender;
        if (ICM_Utils.GOD.contains(player.getName()))
        {
            playerMsg(sender, "&aEnabled god mode.");
            ICM_Utils.GOD.remove(player.getName());
        }
        else
        {
            playerMsg(sender, "&aDisabled god mode.");
            ICM_Utils.GOD.add(player.getName());
        }
        return true;
    }
}

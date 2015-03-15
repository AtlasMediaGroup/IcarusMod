package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import static com.superiornetworks.icarus.ICM_Utils.adminAction;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_doomhammer extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            return true;
        }
        Player player = (Player) sender;
        if (!ICM_Utils.MANAGERS.contains(player.getName()))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }
        if (!ICM_Utils.DOOMHAMMERS.contains(player.getName()))
        {
            ICM_Utils.adminAction(player.getName(), "Unleashing the DOOM-HAMMER!", true);
            for (int i = 0; i < 5; i++)
            {
                player.getWorld().strikeLightningEffect(player.getLocation());
            }
            ICM_Utils.playerMsg(sender, "&aEnabled doomhammer mode.");
            player.getInventory().addItem(ICM_Utils.getDoomHammer());
            ICM_Utils.DOOMHAMMERS.add(player.getName());
        }
        else
        {
            adminAction(player.getName(), "Returning the Doom-Hammer to its sheath.", false);
            ICM_Utils.playerMsg(sender, "&cDisabled doomhammer mode.");
            player.getInventory().remove(ICM_Utils.getDoomHammer());
            ICM_Utils.DOOMHAMMERS.remove(player.getName());
        }
        return true;
    }
}

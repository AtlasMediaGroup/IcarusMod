package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.playerMsg;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.command.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission="")
public class Command_doomhammer extends BukkitCommand
{
    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            return true;
        }
        Player player = (Player) sender;
        if(!ICM_Utils.MANAGERS.contains(player.getName()))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }
        if(!ICM_Utils.DOOMHAMMERS.contains(player.getName()))
        {
            TFM_Util.adminAction(player.getName(), "Unleashing the DOOM-HAMMER!", true);
            for(int i = 0; i < 5; i++)
            {
                player.getWorld().strikeLightningEffect(player.getLocation());
            }
            playerMsg(sender, "Enabled doomhammer mode.", ChatColor.GREEN);
            player.getInventory().addItem(ICM_Utils.getDoomHammer());
            ICM_Utils.DOOMHAMMERS.add(player.getName());
        }
        else
        {
            TFM_Util.adminAction(player.getName(), "Returning the Doom-Hammer to its sheath.", false);
            playerMsg(sender, "Disabled doomhammer mode.", ChatColor.RED);
            player.getInventory().remove(ICM_Utils.getDoomHammer());
            ICM_Utils.DOOMHAMMERS.remove(player.getName());
        }
        return true;
    }
}

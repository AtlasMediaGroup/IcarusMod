package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "unban", description = "Unban a player.", usage = "/unban <player>", rank = ICM_Rank.Rank.ADMINISTRATOR)
public class Command_unban
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length < 1)
        {
            return false;
        }
        String name = args[0];
        try
        {
            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            if(!player.hasPlayedBefore() || !ICM_Bans.isBanned(player))
            {
                ICM_Utils.playerMsg(sender, "&cThat player is not banned.");
                return true;
            }
            ICM_Bans.removeBanWithCheck(sender, player);
            ICM_Utils.adminAction(sender.getName(), "Unbanning " + name + ".", true);
            return true;
        }
        catch(Exception ex)
        {
            Logger.getLogger(Command_ban.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

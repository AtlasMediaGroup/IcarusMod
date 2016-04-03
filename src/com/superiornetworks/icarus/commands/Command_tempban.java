package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "tempban", description = "Ban a bad player for a termporary amount of time.", usage = "/tempban <player> <time> <h | m> <reason>", rank = ICM_Rank.Rank.SUPER, aliases="tban")
public class Command_tempban
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length < 4)
        {
            return false;
        }
        String reason = StringUtils.join(ArrayUtils.subarray(args, 3, args.length), " ");
        Player player = Bukkit.getPlayer(args[0]);
        String playerName = args[0];
        if (player != null)
        {
            playerName = player.getName();
            int level = ICM_Rank.getRank(sender).getLevel();
            level++;
            if (ICM_Rank.isRankOrHigher(player, level))
            {
                ICM_Utils.playerMsg(sender, "&cYou cannot ban someone of a higher level than yourself!");
                return false;
            }
        }

        try
        {
            if (ICM_Bans.isBanned(player))
            {
                ICM_Utils.playerMsg(sender, "&cThat player is already banned.");
                return true;
            }
            int timearg = Integer.parseInt(args[1]);
            TimeUnit unit;
            switch(args[2])
            {
                case "h":
                case "H":
                    if(timearg > 24)
                    {
                        ICM_Utils.playerMsg(sender, "You must define a time limit below 24 hours.");
                        return false;
                    }
                    unit = TimeUnit.HOURS;
                    break;
                case "m":
                case "M":
                    if(timearg > 24 * 60)
                    {
                        ICM_Utils.playerMsg(sender, "You must define a time limit below 24 hours.");
                        return false;
                    }
                    unit = TimeUnit.MINUTES;
                    break;
                default:
                    ICM_Utils.playerMsg(sender, "&cYou failed to provide a valid time unit, defaulting to minutes.");
                    if(timearg > 24 * 60)
                    {
                        ICM_Utils.playerMsg(sender, "You must define a time limit below 24 hours.");
                        return false;
                    }
                    unit = TimeUnit.MINUTES;
            }
            ICM_Bans.addBan(player, sender, reason, TimeUnit.MILLISECONDS.convert(timearg, unit));
            ICM_Utils.adminAction(sender.getName(), "Banning " + playerName + " for " + timearg + (unit == TimeUnit.MINUTES ? "minutes" : " hours") + ". Reason: " + reason, true);
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Command_ban.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (NumberFormatException ex)
        {
            ICM_Utils.playerMsg(sender, "You failed to provide a valid amount of time, this must be a positive integer value below the equivelant of 24 hours.");
            return true;
        }
    }
}

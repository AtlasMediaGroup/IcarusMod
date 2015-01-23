package com.superiornetworks.icarus;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ICM_Bans
{
    public static void addBan(Player player, CommandSender sender, String reason)
    {
        try
        {
            long Time = System.currentTimeMillis() / 1000;
            String ip = TFM_Util.getFuzzyIp(player.getAddress().getAddress().getHostAddress());
            ICM_SqlHandler.updateDatabase("INSERT INTO bans (Name, UUID, IP, BanBy, Reason, Time) VALUES ('" + StringEscapeUtils.escapeSql(player.getName()) + "', '" + StringEscapeUtils.escapeSql(player.getUniqueId().toString()) + "', '" + StringEscapeUtils.escapeSql(ip) + "', '" + StringEscapeUtils.escapeSql(sender.getName()) + "', '" + StringEscapeUtils.escapeSql(reason) + "','" + Time + "');");
            player.kickPlayer("Banned:\n" + reason + " ~ " + sender.getName());
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ICM_Bans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

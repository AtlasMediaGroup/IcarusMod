package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ICM_Bans
{

    public static void addBan(OfflinePlayer player, CommandSender sender, String reason, long time) throws SQLException
    {
        if(isBanned(player))
        {
            sender.sendMessage(ChatColor.RED + player.getName() + " is already banned.");
            return;
        }
        String ip;
        if(player.isOnline())
        {
            ip = Bukkit.getPlayer(player.getName()).getAddress().getAddress().getHostAddress();
            Bukkit.getPlayer(player.getName()).kickPlayer("§c§lYou have been banned!\nYou were banned for: §e" + reason + "\n§c§lBanned by: §e" + sender.getName());
        }
        else
        {
            ip = ICM_SqlHandler.getIp(player.getName());
        }
        Connection c = ICM_SqlHandler.getConnection();
        PreparedStatement statement = c.prepareStatement("INSERT INTO `bans` (`senderName`, `playerName`, `banReason`, `ip`, `time`, `banTime`) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setString(1, sender.getName());
        statement.setString(2, player.getName());
        statement.setString(3, reason);
        statement.setString(4, ip);
        statement.setLong(5, time);
        statement.setLong(6, System.currentTimeMillis());
        statement.executeUpdate();
    }

    public static void removeBanWithCheck(CommandSender sender, OfflinePlayer player) throws SQLException
    {
        if(!isBanned(player) && sender != null)
        {
            sender.sendMessage(ChatColor.RED + player.getName() + " is not banned.");
        }
        else
        {
            removeBan(sender, player);
        }
    }

    public static void removeBan(CommandSender sender, OfflinePlayer player) throws SQLException
    {
        Connection c = ICM_SqlHandler.getConnection();
        PreparedStatement statement = c.prepareStatement("DELETE FROM `bans` WHERE `playerName` = ? OR `ip` = ?");
        statement.setString(1, player.getName());
        statement.setString(2, ICM_SqlHandler.getIp(player.getName()));
        statement.executeUpdate();
    }

    public static String getReason(OfflinePlayer player) throws SQLException
    {
        if(!isBanned(player))
        {
            return ChatColor.RED + player.getName() + " is not banned.";
        }
        Object obj = ICM_SqlHandler.getFromTable("playerName", player.getName(), "banReason", "bans");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        obj = ICM_SqlHandler.getFromTable("ip", ICM_SqlHandler.getIp(player.getName()), "banReason", "bans");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return ChatColor.RED + "No reason given...";
    }

    public static String getBanner(OfflinePlayer player) throws SQLException
    {
        if(!isBanned(player))
        {
            return ChatColor.RED + "Player is not banned.";
        }
        Object obj = ICM_SqlHandler.getFromTable("playerName", player.getName(), "senderName", "bans");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        obj = ICM_SqlHandler.getFromTable("ip", ICM_SqlHandler.getIp(player.getName()), "senderName", "bans");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return ChatColor.RED + "No sender given? Maybe the data was manually entered into the database?";
    }

    public static boolean isBanned(OfflinePlayer player) throws SQLException
    {
        try
        {
            Long time = 0L;
            Long bantime = 0L;
            Object obj = ICM_SqlHandler.getFromTable("playerName", player.getName(), "time", "bans");
            if(obj instanceof Long)
            {
                time = (Long) obj;
            }
            else
            {
                obj = ICM_SqlHandler.getFromTable("ip", ICM_SqlHandler.getIp(player.getName()), "time", "bans");
                if(obj instanceof Long)
                {
                    time = (Long) obj;
                }
            }
            obj = ICM_SqlHandler.getFromTable("playerName", player.getName(), "banTime", "bans");
            if(obj instanceof Long)
            {
                bantime = (Long) obj;
            }
            else
            {
                obj = ICM_SqlHandler.getFromTable("ip", ICM_SqlHandler.getIp(player.getName()), "time", "bans");
                if(obj instanceof Long)
                {
                    bantime = (Long) obj;
                }
            }
            if(time == 0 || bantime == 0)
            {
                return false;
            }
            if(System.currentTimeMillis() - time < bantime)
            {
                removeBanWithCheck(null, player);
                return false;
            }
            return true;
        }
        catch(ClassCastException ex)
        {
            return false;
        }
    }
}

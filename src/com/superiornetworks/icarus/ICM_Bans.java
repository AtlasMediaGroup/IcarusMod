package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ICM_Bans
    {

    public static void addBan(String player, CommandSender sender, String reason) throws SQLException
    {
        if (isBanned(player))
        {
            sender.sendMessage(ChatColor.RED + player + " is already banned.");
            return;
        }
        String ip;
        if (Bukkit.getPlayer(player) != null)
        {
            ip = Bukkit.getPlayer(player).getAddress().getAddress().getHostAddress();
            Bukkit.getPlayer(player).kickPlayer("§c§lYou have been banned!\nYou were banned for: §e" + reason + "\n§c§lBanned by: §e" + sender.getName());
        }
        else
        {
            Object obj = ICM_SqlHandler.getFromTable("playerName", player, "ip", "players");
            if (obj instanceof String)
            {
                ip = (String) obj;
            }
            else
            {
                ip = "0.0.0.0";
            }
        }
        Connection c = ICM_SqlHandler.getConnection();
        PreparedStatement statement = c.prepareStatement("INSERT INTO `bans` (`senderName`, `playerName`, `banReason`, `ip`) VALUES (?, ?, ?, ?)");
        statement.setString(1, sender.getName());
        statement.setString(2, player);
        statement.setString(3, reason);
        statement.setString(4, ip);
        statement.executeUpdate();
    }

    public static void removeBan(CommandSender sender, String player) throws SQLException
    {
        if (!isBanned(player))
        {
            sender.sendMessage(ChatColor.RED + player + " is not banned.");
        }
        else
        {
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("DELETE FROM `bans` WHERE `playerName` = ?");
            statement.setString(1, player);
            statement.executeUpdate();
        }
    }

    public static String getReason(String player) throws SQLException
    {
        if (!isBanned(player))
        {
            return ChatColor.RED + player + " is not banned.";
        }
        Object obj = ICM_SqlHandler.getFromTable("playerName", player, "banReason", "bans");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return ChatColor.RED + "No reason given...";
    }

    public static String getBanner(String player) throws SQLException
    {
        if (!isBanned(player))
        {
            return ChatColor.RED + "Player is not banned.";
        }
        Object obj = ICM_SqlHandler.getFromTable("playerName", player, "senderName", "bans");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return ChatColor.RED + "No sender given? Maybe the data was manually entered into the database?";
    }

    public static boolean isBanned(String player) throws SQLException
    {
        return ICM_SqlHandler.getFromTable("playerName", player, "playerName", "bans") != null;
    }
    }

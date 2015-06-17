package com.superiornetworks.icarus;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ICM_CommandBlock
    {

    public static boolean isBlocked(CommandSender sender, String command) throws SQLException
        {
        String[] label = command.split(" ");
        String search = label[0].replaceAll("/", "");
        if (Bukkit.getCommandAliases().get(search) != null)
            {
            for (String newCommand : Bukkit.getCommandAliases().get(search))
                {
                Object obj = ICM_SqlHandler.getFromTable("commandName", newCommand, "level", "commands");
                if (!(obj instanceof Integer))
                    {
                    continue;
                    }
                int commandLevel = (Integer) obj;
                int senderLevel = ICM_Rank.getRank(sender).level;
                return senderLevel < commandLevel;
                }
            }
        Object obj = ICM_SqlHandler.getFromTable("commandName", search, "level", "commands");
        if (!(obj instanceof Integer))
            {
            return false;
            }
        int commandLevel = (Integer) obj;
        int senderLevel = ICM_Rank.getRank(sender).level;
        return senderLevel < commandLevel;
        }

    public static String getMessage(String command) throws SQLException
        {
        String[] label = command.split(" ");
        String search = label[0].replaceAll("/", "");
        if (Bukkit.getCommandAliases().get(search) != null)
            {
            for (String newCommand : Bukkit.getCommandAliases().get(search))
                {
                Object obj = ICM_SqlHandler.getFromTable("commandName", newCommand, "message", "commands");
                if (!(obj instanceof String))
                    {
                    continue;
                    }
                return (String) obj;
                }
            }
        Object obj = ICM_SqlHandler.getFromTable("commandName", search, "message", "commands");
        if (!(obj instanceof String))
            {
            return "No message defined";
            }
        return (String) obj;
        }

    public static boolean isKicker(String command) throws SQLException
        {
        String[] label = command.split(" ");
        String search = label[0].replaceAll("/", "");
        if (Bukkit.getCommandAliases().get(search) != null)
            {
            for (String newCommand : Bukkit.getCommandAliases().get(search))
                {
                Object obj = ICM_SqlHandler.getFromTable("commandName", newCommand, "kick", "commands");
                if (!(obj instanceof Boolean))
                    {
                    continue;
                    }
                return (Boolean) obj;
                }
            }
        Object obj = ICM_SqlHandler.getFromTable("commandName", search, "kick", "commands");
        if (!(obj instanceof Boolean))
            {
            return false;
            }
        return (Boolean) obj;
        }
    }

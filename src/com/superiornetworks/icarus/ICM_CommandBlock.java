package com.superiornetworks.icarus;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ICM_CommandBlock
{

    public static boolean isBlocked(CommandSender sender, String command) throws SQLException
    {
        String[] label = command.split(" ");
        if(Bukkit.getCommandAliases().get(label[0]) != null)
        {
            for(String newCommand : Bukkit.getCommandAliases().get(label[0]))
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

        return false;
    }
    
    public static String getMessage(String command) throws SQLException
    {
        String[] label = command.split(" ");
        for(String newCommand : Bukkit.getCommandAliases().get(label[0]))
        {
            Object obj = ICM_SqlHandler.getFromTable("commandName", newCommand, "message", "commands");
            if (!(obj instanceof String))
            {
                continue;
            }
            return (String) obj;
        }
        return "Command is not blocked...";
    }
    
    public static boolean isKicker(String command) throws SQLException
    {
        String[] label = command.split(" ");
        for(String newCommand : Bukkit.getCommandAliases().get(label[0]))
        {
            Object obj = ICM_SqlHandler.getFromTable("commandName", newCommand, "kick", "commands");
            if (!(obj instanceof Boolean))
            {
                continue;
            }
            return (Boolean) obj;
        }
        return false;
    }
}

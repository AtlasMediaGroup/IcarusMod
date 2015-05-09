package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ICM_Rank
{
    public enum Rank
    {
        OP(0, "Op"), SUPER(1, "Super Admin"), TELNET(2, "Telnet Admin"), SENIOR(3, "Senior Admin"), DEVELOPER(4, "Developer"), MANAGER(5, "Manager");
        
        public int level;
        public String name;
        Rank(int level, String name)
        {
            this.level = level;
            this.name = name;
        }
        public int getLevel()
        {
            return this.level;
        }
    }
    
    public static boolean isRankOrHigher(CommandSender player, Rank rank)
    {
        return getRank(player).level >= rank.level;
    }
    
    public static boolean isRankOrHigher(CommandSender player, int level)
    {
        return getRank(player).level >= level;
    }
    
    public static Rank getRank(CommandSender player)
    {
        if(!(player instanceof Player))
        {
            if(player.getName().equalsIgnoreCase("CONSOLE"))
            {
                return Rank.SENIOR;
            }
            return Rank.TELNET;
        }
        try
        {
            String rankString = ICM_SqlHandler.getRank(player.getName());
            for(Rank rank : Rank.values())
            {
                if(rank.name.equalsIgnoreCase(rankString))
                {
                    return rank;
                }
            }
            return Rank.OP;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ICM_Rank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Rank.OP;
    }
    
    public static Rank getRank(String playerName)
    {
        try
        {
            String rankString = ICM_SqlHandler.getRank(playerName);
            for(Rank rank : Rank.values())
            {
                if(rank.name.equalsIgnoreCase(rankString))
                {
                    return rank;
                }
            }
        }
        catch(SQLException ex)
        {
            Logger.getLogger(ICM_Rank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Rank.OP;
    }
    
    public static Rank getFromName(String name)
    {
        for(Rank rank : Rank.values())
        {
            if(rank.name.equalsIgnoreCase(name))
            {
                return rank;
            }
        }
        return Rank.OP;
    }
    
    public static Rank getFromLevel(int level)
    {
        for(Rank rank : Rank.values())
        {
            if(rank.level == level)
            {
                return rank;
            }
        }
        return Rank.OP;
    }
    
    public static void setRank(String playerName, Rank rank)
    {
        try
        {
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `rank` = ? WHERE `playerName` = ?");
            statement.setString(1, rank.name);
            statement.setString(2, playerName);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ICM_Rank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

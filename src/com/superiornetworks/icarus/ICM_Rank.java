package com.superiornetworks.icarus;

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
        
        int level;
        String name;
        Rank(int level, String name)
        {
            this.level = level;
            this.name = name;
        }
    }
    
    public static boolean isRankOrHigher(CommandSender player, Rank rank)
    {
        return getRank(player).level >= rank.level;
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
}

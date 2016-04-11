package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "opall", description = "Op all non-op players online.", usage = "/opall", rank = ICM_Rank.Rank.SUPER)
public class Command_opall
{

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(args.length != 0)
        {
            return false;
        }
        ICM_Utils.adminAction(sender.getName(), "Opping all online players.", false);
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(!p.isOp())
            {
                p.sendMessage(ChatColor.YELLOW + "You are now op.");
                p.setOp(true);
            }

            if(ICM_Rank.getRank(p) == ICM_Rank.Rank.NONOP)
            {
                try
                {
                    Connection c = ICM_SqlHandler.getConnection();
                    PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `rank` = 'Op' WHERE `playerName` = ?");
                    statement.setString(1, p.getName());
                }
                catch(SQLException ex)
                {
                    IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
                }
            }
        }
        return true;
    }
}

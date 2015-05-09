package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Rank;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name="unban",description="Unban a player.",usage="/unban <player>",rank=ICM_Rank.Rank.SUPER)
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
            ICM_Bans.removeBan(sender, name);
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Command_ban.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

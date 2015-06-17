package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
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
        if (args.length != 0)
        {
            return false;
        }
        ICM_Utils.adminAction(sender.getName(), "Opping all online players.", false);
        //Uncomment these lines of code when non-op and op ranks have been added.
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (!p.isOp())
            {
                p.sendMessage(ChatColor.YELLOW + "You are now op.");
                p.setOp(true);
            }

            //if (ICM_Rank.isRank(Rank.NonOp)
            //{
            //Connection c = getConnection();
            //PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `rank` = Op WHERE `playerName` = ?");
            //statement.setString(1, p.getName());
            //}
            //To prevent security breaches, it may be a good idea to block certain charecters in join names, if not alreayd done by MC.
        }
        return true;
    }
}

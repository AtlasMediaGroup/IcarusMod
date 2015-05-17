package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="rawsay",description="Say a raw message in chat.",usage="/rawsay <message>",rank=ICM_Rank.Rank.SENIOR)
public class Command_rawsay extends BukkitCommand
{
    @Override
    public boolean run(CommandSender sender, Command cmnd, String string, String[] args)   
   {
       Player p = (Player) sender; 
         if (!ICM_Rank.isRankOrHigher(p, ICM_Rank.Rank.SENIOR))
         {
             p.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
         }
      
        
        if (sender instanceof Player)
            {

                    if (args.length > 0)
                    {
                        Bukkit.broadcastMessage(ICM_Utils.colour(StringUtils.join(args, " ")));
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            else
            {
                Bukkit.broadcastMessage(ICM_Utils.colour(StringUtils.join(args, " ")));
                return true;
            }       

    }    
}

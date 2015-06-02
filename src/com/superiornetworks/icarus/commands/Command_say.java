package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_say extends JavaPlguin 
{
    public say(IcarusMod plugin) 
    {
        super(plugin);
    }
    
    public static void chat(CommandSender sender, String message)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (ICM_Rank.isRankOrHigher(p, ICM_Rank.Rank.SUPER))
            {
                // Some strange ass colour codes being used here...
                p.sendMessage("§d[Server: §d" + sender.getName() + "] " + message);
            }
        }
    }
}

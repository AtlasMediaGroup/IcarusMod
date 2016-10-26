package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChat extends IcarusModule
{

    public AdminChat(IcarusMod plugin)
    {
        super(plugin);
    }

    public static void AdminChatMsg(CommandSender sender, String message)
    {
        for (Player onlineadmins : Bukkit.getOnlinePlayers())
        {
            if (ICM_Rank.isRankOrHigher(onlineadmins, ICM_Rank.Rank.SUPER))
            {
                // Some strange ass colour codes being used here...
                onlineadmins.sendMessage("§f[§bADMIN§f] §c" + ICM_SqlHandler.getNick(sender) + ICM_Rank.getRank(sender).actag + "§f: §b " + message);
            }
        }
    }
}

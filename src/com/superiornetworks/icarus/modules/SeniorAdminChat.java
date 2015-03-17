package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeniorAdminChat extends IcarusModule 
{
    public SeniorAdminChat(IcarusMod plugin) 
    {
        super(plugin);
    }
    
    public static void chat(CommandSender sender, String message)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (TFM_AdminList.isSeniorAdmin(p, true))
            {
                // Some strange ass colour codes being used here...
                p.sendMessage("§f[§bADMIN§f] §4" + sender.getName() + " §d[SrA]§f: §d " + message);
            }
        }
    }
}

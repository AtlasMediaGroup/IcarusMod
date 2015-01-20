package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.entity.Player;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;

public class FamousWarning extends IcarusModule
{

    public FamousWarning(IcarusMod plugin)
    {
        super(plugin);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (TFM_ConfigEntry.UNBANNABLE_USERNAMES.getList().contains(player.getName().toLowerCase()))
        {
            TFM_Util.bcastMsg(ChatColor.DARK_RED + "Attention: " + ChatColor.DARK_PURPLE + ChatColor.BOLD + player.getName() + ChatColor.RESET + ChatColor.DARK_RED + " is listed in our unbannable usernames list meaning they are likely a fake. Please take the appropriate action.");
        }

    }

}

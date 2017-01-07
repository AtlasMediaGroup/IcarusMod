package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_PanelLogger;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModule extends IcarusModule implements Listener
{

    public ChatModule(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event)
    {
        String coloured = ICM_Utils.colour(event.getMessage());
        event.setMessage(coloured);
        Player player = event.getPlayer();
        ICM_PanelLogger.log(ICM_PanelLogger.MessageType.CHAT, player.getName(), ChatColor.stripColor(coloured));
        player.setDisplayName(ICM_Utils.colour(ICM_Rank.getTag(player) + "&r " + ICM_Rank.getNick(player) + "&r"));
    }
}

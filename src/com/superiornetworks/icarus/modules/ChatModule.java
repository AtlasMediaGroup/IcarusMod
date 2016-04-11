package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_PanelLogger;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try
        {
            player.setDisplayName(ICM_Utils.colour(ICM_SqlHandler.getTag(player.getName()) + "&r " + ICM_SqlHandler.getNick(player.getName()) + "&r"));
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ChatModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

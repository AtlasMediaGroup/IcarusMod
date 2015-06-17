package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_CommandBlock;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandBlockModule extends IcarusModule implements Listener
    {

    public CommandBlockModule(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        try
        {
            if (ICM_CommandBlock.isBlocked(event.getPlayer(), event.getMessage()))
            {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ICM_Utils.colour(ICM_CommandBlock.getMessage(event.getMessage())));
                if (ICM_CommandBlock.isKicker(event.getMessage()))
                {
                    event.getPlayer().kickPlayer(ICM_Utils.colour(ICM_CommandBlock.getMessage(event.getMessage())));
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(CommandBlockModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }

package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ImposterModule extends IcarusModule implements Listener
    {

    public ImposterModule(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        if (ICM_Rank.getRank(event.getPlayer()).getLevel() == -1)
        {
            event.getPlayer().teleport(event.getFrom());
            ICM_Utils.playerMsg(event.getPlayer(), "&cYou may not move whilst impostered.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        if (ICM_Rank.getRank(event.getPlayer()).getLevel() == -1)
        {
            ICM_Utils.playerMsg(event.getPlayer(), "&cYou may not send commands whilst impotered.");
            event.setCancelled(true);
        }
    }
    }

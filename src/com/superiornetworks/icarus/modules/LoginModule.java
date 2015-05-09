package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginModule extends IcarusModule implements Listener
{
    public LoginModule(IcarusMod plugin)
    {
        super(plugin);
    }
    
    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event)
    {
        try
        {
            if(ICM_Bans.isBanned(event.getPlayer().getName()) && !ICM_Rank.isRankOrHigher(event.getPlayer(), ICM_Rank.Rank.SUPER))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ICM_Bans.getReason(event.getPlayer().getName()) + " ~ " + ICM_Bans.getBanner(event.getPlayer().getName()));
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ICM_DetailLogger extends BukkitRunnable
{
    ArrayList<Player> prevplayers = new ArrayList<>();

    @Override
    public void run()
    {
        try
        {
            String id = IcarusMod.config.getString("serveridentifier");
            double tps = ICM_TpsFinder.getTPS();
            long ram = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `serverdetails` (`server`, `ram`, `tps`, `time`) VALUES (?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setLong(2, ram);
            statement.setDouble(3, tps);
            statement.setLong(4, System.currentTimeMillis());
            statement.executeUpdate();
            //Remove any players from the onlineplayers table who are no longer online.
            for(Player player : prevplayers)
            {
                if(!player.isOnline())
                {
                    statement = c.prepareStatement("DELETE FROM `onlineplayers` WHERE `player` = ? AND `server` = ?");
                    statement.setString(1, player.getName());
                    statement.setString(2, id);
                    statement.executeUpdate();
                }
            }
            prevplayers.clear();
            prevplayers.addAll(Bukkit.getOnlinePlayers());
        }
        catch(SQLException ex)
        {
            Logger.getLogger(ICM_DetailLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

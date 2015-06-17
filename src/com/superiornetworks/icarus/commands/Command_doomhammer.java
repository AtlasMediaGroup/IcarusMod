package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import static com.superiornetworks.icarus.ICM_Utils.adminAction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "doomhammer", description = "Toggle your doomhammer mode.", usage = "/doomhammer", rank = ICM_Rank.Rank.MANAGER)
public class Command_doomhammer
    {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
        {
        try
            {
            if (!(sender instanceof Player))
                {
                return true;
                }
            Player player = (Player) sender;
            if (!ICM_SqlHandler.hasDoomHammer(player.getName()))
                {
                ICM_Utils.adminAction(player.getName(), "Unleashing the DOOM-HAMMER!", true);
                for (int i = 0; i < 5; i++)
                    {
                    player.getWorld().strikeLightningEffect(player.getLocation());
                    }
                ICM_Utils.playerMsg(sender, "&aEnabled doomhammer mode.");
                player.getInventory().addItem(ICM_Utils.getDoomHammer());
                Connection c = ICM_SqlHandler.getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `doomHammer` = TRUE WHERE `playerName` = ?");
                statement.setString(1, player.getName());
                statement.executeUpdate();
                }
            else
                {
                adminAction(player.getName(), "Returning the Doom-Hammer to its sheath.", false);
                ICM_Utils.playerMsg(sender, "&cDisabled doomhammer mode.");
                player.getInventory().remove(ICM_Utils.getDoomHammer());
                Connection c = ICM_SqlHandler.getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `doomHammer` = FALSE WHERE `playerName` = ?");
                statement.setString(1, player.getName());
                statement.executeUpdate();
                }
            return true;
            }
        catch (SQLException ex)
            {
            Logger.getLogger(Command_doomhammer.class.getName()).log(Level.SEVERE, null, ex);
            }
        return true;
        }
    }

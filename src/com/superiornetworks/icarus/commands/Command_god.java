package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_SqlHandler;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_god extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
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
                playerMsg(sender, "&aEnabled god mode.");
                Connection c = ICM_SqlHandler.getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `godMode` = FALSE WHERE `playerName` = ?");
                statement.setString(1, player.getName());
                statement.executeUpdate();
            }
            else
            {
                playerMsg(sender, "&aDisabled god mode.");
                Connection c = ICM_SqlHandler.getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `godMode` = FALSE WHERE `playerName` = ?");
                statement.setString(1, player.getName());
                statement.executeUpdate();
            }
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Command_god.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}

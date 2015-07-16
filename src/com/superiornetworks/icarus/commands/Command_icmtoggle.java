package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Settings;
import static com.superiornetworks.icarus.ICM_SqlHandler.getConnection;
import com.superiornetworks.icarus.ICM_Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "icmtoggle", description = "Toggle IcarusMod features.", usage = "/icmtoggle <feature>", rank = ICM_Rank.Rank.SUPER)
public class Command_icmtoggle
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage("§7Possible toggles:\nadminworld");
            return true;
        }
        if (args.length == 1)
        {
            try
            {
                if (args[0].equalsIgnoreCase("adminworld"))
                {
                    if (!ICM_Rank.isRankOrHigher(sender, ICM_Rank.Rank.SENIOR))
                    {
                        sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
                        return true;
                    }
                    Connection c = getConnection();
                    PreparedStatement statement = c.prepareStatement("UPDATE `settings` SET `boolean` = ? WHERE `settingName` = 'adminworld-toggled'");
                    if (ICM_Settings.getBoolean("settingName", "adminworld-toggled", "boolean"))
                    {
                        statement.setBoolean(1, false);
                        statement.executeUpdate();
                        sender.sendMessage("§aAdminworld successfully disabled.");
                        return true;
                    }
                    else
                    {
                        statement.setBoolean(1, true);
                        statement.executeUpdate();
                        sender.sendMessage("§aAdminworld successfully enabled.");
                        return true;
                    }
                }
                else
                {
                    sender.sendMessage("§7Possible toggles:\n adminworld");
                    return true;
                }
            }
            catch (SQLException e)
            {
                Bukkit.getLogger().severe(e.getLocalizedMessage());
            }
        }
        return false;
    }
}

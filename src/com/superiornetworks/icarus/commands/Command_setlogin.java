package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_setlogin extends BukkitCommand
{
    @Override
    public boolean run(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 0)
        {
            return false;
        }
        String combined = StringUtils.join(ArrayUtils.subarray(args, 0, args.length), " ");
        try
        {
            PreparedStatement statement = ICM_SqlHandler.getConnection().prepareStatement("UPDATE `icarus`.`players` SET `loginMessage` = ? WHERE `playerName` = ?");
            statement.setString(2, sender.getName());
            statement.setString(1, combined);
            statement.executeUpdate();
            sender.sendMessage(ChatColor.GREEN + "Success! Your login message is now " + ICM_Utils.colour(combined) + ChatColor.GREEN + "!");
        }
        catch (SQLException ex)
        {
            sender.sendMessage(ChatColor.RED + "Something went wrong... please tell a developer of the IcarusMod!");
            Logger.getLogger(Command_setlogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}

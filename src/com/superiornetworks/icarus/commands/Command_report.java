package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.ChatUtils;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(source = SourceType.ANY)
public class Command_report extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmnd, String string, String[] args)
    {

        if (args.length == 0)
        {
            return false;
        }
        Player player = PlayerUtils.getPlayer(args[0]);
        String Reported;
        if(player != null)
        {
            Reported = player.getName();
        }
        else
        {
            Reported = args[0];
        }
        String report_reason = null;
        if (args.length < 2)
        {
            return false;
        }
        else
        {
            if (args.length >= 2)
            {
                report_reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
            }
        }
        if (player == sender)
        {
            sender.sendMessage(ChatColor.DARK_RED + "For some god awful reason, you have attempted to report yourself... Exactly why you have tried that, nobody knows however what we do know is... Your just damn right stupid... Have a cookie");
            final ItemStack heldItem = new ItemStack(Material.COOKIE);
            final ItemMeta heldItemMeta = heldItem.getItemMeta();
            heldItemMeta.setDisplayName((new StringBuilder()).append(ChatColor.WHITE).append("Idiot of ").append(ChatColor.DARK_GRAY).append("the week award").toString());
            heldItem.setItemMeta(heldItemMeta);
            final int firstEmpty = player.getInventory().firstEmpty();
            player.getInventory().setItem(firstEmpty, heldItem);
            return true;
        }
        for (Player admin : Bukkit.getOnlinePlayers())
        {
            if (ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.SUPER))
            {
                admin.sendMessage(ChatUtils.colorize("&8[&4ICarusMod&8] &a" + sender.getName() + "&4Has reported " + Reported + " - " + player.getAddress().getAddress().getHostAddress() + " &4with the reason &2" + report_reason + "&4."));
            }
        }
        if(player != null)
            player.sendMessage(ChatUtils.colorize("&8[&4IcarusMod&8] &4" + "You have been reported with the following reason: " + "&5" + report_reason + "&4 an adminstrator will review this soon."));
        sender.sendMessage(ChatUtils.colorize("&8[&4IcarusMod&8] &4" + "Your report has been recieved and will be reviewed soon."));

        try
        {
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `reports` (`senderName`, `playerName`, `reportReason`, `ip`) VALUES (?,?,?,?)");
            statement.setString(1, sender.getName());
            statement.setString(2, Reported);
            statement.setString(3, report_reason);
            statement.setString(4, player.getAddress().getAddress().getHostAddress());
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            
        }
        return true;

    }

}

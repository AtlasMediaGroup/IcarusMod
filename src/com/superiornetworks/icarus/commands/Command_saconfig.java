package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "saconfig", description = "Access and change admin information", usage = "/saconfig <list | <add | delete> <username> <rank level>>", rank = ICM_Rank.Rank.OP)
public class Command_saconfig
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("list"))
            {
                try
                {
                    ArrayList<String> sadmins = new ArrayList<>();
                    ArrayList<String> sradmins = new ArrayList<>();
                    ArrayList<String> devs = new ArrayList<>();
                    ArrayList<String> managers = new ArrayList<>();
                    ArrayList<ArrayList<String>> arrays = new ArrayList<>();
                    arrays.add(sadmins);
                    arrays.add(sradmins);
                    arrays.add(devs);
                    arrays.add(managers);
                    for(ArrayList<String> array : arrays)
                    {
                        Collections.sort(array, String.CASE_INSENSITIVE_ORDER);
                    }
                    Connection c = ICM_SqlHandler.getConnection();
                    PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `rank` != 'Op' AND `rank` != 'Non-Op'");
                    ResultSet res = statement.executeQuery();
                    int number = 0;
                    while(res.next())
                    {
                        number++;
                        switch(res.getString("rank"))
                        {
                            case "Super Admin":
                                sadmins.add(res.getString("playerName"));
                                break;
                            case "Senior Admin":
                                sradmins.add(res.getString("playerName"));
                                break;
                            case "Developer":
                                devs.add(res.getString("playerName"));
                                break;
                            case "Manager":
                                managers.add(res.getString("playerName"));
                                break;
                            default:
                                break;
                        }
                    }
                    sender.sendMessage(ChatColor.AQUA + "Superior Freedom Network Admins:");
                    sender.sendMessage(ChatColor.GOLD + "  - Total Admins: " + number);
                    sender.sendMessage(ChatColor.AQUA + "    - Super Admins:");
                    sender.sendMessage("        - " + StringUtils.join(sadmins, ", "));
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "    - Senior Admins:");
                    sender.sendMessage("        - " + StringUtils.join(sradmins, ", "));
                    sender.sendMessage(ChatColor.DARK_PURPLE + "    - Developers:");
                    sender.sendMessage("        - " + StringUtils.join(devs, ", "));
                    sender.sendMessage(ChatColor.GREEN + "    - Managers:");
                    sender.sendMessage("        - " + StringUtils.join(managers, ", "));
                    return true;
                }
                catch(SQLException ex)
                {
                    sender.sendMessage(ChatColor.RED + "An SQL error has occured, please contact a developer!");
                    Logger.getLogger(Command_saconfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return false;
        }
        Player player;
        if(args.length == 2)
        {
            player = Bukkit.getPlayer(args[1]);
            if(player == null)
            {
                sender.sendMessage("Player: " + args[1] + " is not online.");
                return true;
            }
            if(args[0].equalsIgnoreCase("delete"))
            {
                if(ICM_Rank.isRankOrHigher(player, ICM_Rank.getRank(sender)) && sender instanceof Player)
                {
                    sender.sendMessage("You may only remove someone from admin if they are a lower rank than you.");
                    return true;
                }
                ICM_Rank.setRank(player, ICM_Rank.Rank.OP, sender);
                ICM_Utils.adminAction(sender.getName(), "Removing " + player.getName() + " from the admin list.", true);
                return true;
            }
            if(args[0].equalsIgnoreCase("add"))
            {
                if(ICM_Rank.getRank(player).level == -1)
                {
                    try
                    {
                        Connection c = ICM_SqlHandler.getConnection();
                        PreparedStatement statement2 = c.prepareStatement("UPDATE `players` SET `ip` = ? WHERE `playerName` = ?");
                        statement2.setString(1, player.getAddress().getAddress().getHostAddress());
                        statement2.setString(2, player.getName());
                        statement2.executeUpdate();
                    }
                    catch(SQLException ex)
                    {
                        Logger.getLogger(Command_saconfig.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                ICM_Rank.setRank(player, ICM_Rank.Rank.SUPER, sender);
                return true;
            }
            return false;
        }
        if(args.length >= 3)
        {
            if(!args[0].equalsIgnoreCase("add"))
            {
                sender.sendMessage("You only need 2 arguments for a removal.");
                return true;
            }
            player = Bukkit.getPlayer(args[1]);
            if(player == null)
            {
                sender.sendMessage(args[1] + " is not online.");
                return true;
            }
            int level;
            try
            {
                level = Integer.parseInt(args[2]);
            }
            catch(Exception ex)
            {
                String rank = StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ");
                level = ICM_Rank.nameToRank(rank).level;
            }
            if(level == 0)
            {
                sender.sendMessage(StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ") + " is an invalid rank.");
                return true;
            }
            if(!ICM_Rank.isRankOrHigher(sender, level))
            {
                sender.sendMessage(ChatColor.RED + "You may only add someone to a rank that's lower than yours.");
                return true;
            }
            if(ICM_Rank.getRank(player).level == -1)
            {
                try
                {
                    Connection c = ICM_SqlHandler.getConnection();
                    PreparedStatement statement2 = c.prepareStatement("UPDATE `players` SET `ip` = ? WHERE `playerName` = ?");
                    statement2.setString(1, player.getAddress().getAddress().getHostAddress());
                    statement2.setString(2, player.getName());
                    statement2.executeUpdate();
                }
                catch(SQLException ex)
                {
                    Logger.getLogger(Command_saconfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ICM_Rank.setRank(player, ICM_Rank.levelToRank(level), sender);
            return true;
        }
        return false;
    }

}

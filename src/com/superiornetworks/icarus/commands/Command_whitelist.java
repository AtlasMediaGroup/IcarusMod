package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.ICM_Whitelist;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="whitelist",description="Manage whitelist",usage="/whitelist <on : off> | <add : remove> <player>",rank=ICM_Rank.Rank.SUPER)
public class Command_whitelist
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length < 1)
        {
            return false;
        }
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("on"))
            {
                ICM_Whitelist.whitelist = true;
                ICM_Utils.playerMsg(sender, "&aWhitelist turned on.");
                return true;
            }
            if(args[0].equalsIgnoreCase("off"))
            {
                ICM_Whitelist.whitelist = false;
                ICM_Utils.playerMsg(sender, "&cWhitelist turned off.");
                return true;
            }
        }
        if(args.length == 2)
        {
            try{
                Player player = Bukkit.getPlayer(args[1]);
                String playerName = args[1];
                if(player != null)
                {
                    playerName = player.getName();
                }
                if(!ICM_SqlHandler.playerExists(playerName))
                {
                    ICM_Utils.playerMsg(sender, "&dPlayer could not be found in database.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("add"))
                {
                    ICM_Utils.adminAction(sender.getName(), "Adding " + playerName + " to the whitelist.", false);
                    ICM_Whitelist.addToWhitelist(playerName);
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove"))
                {
                    ICM_Utils.adminAction(sender.getName(), "Removing " + playerName + " from the whitelist.", true);
                    ICM_Whitelist.removeFromWhitelist(playerName);
                    return true;
                }
                return false;
            }
            catch (SQLException ex){
                Logger.getLogger(Command_whitelist.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}

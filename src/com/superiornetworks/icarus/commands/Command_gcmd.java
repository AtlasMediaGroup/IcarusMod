package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="gcmd",description="Make someone run a command!",usage="/gcmd <player> <command>",rank=ICM_Rank.Rank.SENIOR)
public class Command_gcmd extends BukkitCommand {
    
    @Override
    public boolean run(CommandSender sender, Command cmnd, String string, String[] args)
    {
    
        Player p = (Player) sender;
        if (!ICM_Rank.isRankOrHigher(p, ICM_Rank.Rank.SENIOR))
         {
             p.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
         }
        
     if (sender instanceof Player)
            {

                    if (args.length < 2)
                    {
                        return false;
                    }
                    
                    Player target = Bukkit.getServer().getPlayer(args[0]);

                    String outcommand = "";
                    try
                    {
                        StringBuilder outcommand_bldr = new StringBuilder();
                        for (int i = 1; i < args.length; i++)
                        {
                            outcommand_bldr.append(args[i]).append(" ");
                        }
                        outcommand = outcommand_bldr.toString().trim();
                    }
                    catch (Throwable ex)
                    {
                        sender.sendMessage(ChatColor.GRAY + "Error building command: " + ex.getMessage());
                        return true;
                    }

                    try
                    {
                        p.sendMessage("Sending command as " + target.getName() + ": " + outcommand);
                        if (Bukkit.getServer().dispatchCommand(target, outcommand))
                        {
                            p.sendMessage("Command sent.");
                        }
                        else
                        {
                            p.sendMessage("Unknown error sending command.");
                        }
                    }
                    catch (Throwable ex)
                    {
                        p.sendMessage("Error sending command: " + ex.getMessage());
                    }
            
                    return true;
                }

            
            else
            {
            if (args.length < 2)
                {
                    return false;
                }

                Player target = Bukkit.getServer().getPlayer(args[0]);

                String outcommand = "";
                try
                {
                    StringBuilder outcommand_bldr = new StringBuilder();
                    for (int i = 1; i < args.length; i++)
                    {
                        outcommand_bldr.append(args[i]).append(" ");
                    }
                    outcommand = outcommand_bldr.toString().trim();
                }
                catch (Throwable ex)
                {
                    sender.sendMessage(ChatColor.GRAY + "Error building command: " + ex.getMessage());
                    return true;
                }

                try
                {
                    sender.sendMessage("Sending command as " + target.getName() + ": " + outcommand);
                    if (Bukkit.getServer().dispatchCommand(target, outcommand))
                    {
                        sender.sendMessage("Command sent.");
                    }
                    else
                    {
                        sender.sendMessage("Unknown error sending command.");
                    }
                }
                catch (Throwable ex)
                {
                    sender.sendMessage("Error sending command: " + ex.getMessage());
                }
        
                return true;
            }     
}
}

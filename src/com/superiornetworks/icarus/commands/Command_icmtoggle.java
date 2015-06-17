package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "icmtoggle", description = "Toggle IcarusMod features.", usage = "/toggle <feature>", rank = ICM_Rank.Rank.MANAGER)
public class Command_icmtoggle
    {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
        {
        if (args.length == 0)
            {
            playerMsg(sender, ChatColor.AQUA + "Possible toggles:");
            for (String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
                {
                playerMsg(sender, " - " + (IcarusMod.config.getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
                }
            return true;
            }
        if (args.length == 1)
            {
            for (String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
                {
                if (args[0].equalsIgnoreCase(toggle))
                    {
                    IcarusMod.config.set("toggles." + toggle, !IcarusMod.config.getBoolean("toggles." + toggle));
                    playerMsg(sender, ChatColor.GOLD + "Toggled " + toggle + (IcarusMod.config.getBoolean("toggles." + toggle) ? " on." : " off."));
                    return true;
                    }
                }
            playerMsg(sender, ChatColor.AQUA + "Possible toggles:");
            for (String toggle : IcarusMod.config.getConfigurationSection("toggles").getKeys(false))
                {
                playerMsg(sender, " - " + (IcarusMod.config.getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
                }
            return true;
            }
        return false;
        }
    }

package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import com.superiornetworks.icarus.IcarusMod;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "")
public class Command_icmtoggle extends BukkitCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!ICM_Rank.isRankOrHigher(player, ICM_Rank.Rank.MANAGER))
            {
                playerMsg(sender, ICM_Utils.NO_PERMS_MESSAGE);
                return true;
            }
        }
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

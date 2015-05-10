package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.modules.BusySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="busy",description="Toggle busy mode.",usage="/busy",rank=ICM_Rank.Rank.SUPER)
public class Command_busy
{
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args)
    {

        Player player = (Player) sender;
        BusySystem.toggleBusyPlayer(player);
        return true;
    }
}

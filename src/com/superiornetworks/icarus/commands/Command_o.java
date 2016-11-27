package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.modules.AdminChat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//This will likely be changed to a better admin chat method reminiscent of the FOPM: R's
@CommandParameters(name = "p", description = "Send a message to admin chat.", usage = "/o <message>", rank = ICM_Rank.Rank.SUPER)
public class Command_o
{

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        AdminChat.chat(sender, StringUtils.join(args, " "));
        return true;
    }
}

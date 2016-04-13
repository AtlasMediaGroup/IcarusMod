package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_SqlHandler;
import static com.superiornetworks.icarus.ICM_Utils.playerMsg;
import com.superiornetworks.icarus.modules.AdminChat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "o", description = "Toggle or use adminchat.", usage = "/o [message]", rank = ICM_Rank.Rank.SUPER)
public class Command_o {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            try {
                if (!(sender instanceof Player)) {
                    return true;
                }
                Player player = (Player) sender;
                if (!ICM_SqlHandler.inAC(player.getName())) {
                    playerMsg(sender, "&7Toggling adminchat: On");
                    Connection c = ICM_SqlHandler.getConnection();
                    PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `commandSpyOn` = TRUE WHERE `playerName` = ?");
                    statement.setString(1, player.getName());
                    statement.executeUpdate();
                } else {
                    playerMsg(sender, "&7Toggling adminchat: Off");
                    Connection c = ICM_SqlHandler.getConnection();
                    PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `commandSpyOn` = FALSE WHERE `playerName` = ?");
                    statement.setString(1, player.getName());
                    statement.executeUpdate();
                }
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Command_o.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }

        if (args.length >= 1) {
            AdminChat.AdminChatMsg(sender, StringUtils.join(args, " "));
            return true;
        }

        return true;
    }
}

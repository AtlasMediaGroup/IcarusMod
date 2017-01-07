package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ICM_PanelLogger
{
    public enum MessageType
    {
        CMD, CHAT, SRVMSG, CONNECT, DISCONNECT
    }
    
    public static void log(MessageType type, String sender, String message)
    {
        try
        {
            Connection c = ICM_SqlHandler.getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `panellog` (`server`, `time`, `username`, `action`, `value`) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, IcarusMod.config.getString("serveridentifier"));
            statement.setLong(2, System.currentTimeMillis());
            statement.setString(3, sender);
            statement.setString(4, type.toString());
            statement.setString(5, message);
            statement.executeUpdate();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(ICM_PanelLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

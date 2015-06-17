package com.superiornetworks.icarus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ICM_Whitelist
    {

    public static boolean whitelist = false;

    public static boolean isWhitelisted(String player)
        {
        try
            {
            Object obj = ICM_SqlHandler.getFromTable("playerName", player, "whitelisted", "players");
            if (obj instanceof Boolean)
                {
                return (Boolean) obj;
                }
            return false;
            }
        catch (SQLException ex)
            {
            Logger.getLogger(ICM_Whitelist.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            }
        }

    public static void addToWhitelist(String player) throws SQLException
        {
        Connection c = ICM_SqlHandler.getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `whitelisted` = TRUE WHERE `playerName` = ?");
        statement.setString(1, player);
        statement.executeUpdate();
        }

    public static void removeFromWhitelist(String player) throws SQLException
        {
        Connection c = ICM_SqlHandler.getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `whitelisted` = FALSE WHERE `playerName` = ?");
        statement.setString(1, player);
        statement.executeUpdate();
        }
    }

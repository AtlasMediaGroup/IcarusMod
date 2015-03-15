package com.superiornetworks.icarus;

import static com.superiornetworks.icarus.IcarusMod.mySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ICM_SqlHandler
{

    public static void updateDatabase(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate(SQLquery);
    }

    public static void getValueFromDB(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        ResultSet res = statement.executeQuery(SQLquery);
        res.next();
    }
    
    public static boolean playerExists(String playerName) throws SQLException
    {
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `playerName` = '?'");
        statement.setString(1, playerName);
        ResultSet res = statement.executeQuery();
        res.next();
        return res.getString("playerName") != null;
    }
    
    public static void generateNewPlayer(String playerName) throws SQLException
    {
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("INSERT INTO `players` (`playerName`, `loginMessage`, `rank`, `godMode`, `doomHammer`, `commandSpy`) VALUES ('?', '', 'Op', '0', '0', '0')");
        statement.setString(1, playerName);
        statement.executeUpdate();
    }
    
    public static String getLoginMessage(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return null;
        }
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `playerName` = '?'");
        statement.setString(1, playerName);
        ResultSet res = statement.executeQuery();
        res.next();
        if(res.getString("loginMessage") == null)
        {
            return null;
        }
        return res.getString("loginMessage");
    }
    
    public static String getRank(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return "Op";
        }
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `playerName` = '?'");
        statement.setString(1, playerName);
        ResultSet res = statement.executeQuery();
        res.next();
        if(res.getString("rank") == null)
        {
            return "Op";
        }
        else
        {
            return res.getString("rank");
        }
    }
    
    public static boolean hasDoomHammer(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return false;
        }
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `playerName` = '?'");
        statement.setString(1, playerName);
        ResultSet res = statement.executeQuery();
        res.next();
        return res.getBoolean("doomHammer");
    }
    
    public static boolean isGod(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return false;
        }
        Connection c = mySQL.openConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `players` WHERE `playerName` = '?'");
        statement.setString(1, playerName);
        ResultSet res = statement.executeQuery();
        res.next();
        return res.getBoolean("godMod");
    }

}

package com.superiornetworks.icarus;

import static com.superiornetworks.icarus.IcarusMod.mySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;

public class ICM_SqlHandler
    {

    public static Connection getConnection()
    {
        if (mySQL.checkConnection())
        {
            return mySQL.getConnection();
        }
        return mySQL.openConnection();
    }

    public static void generateTables() throws SQLException
    {
        Connection c = mySQL.openConnection();
        String players = "CREATE TABLE IF NOT EXISTS `players` ("
                         + "`id` INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                         + "`playerName` VARCHAR(16) NOT NULL UNIQUE,"
                         + "`nick` TEXT,"
                         + "`tag` TEXT,"
                         + "`loginMessage` TEXT,"
                         + "`rank` TEXT,"
                         + "`ip` VARCHAR(64),"
                         + "`godMode` BOOLEAN,"
                         + "`doomHammer` BOOLEAN,"
                         + "`whitelisted` BOOLEAN"
                         + ")";
        String reports = "CREATE TABLE IF NOT EXISTS `reports` ("
                         + "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                         + "`senderName` VARCHAR(16) NOT NULL,"
                         + "`playerName` VARCHAR(16) NOT NULL,"
                         + "`reportReason` TEXT,"
                         + "`ip` VARCHAR(64),"
                         + "`time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                         + ")";
        String bans = "CREATE TABLE IF NOT EXISTS `bans` ("
                      + "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                      + "`senderName` VARCHAR(16) NOT NULL,"
                      + "`playerName` VARCHAR(16) NOT NULL UNIQUE,"
                      + "`banReason` TEXT,"
                      + "`ip` VARCHAR(64),"
                      + "`time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                      + ")";
        String commands = "CREATE TABLE IF NOT EXISTS `commands` ("
                          + "`commandName` VARCHAR(64) NOT NULL UNIQUE,"
                          + "`level` INTEGER NOT NULL,"
                          + "`message` TEXT NOT NULL,"
                          + "`kick` BOOLEAN"
                          + ")";
        String settings = "CREATE TABLE IF NOT EXISTS `settings` ("
                          + "`settingName` VARCHAR(64) NOT NULL UNIQUE,"
                          + "`int` INTEGER,"
                          + "`string` TEXT,"
                          + "`boolean` BOOLEAN"
                          + ")";
        c.createStatement().execute(players);
        c.createStatement().execute(reports);
        c.createStatement().execute(bans);
        c.createStatement().execute(commands);
        c.createStatement().execute(settings);
    }

    public static void generateNewPlayer(Player player) throws SQLException
    {
        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("INSERT INTO `players` (`playerName`, `loginMessage`, `rank`, `ip`, `godMode`, `doomHammer`, `whitelisted`) VALUES (?, '', 'Op', ?, '0', '0', FALSE)");
        statement.setString(1, player.getName());
        statement.setString(2, player.getAddress().getAddress().getHostAddress());
        statement.executeUpdate();
    }

    public static Object getFromTable(String uniqueColumn, String uniqueValue, String lookingFor, String inTable) throws SQLException
    {
        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `" + inTable + "` WHERE `" + uniqueColumn + "` = ?");
        statement.setString(1, uniqueValue);
        ResultSet res = statement.executeQuery();
        if (res.next())
        {
            return res.getObject(lookingFor);
        }
        return null;
    }

    public static boolean playerExists(String playerName) throws SQLException
    {
        return getFromTable("playerName", playerName, "playerName", "players") != null;
    }

    public static String getIp(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return null;
        }
        Object obj = getFromTable("playerName", playerName, "ip", "players");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static String getLoginMessage(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return null;
        }
        Object obj = getFromTable("playerName", playerName, "loginMessage", "players");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static String getRank(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return "Op";
        }
        Object obj = getFromTable("playerName", playerName, "rank", "players");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return "Op";
    }

    public static String getTag(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return "&7[&c" + getRank(playerName) + "&7]";
        }
        Object obj = getFromTable("playerName", playerName, "tag", "players");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return "&7[&c" + getRank(playerName) + "&7]";
    }

    public static String getNick(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return playerName;
        }
        Object obj = getFromTable("playerName", playerName, "nick", "players");
        if (obj instanceof String)
        {
            return (String) obj;
        }
        return playerName;
    }

    public static boolean hasDoomHammer(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return false;
        }
        Object obj = getFromTable("playerName", playerName, "doomHammer", "players");
        if (obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        return false;
    }

    public static boolean isGod(String playerName) throws SQLException
    {
        if (!playerExists(playerName))
        {
            return false;
        }
        Object obj = getFromTable("playerName", playerName, "godMode", "players");
        if (obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        return false;
    }

    public static void setNickname(String playerName, String nickname) throws SQLException
    {
        //For sake of simplicity, this method will replace & with §, so players can also have colors in their nicks.
        if (!playerExists(playerName))
        {
            return;
        }

        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `nick` = ? WHERE `playerName` = ?");
        statement.setString(1, nickname.replaceAll("&", "§"));
        statement.setString(2, playerName);
        statement.executeUpdate();
    }

    public static void setTag(String playerName, String tag) throws SQLException
    {
        //For sake of simplicity, this method will replace & with §, so players can also have colors in their nicks.
        if (!playerExists(playerName))
        {
            return;
        }

        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `tag` = ? WHERE `playerName` = ?");
        statement.setString(1, tag.replaceAll("&", "§"));
        statement.setString(2, playerName);
        statement.executeUpdate();
    }
    }

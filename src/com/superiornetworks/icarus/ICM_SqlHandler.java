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
        if(mySQL.checkConnection())
        {
            return mySQL.getConnection();
        }
        return mySQL.openConnection();
    }

    public static void generateTables() throws SQLException
    {
        Connection c = getConnection();
        String players = "CREATE TABLE IF NOT EXISTS `players` ("
                + "`id` INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + "`playerName` VARCHAR(16) NOT NULL UNIQUE,"
                + "`nick` TEXT,"
                + "`tag` TEXT,"
                + "`loginMessage` TEXT,"
                + "`rank` TEXT,"
                + "`ip` VARCHAR(64),"
                + "`commandSpyOn` BOOLEAN,"
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
                + "`time` BIGINT,"
                + "`bantime` BIGINT,"
                + "`perm` BOOLEAN"
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
        String panellog = "CREATE TABLE IF NOT EXISTS `panellog` ("
                + "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + "`server` VARCHAR(16),"
                + "`time` BIGINT,"
                + "`username` VARCHAR(16),"
                + "`action` VARCHAR(16),"
                + "`value` VARCHAR(128)"
                + ")";
        String onlineplayers = "CREATE TABLE IF NOT EXISTS `onlineplayers` ("
                + "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + "`server` VARCHAR(16),"
                + "`player` VARCHAR(16),"
                + "`jointime` BIGINT"
                + ")";
        String serverdetails = "CREATE TABLE IF NOT EXISTS `serverdetails` ("
                + "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + "`server` VARCHAR(16),"
                + "`ram` BIGINT,"
                + "`tps` DOUBLE,"
                + "`time` BIGINT"
                + ")";
        c.prepareStatement(players).executeUpdate();
        c.prepareStatement(reports).executeUpdate();
        c.prepareStatement(bans).executeUpdate();
        c.prepareStatement(commands).executeUpdate();
        c.prepareStatement(serverdetails).executeUpdate();
        c.prepareStatement(panellog).executeUpdate();
        c.prepareStatement(onlineplayers).executeUpdate();
        c.prepareStatement(settings).executeUpdate();
    }

    public static void generateNewPlayer(Player player, String host) throws SQLException
    {
        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("INSERT INTO `players` (`playerName`, `loginMessage`, `rank`, `ip`, `godMode`, `doomHammer`, `whitelisted`) VALUES (?, '', 'Op', ?, '0', '0', 0)");
        statement.setString(1, player.getName());
        statement.setString(2, host);
        statement.executeUpdate();
    }

    public static Object getFromTable(String uniqueColumn, String uniqueValue, String lookingFor, String inTable) throws SQLException
    {
        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM `" + inTable + "` WHERE `" + uniqueColumn + "` = ?");
        statement.setString(1, uniqueValue);
        ResultSet res = statement.executeQuery();
        if(res.next())
        {
            return res.getObject(lookingFor);
        }
        return null;
    }

    public static boolean updateInTable(String uniqueColumn, String uniqueValue, Object newValue, String columnToChange, String table) throws SQLException
    {
        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE " + table + " SET " + columnToChange + " = ? WHERE " + uniqueColumn + " = ?");
        statement.setObject(1, newValue);
        statement.setString(2, uniqueValue);
        int i = statement.executeUpdate();
        c.commit();
        return i > 0;
    }

    public static boolean playerExists(String playerName) throws SQLException
    {
        return getFromTable("playerName", playerName, "playerName", "players") != null;
    }

    public static String getIp(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return null;
        }
        Object obj = getFromTable("playerName", playerName, "ip", "players");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static String getLoginMessage(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return null;
        }
        Object obj = getFromTable("playerName", playerName, "loginMessage", "players");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static String getRank(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return "Op";
        }
        Object obj = getFromTable("playerName", playerName, "rank", "players");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return "Op";
    }

    public static String getTag(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return "&7[&c" + getRank(playerName) + "&7]";
        }
        Object obj = getFromTable("playerName", playerName, "tag", "players");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static String getNick(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return playerName;
        }
        Object obj = getFromTable("playerName", playerName, "nick", "players");
        if(obj instanceof String)
        {
            return (String) obj;
        }
        return null;
    }

    public static boolean hasDoomHammer(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return false;
        }
        Object obj = getFromTable("playerName", playerName, "doomHammer", "players");
        if(obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        return false;
    }

    public static boolean isGod(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return false;
        }
        Object obj = getFromTable("playerName", playerName, "godMode", "players");
        if(obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        return false;
    }
    
    public static boolean inAC(String playerName) throws SQLException
    {
        if(!playerExists(playerName))
        {
            return false;
        }
        Object obj = getFromTable("playerName", playerName, "commandSpyOn", "players");
        if(obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        return false;
    }

    public static void setNickname(String playerName, String nickname) throws SQLException
    {
        //No need to replace & with § as the ICM_Utils.colour method handles this.
        if(!playerExists(playerName))
        {
            return;
        }

        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `nick` = ? WHERE `playerName` = ?");
        statement.setString(1, nickname);
        statement.setString(2, playerName);
        statement.executeUpdate();

        ICM_Rank.nicks.clear();
    }

    public static void setTag(String playerName, String tag) throws SQLException
    {
        //No need to replace & with § as the ICM_Utils.colour method handles this.
        if(!playerExists(playerName))
        {
            return;
        }

        Connection c = getConnection();
        PreparedStatement statement = c.prepareStatement("UPDATE `players` SET `tag` = ? WHERE `playerName` = ?");
        statement.setString(1, tag);
        statement.setString(2, playerName);
        statement.executeUpdate();

        ICM_Rank.tags.clear();
    }
}

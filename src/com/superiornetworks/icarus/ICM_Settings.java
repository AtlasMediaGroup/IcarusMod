package com.superiornetworks.icarus;

import static com.superiornetworks.icarus.ICM_SqlHandler.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ICM_Settings
    {

    public static boolean settingExists(String settingName) throws SQLException
        {
        return ICM_SqlHandler.getFromTable("settingName", settingName, "settingName", "settings") != null;
        }

    public static void generateDefaultSettings() throws SQLException
        {
        //This method does not override existing settings, it only creates the settings if they do not exist.

        if (!settingExists("title-message-on-join"))
            {
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `settings` (`settingName`, `int`, `boolean`, `string`) VALUES ('title-message-on-join', NULL, NULL, '§6§lWelcome back!')");
            statement.executeUpdate();
            }
        if (!settingExists("subtitle-message-on-join"))
            {
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `settings` (`settingName`, `int`, `boolean`, `string`) VALUES ('subtitle-message-on-join', NULL, NULL, '§f§lWe hope you enjoy your stay.')");
            statement.executeUpdate();
            }
        if (!settingExists("tab-header"))
            {
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `settings` (`settingName`, `int`, `boolean`, `string`) VALUES ('tab-header', NULL, NULL, '§f&lThe Freedom Network')");
            statement.executeUpdate();
            }
        if (!settingExists("news")) //This will be used in the footer.
            {
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO `settings` (`settingName`, `int`, `boolean`, `string`) VALUES ('news', NULL, NULL, '§fNo news at the moment!')");
            statement.executeUpdate();
            }
        //To add: network-wide lockdown, whitelist, adminmode, and more.
        }

    //The reason we have these methods is so that we do not have to run instanceof checks every time a setting is checked.
    public static String getString(String uniqueColumn, String uniqueValue, String lookingFor) throws SQLException
        {
        Object obj = ICM_SqlHandler.getFromTable(uniqueColumn, uniqueValue, lookingFor, "settings");
        if (obj instanceof String)
            {
            return (String) obj;
            }
        return "No string found. Please contact a developer.";
        }

    public static Integer getInt(String uniqueColumn, String uniqueValue, String lookingFor) throws SQLException
        {
        Object obj = ICM_SqlHandler.getFromTable(uniqueColumn, uniqueValue, lookingFor, "settings");
        if (obj instanceof Integer)
            {
            return (Integer) obj;
            }
        return 0;
        }

    public static boolean getBoolean(String uniqueColumn, String uniqueValue, String lookingFor) throws SQLException
        {
        Object obj = ICM_SqlHandler.getFromTable(uniqueColumn, uniqueValue, lookingFor, "settings");
        if (obj instanceof Boolean)
            {
            return (Boolean) obj;
            }
        return false;
        }

    public static void updateSetting(String settingName, Object obj) throws SQLException
        {
        if (obj instanceof String)
            {
            String string = (String) obj;
            if (!settingExists(settingName))
                {
                return;
                }
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE `settings` SET `string` = ? WHERE `settingName` = ?");
            statement.setString(1, string);
            statement.setString(2, settingName);
            }
        else if (obj instanceof Boolean)
            {
            Boolean bool = (Boolean) obj; //If you name it boolean, Java recognizes it as the boolean class, not a variable.
            if (!settingExists(settingName))
                {
                return;
                }
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE `settings` SET `boolean` = ? WHERE `settingName` = ?");
            statement.setBoolean(1, bool);
            statement.setString(2, settingName);
            }
        else if (obj instanceof Integer)
            {
            Integer integer = (Integer) obj; //Same goes for this.
            if (!settingExists(settingName))
                {
                return;
                }
            Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE `settings` SET `int` = ? WHERE `settingName` = ?");
            statement.setInt(1, integer);
            statement.setString(2, settingName);
            }
        }
    }

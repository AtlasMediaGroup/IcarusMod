package com.superiornetworks.icarus;

import static com.superiornetworks.icarus.IcarusMod.mySQL;
import java.sql.Connection;
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

    public void getValueFromDB(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        ResultSet res = statement.executeQuery(SQLquery);
        res.next();
    }

}

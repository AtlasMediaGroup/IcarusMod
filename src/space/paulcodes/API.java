package space.paulcodes;

import com.superiornetworks.icarus.ICM_SqlHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import space.paulcodes.otherapis.TabAPI;
import java.sql.SQLException;

public class API 
{
    public static ChatColor checkPlayerColor(Player p) 
    {
        try 
        {
            switch (ICM_SqlHandler.getRank(p.getName())) 
            {
                case "Super Admin":
                    return ChatColor.AQUA;
                case "Telnet Admin":
                    return ChatColor.DARK_GREEN;
                case "Senior Admin":
                    return ChatColor.LIGHT_PURPLE;
                case "Developer":
                    return ChatColor.DARK_PURPLE;
                case "Manager":
                    return ChatColor.GREEN;
                default:
                    return ChatColor.GRAY;
            }
        }
        catch(SQLException ex) 
        {
            ex.printStackTrace();
        }
        return ChatColor.DARK_GRAY;
    }

    public static void setRankColor(Player p) 
    {
        TabAPI.setPlayerTab(checkPlayerColor(p), p);
    }
}

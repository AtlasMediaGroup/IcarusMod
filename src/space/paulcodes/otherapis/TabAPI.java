package space.paulcodes.otherapis;

import org.bukkit.entity.Player;

public class TabAPI
    {

    public static void setPlayerTab(String prefix, Player p)
        {
        p.setCustomName(prefix + p.getName());
        p.setPlayerListName(p.getCustomName());
        }

    }

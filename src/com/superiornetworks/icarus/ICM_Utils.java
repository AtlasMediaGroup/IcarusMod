package com.superiornetworks.icarus;

import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;

public class ICM_Utils
{

    public static final List<String> MANAGERS = Arrays.asList("wild1145", "Camzie99", "Hockeyfan360");
    public static final List<String> DEVELOPERS = Arrays.asList("wild1145", "Camzie99", "Hockeyfan360");

    public static String decolorize(String string)
    {
        return string.replaceAll("\\u00A7(?=[0-9a-fk-or])", "&");
    }

    public static String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}

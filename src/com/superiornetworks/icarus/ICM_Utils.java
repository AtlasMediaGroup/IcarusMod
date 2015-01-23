package com.superiornetworks.icarus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;

public class ICM_Utils
{

    public static final List<String> MANAGERS = Arrays.asList("wild1145", "Camzie99");
    public static final List<String> COMMUNITYTEAM = Arrays.asList("");
    public static final List<String> DEVELOPERS = Arrays.asList("wild1145", "Camzie99", "Hockeyfan360");
    
    //Want to migrate to custom playerdata.
    public static ArrayList<String> DOOMHAMMERS = new ArrayList<>();
    public static ArrayList<String> GOD = new ArrayList<>();
    
    public static final String NO_PERMS_MESSAGE = (ChatColor.DARK_RED + "Sorry, you do not have the required permissions to access this command. If you feel this is in error, please contact a server manager or developer ASAP!");

}

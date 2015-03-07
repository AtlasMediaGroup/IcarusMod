package com.superiornetworks.icarus.bridge;

import net.coreprotect.CoreProtectAPI;

public class CoreProtect
{

    private static CoreProtectAPI getCoreProtect()
    {
        CoreProtectAPI CoreProtect = CoreProtectAPI.plugin.getAPI();
        return CoreProtect;
    }

    public static void rollback(String user, int time, int radius)
    {
        getCoreProtect().performRollback(user, time, radius, null, null, null);
    }

    public static void restore(String user, int time, int radius)
    {
        getCoreProtect().performRestore(user, time, radius, null, null, null);
    }

}

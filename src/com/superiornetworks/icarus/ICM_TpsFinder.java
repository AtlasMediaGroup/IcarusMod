package com.superiornetworks.icarus;

import org.bukkit.scheduler.BukkitRunnable;

public class ICM_TpsFinder extends BukkitRunnable
{

    public static int tickcount = 0;
    public static long[] ticks = new long[600];
    public static long lasttick = 0L;

    public static double getTPS()
    {
        return getTPS(100);
    }

    public static double getTPS(int tickamount)
    {
        if(tickcount < tickamount)
        {
            return 20.0D;
        }
        int target = (tickcount - 1 - tickamount) % ticks.length;
        long elapsed = System.currentTimeMillis() - ticks[target];

        return tickamount / (elapsed / 1000.0D);
    }

    public static long getElapsed(int tickID)
    {
        if(tickcount - tickID >= ticks.length)
        {
            
        }

        long time = ticks[(tickID % ticks.length)];
        return System.currentTimeMillis() - time;
    }

    @Override
    public void run()
    {
        ticks[(tickcount % ticks.length)] = System.currentTimeMillis();

        tickcount += 1;
    }
}

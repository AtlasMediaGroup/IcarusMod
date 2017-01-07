package com.superiornetworks.icarus;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.bukkit.scheduler.BukkitRunnable;

public class ICM_LoggerHandler extends Handler
{

    @Override
    public void publish(final LogRecord record)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                ICM_PanelLogger.log(ICM_PanelLogger.MessageType.SRVMSG, "SRVMSG", record.getMessage());
            }
        }.runTaskAsynchronously(IcarusMod.plugin);
    }

    @Override
    public void flush()
    {

    }

    @Override
    public void close() throws SecurityException
    {

    }

}

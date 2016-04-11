package com.superiornetworks.icarus;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ICM_LoggerHandler extends Handler
{

    @Override
    public void publish(LogRecord record)
    {
        ICM_PanelLogger.log(ICM_PanelLogger.MessageType.SRVMSG, "SRVMSG", record.getMessage());
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

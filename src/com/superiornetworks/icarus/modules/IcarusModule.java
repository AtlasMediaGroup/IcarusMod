package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Server;

public class IcarusModule
    {

    protected final IcarusMod plugin;
    protected final Server server;

    public IcarusModule(IcarusMod plugin)
        {
        this.plugin = plugin;
        this.server = plugin.getServer();
        }
    }

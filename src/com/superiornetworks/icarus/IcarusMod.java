package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.ICM_CommandRegistry;
import com.superiornetworks.icarus.listeners.PlayerListener;
import com.superiornetworks.icarus.modules.*;
import java.sql.SQLException;
import me.husky.mysql.MySQL;
import net.pravian.aero.command.handler.AeroCommandHandler;
import net.pravian.aero.plugin.AeroPlugin;
import net.pravian.aero.util.Loggers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public class IcarusMod extends AeroPlugin<IcarusMod>
{

    public static IcarusMod plugin;
    
    public static AeroCommandHandler handler;

    // MySQL  
    public static MySQL mySQL;

    // YAML File Information
    public static ICM_Config icmconfig;
    public static FileConfiguration config;

    // Module Information
    public FamousWarning famousWarning;
    public BusySystem busySystem;
    public CreativePVP creativePVP;
    public DoomHammer doomHammer;
    public AdminWorldToggle adminWorldToggle;
    public DevelopmentMode developmentMode;
    public ChatModule chatModule;
    public JoinModule joinModule;
    public CommandBlockModule commandBlockModule;
    public CommandSpyModule commandSpyModule;
    public ImposterModule imposterModule;
    public BlockControl blockControl;

    @Override
    public void load()
    {
        plugin = this;

        // Module Loading
        famousWarning = new FamousWarning(plugin);
        busySystem = new BusySystem(plugin);
        creativePVP = new CreativePVP(plugin);
        doomHammer = new DoomHammer(plugin);
        adminWorldToggle = new AdminWorldToggle(plugin);
        developmentMode = new DevelopmentMode(plugin);
        chatModule = new ChatModule(plugin);
        joinModule = new JoinModule(plugin);
        commandBlockModule = new CommandBlockModule(plugin);
        commandSpyModule = new CommandSpyModule(plugin);
        imposterModule = new ImposterModule(plugin);
        blockControl = new BlockControl(plugin);
    }

    @Override
    public void enable()
    {
        IcarusMod.plugin = this;
        
        //Handles logs from the server
        Bukkit.getServer().getLogger().addHandler(new ICM_LoggerHandler());
        
        /*//Command Registration Stuff
        handler = new SimpleCommandHandler(plugin);
        handler.setCommandClassPrefix("Command_");
        handler.loadFrom(Command_say.class.getPackage());
        handler.registerAll();*/

        // More YAML Setting Up and information.
        icmconfig = new ICM_Config(plugin, "config.yml");
        icmconfig.saveDefaultConfig();
        config = icmconfig.getConfig();

        boolean error = false;
        if (config.getString("hostname") == null)
        {
            Loggers.severe(plugin, "Hostname is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("port") == null)
        {
            Loggers.severe(plugin, "Port is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("database") == null)
        {
            Loggers.severe(plugin, "Database is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("username") == null)
        {
            Loggers.severe(plugin, "Username is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("password") == null)
        {
            Loggers.severe(plugin, "Password is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }

        final PluginManager pm = plugin.getServer().getPluginManager();
        if (!error)
        {
            // Listeners
            pm.registerEvents(new PlayerListener(plugin), plugin);

            // MySQL Stuffs
            //Create MySQL
            mySQL = new MySQL(plugin, config.getString("hostname"), config.getString("port"), config.getString("database"), config.getString("username"), config.getString("password"));
            try
            {
                //Generate Default Tables
                ICM_SqlHandler.generateTables();
                ICM_Settings.generateDefaultSettings();
            }
            catch (SQLException ex)
            {
                plugin.getLogger().severe(ex.getLocalizedMessage());
            }

            //Enable Commands
            ICM_CommandRegistry.registerCommands();

            // The All Clear
            Loggers.info(plugin, "has been enabled with no problems.");
        }
        else
        {
            Bukkit.broadcastMessage("IcarusMod had an issue loading up, please check your logs for more info, on first start, this is normal!");
            pm.disablePlugin(plugin);
        }

    }

    @Override
    public void disable()
    {
        IcarusMod.plugin = null;
        // All clear, its disabled! Woot
        Loggers.info(plugin, "has been disabled with no problems.");
    }
}

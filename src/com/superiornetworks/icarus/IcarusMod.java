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
    
    public static ICM_CommandRegistry registry;

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
        //Creates a reference to a plugin instance
        IcarusMod.plugin = this;

        // More YAML Setting Up and information.
        icmconfig = new ICM_Config(plugin, "config.yml");
        icmconfig.saveDefaultConfig();
        config = icmconfig.getConfig();

        //Check 
        boolean error = false;
        if (config.getString("hostname") == null || config.getString("hostname").equalsIgnoreCase(""))
        {
            Loggers.severe(plugin, "Hostname is null in the config, please stop the server, amend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("port") == null || config.getString("port").equalsIgnoreCase(""))
        {
            Loggers.severe(plugin, "Port is null in the config, please stop the server, amend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("database") == null || config.getString("database").equalsIgnoreCase(""))
        {
            Loggers.severe(plugin, "Database is null in the config, please stop the server, amend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("username") == null || config.getString("username").equalsIgnoreCase(""))
        {
            Loggers.severe(plugin, "Username is null in the config, please stop the server, amend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("password") == null || config.getString("password").equalsIgnoreCase(""))
        {
            Loggers.severe(plugin, "Password is null in the config, please stop the server, amend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        
        final PluginManager pm = plugin.getServer().getPluginManager();
        if (!error)
        {
            // Listeners
            pm.registerEvents(new PlayerListener(plugin), plugin);

            //Handling MySQL
            //Create MySQL
            mySQL = new MySQL(plugin, config.getString("hostname"), config.getString("port"), config.getString("database"), config.getString("username"), config.getString("password"));
            try
            {
                //Generate Default Tables
                Bukkit.broadcastMessage("Generating all required tables.");
                ICM_SqlHandler.generateTables();
                ICM_Settings.generateDefaultSettings();
            }
            catch (SQLException ex)
            {
                plugin.getLogger().severe(ex.getLocalizedMessage());
            }

            //Enable Commands
            registry = new ICM_CommandRegistry();
            
            //Handles logs from the server
            Bukkit.getServer().getLogger().addHandler(new ICM_LoggerHandler());
            
            //Handles data logging (TPS, RAM usage, Online Players)
            ICM_TpsFinder tpsfinder = new ICM_TpsFinder();
            tpsfinder.runTaskTimer(plugin, 0, 20L);
            ICM_DetailLogger detaillogger = new ICM_DetailLogger();
            detaillogger.runTaskTimer(plugin, 0, 20L * 15L);
            
            //All clear
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
        //Unregistering custom commands
        registry.unregisterCommands();
        IcarusMod.plugin = null;
        // All clear, its disabled! Woot
        Loggers.info(plugin, "has been disabled with no problems.");
    }
}

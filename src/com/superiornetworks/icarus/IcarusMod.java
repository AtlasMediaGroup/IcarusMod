package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_ban;
import com.superiornetworks.icarus.commands.ICM_CommandRegistry;
import com.superiornetworks.icarus.listeners.PlayerListener;
import com.superiornetworks.icarus.modules.*;
import java.sql.SQLException;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public class IcarusMod extends BukkitPlugin
{

    public static IcarusMod plugin;
    public BukkitCommandHandler handler;

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
    public void onLoad()
    {
        plugin = this;
        this.handler = new BukkitCommandHandler(plugin);

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
    public void onEnable()
    {
        // Bukkit Lib Important Stuff
        BukkitLib.init(plugin);
        handler.setCommandLocation(Command_ban.class.getPackage());

        // More YAML Setting Up and information.
        icmconfig = new ICM_Config(plugin, "config.yml");
        icmconfig = new ICM_Config(plugin, "saconfig.yml");
        icmconfig.saveDefaultConfig();
        config = icmconfig.getConfig();

        boolean error = false;
        if (config.getString("hostname") == null)
        {
            LoggerUtils.severe(plugin, "Hostname is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("port") == null)
        {
            LoggerUtils.severe(plugin, "Port is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("database") == null)
        {
            LoggerUtils.severe(plugin, "Database is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("username") == null)
        {
            LoggerUtils.severe(plugin, "Username is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
            error = true;
        }
        if (config.getString("password") == null)
        {
            LoggerUtils.severe(plugin, "Password is null in the config, please stop the server, ammend the fault and then restart. IcarusMod will not load until this error is resolved.");
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
            LoggerUtils.info(plugin, "has been enabled with no problems.");
        }
        else
        {
            Bukkit.broadcastMessage("IcarusMod had an issue loading up, please check your logs for more info, on first start, this is normal!");
            pm.disablePlugin(plugin);
        }

    }

    @Override
    public void onDisable()
    {
        // All clear, its disabled! Woot
        LoggerUtils.info(plugin, "has been disabled with no problems.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        // BukkitLib Magic here, making commands work!
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }
}

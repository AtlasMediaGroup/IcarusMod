package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_icarusmod;
import com.superiornetworks.icarus.commands.ICM_CommandRegistry;
import com.superiornetworks.icarus.listeners.PlayerListener;
import com.superiornetworks.icarus.modules.AdminWorldToggle;
import com.superiornetworks.icarus.modules.BusySystem;
import com.superiornetworks.icarus.modules.ChatModule;
import com.superiornetworks.icarus.modules.CommandBlockModule;
import com.superiornetworks.icarus.modules.CommandSpyModule;
import com.superiornetworks.icarus.modules.CreativePVP;
import com.superiornetworks.icarus.modules.DevelopmentMode;
import com.superiornetworks.icarus.modules.DoomHammer;
import com.superiornetworks.icarus.modules.FamousWarning;
import com.superiornetworks.icarus.modules.JoinModule;
import com.superiornetworks.icarus.modules.LoginModule;
import java.sql.SQLException;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public class IcarusMod extends BukkitPlugin
{
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
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
    public LoginModule loginModule;

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
        loginModule = new LoginModule(plugin);
    }

    @Override
    public void onEnable()
    {
        // Bukkit Lib Important Stuff
        BukkitLib.init(plugin);
        handler.setCommandLocation(Command_icarusmod.class.getPackage());

        // More YAML Setting Up and information.
        icmconfig = new ICM_Config(plugin, "config.yml");
        icmconfig.saveDefaultConfig();
        config = icmconfig.getConfig();

        // Listeners
        final PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(plugin), plugin);

        // MySQL Stuffs
        //Create MySQL
        mySQL = new MySQL(plugin, config.getString("hostname"), config.getString("port"), config.getString("database"), config.getString("username"), config.getString("password"));
        try
        {
            //Generate Default Tables
            ICM_SqlHandler.generateTables();
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

    @Override
    public void onDisable()
    {
        // Save the config.
        icmconfig.saveConfig();

        // All clear, its disabled! Woot
        LoggerUtils.info(plugin, "has been disabled with no problems.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        // BukkitLib Magic here, making commands work!
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }

@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int cooldownTime = 3;
        if(cooldowns.containsKey(sender.getName())) {
            long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0) {
                sender.sendMessage(ChatColor.RED + "You cant use that commands for another "+ secondsLeft +" seconds!");
                return true;
            }
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());
        return true;
    }
    
}

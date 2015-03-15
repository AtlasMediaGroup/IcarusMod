package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_icarusmod;
import com.superiornetworks.icarus.listeners.PlayerListener;
import com.superiornetworks.icarus.modules.AdminWorldToggle;
import com.superiornetworks.icarus.modules.BusySystem;
import com.superiornetworks.icarus.modules.ChatModule;
import com.superiornetworks.icarus.modules.CommandSpyOverride;
import com.superiornetworks.icarus.modules.CreativePVP;
import com.superiornetworks.icarus.modules.DevelopmentMode;
import com.superiornetworks.icarus.modules.DoomHammer;
import com.superiornetworks.icarus.modules.FamousWarning;
import com.superiornetworks.icarus.modules.JoinModule;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

public class IcarusMod extends BukkitPlugin
{

    public static IcarusMod plugin;
    public BukkitCommandHandler handler;

    // MySQL  
    public static MySQL mySQL;

    // YAML File Information
    public static YamlConfig config;

    // Module Information
    public FamousWarning famousWarning;
    public BusySystem busySystem;
    public CreativePVP creativePVP;
    public DoomHammer doomHammer;
    public AdminWorldToggle adminWorldToggle;
    public CommandSpyOverride commandSpyOverride;
    public DevelopmentMode developmentMode;
    public ChatModule chatModule;
    public JoinModule joinModule;

    @Override
    public void onLoad()
    {
        this.plugin = this;
        this.handler = new BukkitCommandHandler(plugin);

        // Module Loading
        famousWarning = new FamousWarning(plugin);
        busySystem = new BusySystem(plugin);
        creativePVP = new CreativePVP(plugin);
        doomHammer = new DoomHammer(plugin);
        adminWorldToggle = new AdminWorldToggle(plugin);
        commandSpyOverride = new CommandSpyOverride(plugin);
        developmentMode = new DevelopmentMode(plugin);
        chatModule = new ChatModule(plugin);
        joinModule = new JoinModule(plugin);
    }

    @Override
    public void onEnable()
    {
        // Bukkit Lib Important Stuff
        BukkitLib.init(plugin);
        handler.setCommandLocation(Command_icarusmod.class.getPackage());

        // More YAML Setting Up and information.
        this.config = new YamlConfig(plugin, "config.yml");

        // Listeners
        final PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(plugin), plugin);

        // MySQL Stuffs
        mySQL = new MySQL(plugin, "fop.us.to", "3306", "icarus", "icarus", "Password123*");

        // The Actual Loading of the configuration File
        config.load();

        // The All Clear
        LoggerUtils.info(plugin, "has been enabled with no problems.");

    }

    @Override
    public void onDisable()
    {
        // Save the config.
        config.save();

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

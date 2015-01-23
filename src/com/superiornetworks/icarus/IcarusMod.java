package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_icarusmod;
import com.superiornetworks.icarus.listeners.PlayerListener;
import com.superiornetworks.icarus.modules.*;
import java.util.Set;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.reflections.Reflections;

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

        /*
         // Listeners - Thanks WickedGamingUK for the code :) - This currently does not work and we will re-visit at a later date. 
         Reflections listeners = new Reflections(PlayerListener.class.getPackage());

         Set<Class<? extends Listener>> listenerSet = listeners.getSubTypesOf(Listener.class);

         for (Class<? extends Listener> listener : listenerSet)
         {
         register(listener);
         }
         */
        // MySQL Stuffs
        mySQL = new MySQL(plugin, config.getString("Hostname"), config.getString("Port"), config.getString("Database"), config.getString("User"), config.getString("Password"));

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

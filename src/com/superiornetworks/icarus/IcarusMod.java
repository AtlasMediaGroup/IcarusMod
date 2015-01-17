package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_Icarus;
import java.util.logging.Level;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class IcarusMod extends BukkitPlugin
{

    private IcarusMod plugin;
    public BukkitLogger logger;
    public String pluginName;
    public String pluginVersion;
    public String pluginBuildNumber;
    public String pluginBuildDate;
    public String pluginAuthors;
    public static BukkitCommandHandler handler;

    @Override
    public void onLoad()
    {
        final PluginDescriptionFile pdf = plugin.getDescription();
        pluginName = pdf.getName();
        pluginVersion = pdf.getVersion();
        pluginAuthors = PlayerUtils.concatPlayernames(pdf.getAuthors());
        plugin = this;
        logger = new BukkitLogger(plugin);

    }

    @Override
    public void onEnable()
    {
        logger.log(Level.INFO, "The IcarusMod has been enabled without issue.");
        handler.setCommandLocation(Command_Icarus.class.getPackage());
    }

    @Override
    public void onDisable()
    {
        logger.log(Level.INFO, "The IcarusMod has been disabled without isue.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }

}

package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_Icarus;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import static me.StevenLawson.TotalFreedomMod.TotalFreedomMod.plugin;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class IcarusMod extends BukkitPlugin
{

    private IcarusMod plugin;

    public String pluginName;
    public String pluginVersion;
    public String pluginBuildNumber;
    public String pluginBuildDate;
    public String pluginAuthors;
    //
    public BukkitCommandHandler handler;
    //
    public static MySQL mySQL;

    @Override
    public void onLoad()
    {
        final PluginDescriptionFile pdf = plugin.getDescription();
        pluginName = pdf.getName();
        pluginVersion = pdf.getVersion();
        pluginAuthors = PlayerUtils.concatPlayernames(pdf.getAuthors());
        this.plugin = this;
        this.handler = new BukkitCommandHandler(plugin);

    }

    @Override
    public void onEnable()
    {
        BukkitLib.init(plugin);
        handler.setCommandLocation(Command_Icarus.class.getPackage());
        TFM_Log.info(pluginName + " has been enabled without any issues.");
        mySQL = new MySQL(plugin, TFM_ConfigEntry.HOSTNAME.getString(), TFM_ConfigEntry.PORT.getString(), TFM_ConfigEntry.DATABASE.getString(), TFM_ConfigEntry.USER.getString(), TFM_ConfigEntry.PASSWORD.getString());

    }

    @Override
    public void onDisable()
    {
        TFM_Log.info(pluginName + " has been disabled without any issues.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }

}

package com.superiornetworks.icarus;

import com.superiornetworks.icarus.commands.Command_icarusmod;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.husky.mysql.MySQL;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class IcarusMod extends BukkitPlugin
{

    public IcarusMod plugin;
    public BukkitCommandHandler handler;
    public static MySQL mySQL;

    @Override
    public void onLoad()
    {
        this.plugin = this;
        this.handler = new BukkitCommandHandler(plugin);
    }

    @Override
    public void onEnable()
    {
        BukkitLib.init(plugin);
        LoggerUtils.info(plugin, "has been enabled with no problems.");

        handler.setCommandLocation(Command_icarusmod.class.getPackage());

        mySQL = new MySQL(plugin, TFM_ConfigEntry.HOSTNAME.getString(), TFM_ConfigEntry.PORT.getString(), TFM_ConfigEntry.DATABASE.getString(), TFM_ConfigEntry.USER.getString(), TFM_ConfigEntry.PASSWORD.getString());
    }

    @Override
    public void onDisable()
    {
        LoggerUtils.info(plugin, "has been disabled with no problems.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }
}

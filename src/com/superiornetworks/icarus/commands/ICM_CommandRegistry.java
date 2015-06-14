package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.IcarusMod;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

public class ICM_CommandRegistry
{

    private static CommandMap cmap = getCommandMap();

    public ICM_CommandRegistry()
    {
        registerCommands();
    }

    public static void registerCommands()
    {
        try
        {
            Pattern PATTERN = Pattern.compile("com/superiornetworks/icarus/commands/(Command_[^\\$]+)\\.class");
            CodeSource codeSource = IcarusMod.class.getProtectionDomain().getCodeSource();
            if (codeSource != null)
            {
                ZipInputStream zip = new ZipInputStream(codeSource.getLocation().openStream());
                ZipEntry zipEntry;
                while ((zipEntry = zip.getNextEntry()) != null)
                {
                    String entryName = zipEntry.getName();
                    Matcher matcher = PATTERN.matcher(entryName);
                    if (matcher.find())
                    {
                        try
                        {
                            Class<?> commandClass = Class.forName("com.superiornetworks.icarus.commands." + matcher.group(1));
                            if (commandClass.isAnnotationPresent(CommandParameters.class))
                            {
                                Annotation annotation = commandClass.getAnnotation(CommandParameters.class);
                                CommandParameters params = (CommandParameters) annotation;
                                ICM_Command command = new ICM_BlankCommand(params.name(), params.usage(), params.description(), Arrays.asList(params.aliases().split(", ")), params.rank(), commandClass);
                                command.register();
                            }
                        }
                        catch (ClassNotFoundException ex)
                        {

                        }
                    }
                }
            }
        }
        catch (IOException | NoSuchMethodException ex)
        {
            IcarusMod.plugin.getLogger().severe(ex.getLocalizedMessage());
        }
    }

    public static boolean isICMCommand(String name)
    {
        Command cmd = cmap.getCommand(name);
        if (!(cmd instanceof PluginCommand))
        {
            return true;
        }
        PluginCommand command = (PluginCommand) cmd;
        return command.getPlugin() == IcarusMod.plugin;
    }

    private static CommandMap getCommandMap()
    {
        if (cmap == null)
        {
            try
            {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            }
            catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        else if (cmap != null)
        {
            return cmap;
        }
        return getCommandMap();
    }

}

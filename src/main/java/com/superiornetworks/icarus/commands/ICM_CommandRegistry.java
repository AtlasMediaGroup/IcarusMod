package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.IcarusMod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

public class ICM_CommandRegistry
{
    private CommandMap cmap = getCommandMap();
    private final ArrayList<String> commands = new ArrayList<>();

    public ICM_CommandRegistry()
    {
        registerCommands();
    }
    
    public void unregisterCommands()
    {
        for(String name : commands)
        {
            Command cmd = cmap.getCommand(name);
            cmd.unregister(cmap);
        }
    }

    public void registerCommands()
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
                                commands.add(params.name());
                            }
                            else
                            {
                                Constructor construct = commandClass.getConstructor();
                                ICM_Command command = (ICM_Command) construct.newInstance();
                                command.register();
                                commands.add(command.command);
                            }
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
                        {
                            Bukkit.broadcastMessage("" + ex);
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
            IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
        }
    }

    private CommandMap getCommandMap()
    {
        if (cmap == null)
        {
            try
            {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
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

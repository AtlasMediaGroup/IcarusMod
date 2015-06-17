package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank.Rank;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ICM_BlankCommand extends ICM_Command
    {

    Class clazz;
    Object object;

    public ICM_BlankCommand(String name, String usage, String description, List<String> aliases, Rank rank, Class clazz) throws NoSuchMethodException
    {
        super(name, usage, description, aliases, rank);
        this.clazz = clazz;
        try
        {
            this.object = clazz.getConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(ICM_BlankCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        try
        {
            return (boolean) clazz.getMethod("onCommand", CommandSender.class, Command.class, String.class, String[].class).invoke(object, sender, cmd, label, args);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(ICM_BlankCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    }

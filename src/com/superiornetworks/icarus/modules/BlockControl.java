package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Settings;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class BlockControl extends IcarusModule implements Listener 
{
    public BlockControl(IcarusMod plugin) 
    {
        super(plugin);
    }
    
    @EventHandler (priority = EventPriority.HIGH)
    public void onBlockBurn(BlockBurnEvent event)
    {
        try
        {
            if (!ICM_Settings.getBoolean("fire-toggled"))
            {
                event.setCancelled(true);
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockIgnite(BlockIgniteEvent event)
    {
        try
        {
            if (!ICM_Settings.getBoolean("fire-toggled"))
            {
                event.setCancelled(true);
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFromTo(BlockFromToEvent event)
    {
        try
        {
            if (!ICM_Settings.getBoolean("fluidspread-toggled"))
            {
                event.setCancelled(true);
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        try
        {
            switch (event.getBlockPlaced().getType())
            {
                case LAVA:
                case STATIONARY_LAVA:
                {
                    if (!ICM_Settings.getBoolean("fluidplace-toggled"))
                    {
                        event.setCancelled(true);
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Fluid placing is currently disabled.");
                    }
                    break;
                }
                case WATER:
                case STATIONARY_WATER:
                {
                    if (!ICM_Settings.getBoolean("fluidplace-toggled"))
                    {
                        event.setCancelled(true);
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Fluid placing is currently disabled.");
                    }
                    break;
                }
                case FIRE:
                {
                    if (!ICM_Settings.getBoolean("fire-toggled"))
                    {
                        event.setCancelled(true);
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Fire is currently disabled.");
                    }
                    break;
                }
                case TNT:
                {
                    if (!ICM_Settings.getBoolean("explosives-toggled"))
                    {
                        event.setCancelled(true);
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "TNT is currently disabled.");
                    }
                    break;
                }
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event)
    {
        try
        {
            if (!ICM_Settings.getBoolean("explosives-toggled"))
            {
                event.setCancelled(true);
            }
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe(e.getLocalizedMessage());
        }
    }
}

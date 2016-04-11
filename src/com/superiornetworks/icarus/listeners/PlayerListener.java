package com.superiornetworks.icarus.listeners;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{

    private final IcarusMod plugin;

    public PlayerListener(IcarusMod plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        plugin.famousWarning.onUncancelledPlayerJoin(event);
        plugin.developmentMode.onUncancelledPlayerJoin(event);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerLogin(PlayerLoginEvent event)
    {
        plugin.joinModule.onPlayerLogin(event);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        plugin.commandBlockModule.onCommandPreprocess(event);
        plugin.commandSpyModule.onCommandPreprocess(event);
        plugin.imposterModule.onCommandPreprocess(event);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        plugin.adminWorldToggle.onPlayerMoveEvent(event);
        plugin.imposterModule.onPlayerMoveEvent(event);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event)
    {
        plugin.creativePVP.onEntityHit(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.busySystem.onPlayerQuit(event);
        plugin.developmentMode.onPlayerQuit(event);
        plugin.joinModule.onPlayerQuit(event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        plugin.busySystem.onPlayerChat(event);

    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event)
    {
        plugin.chatModule.onChat(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        plugin.creativePVP.onEntityDamage(event);
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event)
    {
        plugin.doomHammer.onPlayerUseItem(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        plugin.blockControl.onBlockPlace(event);
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event)
    {
        plugin.blockControl.onBlockIgnite(event);
    }
    
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event)
    {
        plugin.blockControl.onBlockBurn(event);
    }
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        plugin.blockControl.onEntityExplode(event);
    }
    
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event)
    {
        plugin.blockControl.onBlockFromTo(event);
    }
}

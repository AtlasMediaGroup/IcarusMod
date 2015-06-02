package com.superiornetworks.icarus.listeners;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerListener implements Listener
{

    private final IcarusMod plugin;

    public PlayerListener(IcarusMod plugin)
    {
        this.plugin = plugin;
    }

 @EventHandler
  public void onExplode(EntityExplodeEvent event)
  {
      event.setCancelled(true);
  }
  
  @EventHandler
  public void onItemUse(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (event.getItem() == null) {
      return;
    }
    ItemStack item = event.getItem();
    if (((item.getType() == Material.WATER) || (item.getType() == Material.WATER_BUCKET) || (item.getType() == Material.STATIONARY_WATER))) {
      event.setCancelled(true);
    }
    if (((item.getType() == Material.LAVA) || (item.getType() == Material.LAVA_BUCKET) || (item.getType() == Material.STATIONARY_LAVA))) {
      event.setCancelled(true);
    }
    if ((item.getType() == Material.TNT)) {
      event.setCancelled(true);
    }
    if (((item.getType() == Material.FLINT_AND_STEEL) || (item.getType() == Material.FIRE) || (item.getType() == Material.FIREBALL))) {
      event.setCancelled(true);
    }
  }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        plugin.famousWarning.onUncancelledPlayerJoin(event);
        plugin.developmentMode.onUncancelledPlayerJoin(event);
        plugin.joinModule.onPlayerJoin(event);
    }
    
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        plugin.commandBlockModule.onCommandPreprocess(event);
        plugin.commandSpyModule.onCommandPreprocess(event);
        plugin.imposterModule.onCommandPreprocess(event);
    }
    
    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event)
    {
        plugin.loginModule.onPlayerLoginEvent(event);
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

}

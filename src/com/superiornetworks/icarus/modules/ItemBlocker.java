package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.IcarusMod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemBlocker extends IcarusModule implements Listener {
    
    public ItemBlocker(IcarusMod plugin)
    {
        super(plugin);
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
  
    @EventHandler
  public void onBlockIgnite(BlockIgniteEvent event)
  {
      event.setCancelled(true);
  }
  
  @EventHandler
  public void onLiquidSpread(BlockFromToEvent event)
  {
    if (((event.getBlock().getType() == Material.WATER) || (event.getBlock().getType() == Material.STATIONARY_WATER))) {
      event.setCancelled(true);
    }
    if (((event.getBlock().getType() == Material.LAVA) || (event.getBlock().getType() == Material.STATIONARY_LAVA))) {
      event.setCancelled(true);
    }
  }
  
}

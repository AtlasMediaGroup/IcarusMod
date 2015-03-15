package com.superiornetworks.icarus.modules;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_SqlHandler;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DoomHammer extends IcarusModule implements Listener
{

    public DoomHammer(IcarusMod plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event)
    {
        try
        {
            ItemStack item = event.getItem();
            Player player = event.getPlayer();
            if (item == null)
            {
                return;
            }
            Entity e = null;
            if (item.equals(ICM_Utils.getDoomHammer()) && ICM_SqlHandler.hasDoomHammer(player.getName()))
            {
                for (Block block : player.getLineOfSight(null, 50))
                {
                    Location loc2 = block.getLocation();
                    for (LivingEntity entity : player.getWorld().getLivingEntities())
                    {
                        if (entity.getLocation().distance(loc2) <= 2 && !entity.equals(player))
                        {
                            e = entity;
                        }
                    }
                }
                if (e instanceof Player)
                {
                    Player eplayer = (Player) e;
                    ICM_Bans.addBan(eplayer, player, "Hit by " + player.getName() + "'s DoomHammer!");
                    ICM_Utils.adminAction(player.getName(), "Casting oblivion over " + eplayer.getName(), true);
                    Bukkit.broadcastMessage(ChatColor.RED + eplayer.getName() + " will be completely obliviated!");
                }
                else if (e instanceof LivingEntity)
                {
                    final LivingEntity le = (LivingEntity) e;
                    le.setVelocity(le.getVelocity().add(new Vector(0, 3, 0)));
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            le.getWorld().createExplosion(le.getLocation().getX(), le.getLocation().getY(), le.getLocation().getZ(), 5f, false, false);
                            le.getWorld().strikeLightningEffect(le.getLocation());
                            le.setHealth(0d);
                        }
                    }.runTaskLater(IcarusMod.plugin, 20L * 2L);
                    
                }
                event.setCancelled(true);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DoomHammer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

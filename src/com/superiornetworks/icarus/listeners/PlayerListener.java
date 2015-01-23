package com.superiornetworks.icarus.listeners;

import com.superiornetworks.icarus.ICM_Bans;
import com.superiornetworks.icarus.ICM_Utils;
import com.superiornetworks.icarus.IcarusMod;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.playerMsg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
        Player player = event.getPlayer();
        // Loading in the Famous Warning System Module
        plugin.famousWarning.onUncancelledPlayerJoin(event);

        //Turn on commandspy
        if (TFM_AdminList.isSuperAdmin(player))
        {
            TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
            playerdata.setCommandSpy(true);
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (!ICM_Utils.MANAGERS.contains(player.getName()) && event.getTo().getWorld() == Bukkit.getWorld("adminworld") && !IcarusMod.config.getBoolean("toggles.AdminWorld"))
        {
            TFM_Util.playerMsg(player, "AdminWorld is currently disabled.", ChatColor.RED);
            event.setCancelled(true);
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        if (!(event.getDamager() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        Player hitter = (Player) event.getDamager();
        if (hitter.getGameMode() == GameMode.CREATIVE || ICM_Utils.GOD.contains(hitter.getName()))
        {
            event.setCancelled(true);
            playerMsg(hitter, "Don't try to PVP in Creative or God mode. ~ Thanks.", ChatColor.RED);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        if (ICM_Utils.GOD.contains(player.getName()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event)
    {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null)
        {
            return;
        }
        Entity e = null;
        if (item.equals(ICM_Utils.getDoomHammer()) && ICM_Utils.DOOMHAMMERS.contains(player.getName()))
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
                TFM_Util.adminAction(player.getName(), "Casting oblivion over " + eplayer.getName(), true);
                TFM_Util.bcastMsg(eplayer.getName() + " will be completely obliviated!", ChatColor.RED);
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

}

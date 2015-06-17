package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandParameters(name = "potato", description = "Give everyone potatoes!", usage = "/potato", rank = ICM_Rank.Rank.SUPER)
public class Command_potato
    {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            Inventory inv = p.getInventory();

            ItemStack item = new ItemStack(Material.BAKED_POTATO, 1);
            ItemMeta im;
            im = item.getItemMeta();
            im.setDisplayName(ChatColor.GOLD + "Potato");
            item.setItemMeta(im);
            inv.addItem(item);
            p.sendMessage(ChatColor.GOLD + "Much potato, such yum.");
        }
        return true;
    }
    }

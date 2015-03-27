package com.superiornetworks.icarus.commands;

import com.superiornetworks.icarus.ICM_Utils;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(source = SourceType.ANY, permission = "")
public class Command_pie extends BukkitCommand 
{
    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
        if (!TFM_AdminList.isSuperAdmin(sender))
        {
            sender.sendMessage(ICM_Utils.NO_PERMS_MESSAGE);
            return true;
        }
        
        for (Player p : Bukkit.getOnlinePlayers())
        {
            Inventory inv = p.getInventory();
            
            ItemStack item = new ItemStack(Material.PUMPKIN_PIE, 1);
            ItemMeta im;
            im = item.getItemMeta();
            im.setDisplayName(ChatColor.AQUA + "Pie");
            item.setItemMeta(im);
            inv.addItem(item);
            p.sendMessage(ChatColor.AQUA + "Q: When does Dorothy from The Wizard of OZ weigh a pie? A: Somewhere over the rainbow, weigh-a-pie.");
        }
        return true;
    }
}

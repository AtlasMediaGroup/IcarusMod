package com.superiornetworks.icarus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ICM_Utils
{

    public static final ArrayList<Player> IMPOSTERS = new ArrayList<>();
    public static final List<String> MANAGERS = Arrays.asList("Wild1145", "Varuct", "thecjgcjg");
    public static final List<String> DEVELOPERS = Arrays.asList("Wild1145", "Camzie99", "Hockeyfan360");
    public static final List<ChatColor> COLOURS = Arrays.asList(
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.BLUE,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW
    );
    //REPLACING WITH MYSQL SHORTLY
    public static final List<String> FAMOUS = Arrays.asList("");

    public static final String NO_PERMS_MESSAGE = (ChatColor.DARK_RED + "Sorry, you do not have the required permissions to access this command. If you feel this is in error, please contact a server manager or developer ASAP!");

    public static ItemStack getDoomHammer()
    {
        ItemStack banhammer = new ItemStack(Material.GOLD_AXE, 1);
        banhammer.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        ItemMeta banhammermeta = banhammer.getItemMeta();
        banhammermeta.setLore(Arrays.asList(ChatColor.BLUE + "Unleash the power of...", ChatColor.YELLOW + "The DoomHammer!"));
        banhammermeta.setDisplayName(ChatColor.RED + "DoomHammer!");
        banhammer.setItemMeta(banhammermeta);
        return banhammer;
    }

    public static ChatColor getRandomChatColour()
    {
        Random random = new Random();
        return COLOURS.get(random.nextInt(COLOURS.size()));
    }

    public static String colour(String string)
    {
        string = ChatColor.translateAlternateColorCodes('&', string);
        string = string.replaceAll("&-", getRandomChatColour().toString());
        return string;
    }

    public static String aOrAn(String string)
    {
        if (string.toLowerCase().matches("^[aeiou].*"))
        {
            return "an";
        }
        return "a";
    }

    public static void adminAction(String name, String message, boolean isRed)
    {
        Bukkit.broadcastMessage((isRed ? ChatColor.RED : ChatColor.AQUA) + name + " - " + message);
    }

    public static void playerMsg(CommandSender player, String message)
    {
        player.sendMessage(colour(message));
    }

    public static String buildMessage(String[] args, int startindex)
    {
        //Not certain why a complex method was used for this before, this does the exact same thing.
        return StringUtils.join(args, " ", startindex, args.length);
    }
}

package com.superiornetworks.icarus;

import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ICM_Rank
{

    public enum Rank
    {

        IMPOSTER(-1, "Imposter", "§3[IMP]"), NONOP(0, "Non-Op", "§c[Non-Op]"), OP(0, "Op", "§c[Op]"), SUPER(1, "Super Admin", "§b[SA]"), SENIOR(2, "Senior Admin", "§d[SrA]"), EXECUTIVE(3, "Executive Admin", "§6[EXEC]"), DEVELOPER(4, "Developer", "§5[Dev]"), MANAGER(5, "Manager", "§4[Manager]");

        public int level;
        public String name;
        public String actag; //Admin-chat tag

        Rank(int level, String name, String actag)
        {
            this.level = level;
            this.name = name;
            this.actag = actag;
        }

        public int getLevel()
        {
            return this.level;
        }
    }

    public static HashMap<String, Rank> ranks = new HashMap<>();
    public static HashMap<String, String> nicks = new HashMap<>();
    public static HashMap<String, String> tags = new HashMap<>();

    public static boolean shouldCache()
    {
        return (IcarusMod.config.contains("toggles.caching") && IcarusMod.config.getBoolean("toggles.caching"));
    }

    public static Rank getRank(CommandSender player)
    {
        try
        {
            if(!(player instanceof Player))
            {
                if(ranks.containsKey(player.getName()) && shouldCache())
                {
                    return ranks.get(player.getName());
                }
                if("Console".equalsIgnoreCase(player.getName()))
                {
                    if(shouldCache())
                    {
                        ranks.put(player.getName(), Rank.SENIOR);
                    }
                    return Rank.SENIOR;
                }
                else
                {
                    OfflinePlayer offplayer = Bukkit.getOfflinePlayer(player.getName().replaceAll("[^A-Za-z0-9_]", ""));
                    if(offplayer == null)
                    {
                        if(shouldCache())
                        {
                            ranks.put(player.getName(), Rank.SENIOR);
                        }
                        return Rank.SENIOR;
                    }
                    for(Rank rank : Rank.values())
                    {
                        if(ICM_SqlHandler.getRank(player.getName()).equalsIgnoreCase((rank.name)))
                        {
                            return rank;
                        }
                    }
                    if(shouldCache())
                    {
                        ranks.put(player.getName(), Rank.SENIOR);
                    }
                    return Rank.SENIOR;
                }
            }
            if(ICM_Utils.IMPOSTERS.contains((Player) player))
            {
                return Rank.IMPOSTER;
            }
            if(ranks.containsKey(player.getName()) && shouldCache())
            {
                return ranks.get(player.getName());
            }
            try
            {
                for(Rank rank : Rank.values())
                {
                    if(ICM_SqlHandler.getRank(((Player) player).getName()).equalsIgnoreCase(rank.name))
                    {
                        if(shouldCache())
                        {
                            ranks.put(player.getName(), rank);
                        }
                        return rank;
                    }
                }
            }
            catch(Exception ex)
            {
                IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
            }
        }
        catch(Exception ex)
        {
            IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
        }
        if(shouldCache())
        {
            ranks.put(player.getName(), Rank.OP);
        }
        return Rank.OP;
    }

    public static boolean isRankOrHigher(CommandSender player, Rank rank)
    {
        return getRank(player).level >= rank.getLevel();
    }

    public static boolean isRankOrHigher(CommandSender player, int rank)
    {
        return getRank(player).level >= rank;
    }

    public static boolean isEqualOrHigher(Rank rank, Rank rank2)
    {
        return rank.level >= rank2.level;
    }

    public static boolean isRank(CommandSender player, Rank rank)
    {
        return getRank(player).equals(rank);
    }

    public static Player getPlayer(String nick)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if(player.getDisplayName().toLowerCase().contains(nick.toLowerCase()))
            {
                return player;
            }
            if(player.getName().toLowerCase().contains(nick.toLowerCase()))
            {
                return player;
            }
        }
        return null;
    }

    public static Rank getFromUsername(String name)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        try
        {
            if(player != null)
            {
                for(Rank rank : Rank.values())
                {
                    if(!ICM_SqlHandler.playerExists(player.getName()))
                    {
                        return Rank.OP;
                    }
                    if(ICM_SqlHandler.getRank(player.getName()).equalsIgnoreCase(rank.name))
                    {
                        return rank;
                    }
                }
            }
        }
        catch(Exception ignored)
        {

        }
        return Rank.OP;
    }

    public static Rank levelToRank(int level)
    {
        for(Rank rank : Rank.values())
        {
            if(rank.level == level)
            {
                return rank;
            }
        }
        return Rank.OP;
    }

    public static Rank nameToRank(String name)
    {
        for(Rank rank : Rank.values())
        {
            if(rank.name.equalsIgnoreCase(name) || rank.name.split(" ")[0].equalsIgnoreCase(name))
            {
                return rank;
            }
        }
        return Rank.OP;
    }

    public static void unImposter(Player player)
    {
        try
        {
            ICM_Utils.IMPOSTERS.remove(player);
            ICM_SqlHandler.updateInTable("playerName", player.getName(), false, "imposter", "players");
            ICM_SqlHandler.updateInTable("playerName", player.getName(), player.getAddress().getHostString(), "ip", "players");
            if(shouldCache())
            {
                ranks.clear();
                nicks.clear();
                tags.clear();
            }
            Bukkit.broadcastMessage(ChatColor.BLUE + player.getName() + ChatColor.GOLD + " has been verified as an admin!");
        }
        catch(SQLException | CommandException ex)
        {
            IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
        }
    }

    public static String getTag(Player player)
    {
        if(tags.containsKey(player.getName()) && shouldCache())
        {
            String tag = tags.get(player.getName());
            if("".equalsIgnoreCase(tag) || tag == null || "off".equalsIgnoreCase(tag) || "default".equals(tag))
            {
                tags.put(player.getName(), getRank(player).actag);
                return getRank(player).actag;
            }
            return tag;
        }
        try
        {
            String tag = ICM_SqlHandler.getTag(player.getName());
            if("".equals(tag) || tag == null || "off".equals(tag) || "default".equals(tag))
            {
                if(shouldCache())
                {
                    tags.put(player.getName(), getRank(player).actag);
                }
                return getRank(player).actag;
            }
            if(shouldCache())
            {
                tags.put(player.getName(), tag);
            }
            return tag;
        }
        catch(Exception ex)
        {
            IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
            if(shouldCache())
            {
                tags.put(player.getName(), getRank(player).actag);
            }
            return getRank(player).actag;
        }
    }

    public static String getNick(Player player)
    {
        if(nicks.containsKey(player.getName()) && shouldCache())
        {
            String nick = nicks.get(player.getName());
            if("".equalsIgnoreCase(nick) || nick == null || "off".equalsIgnoreCase(nick))
            {
                if(shouldCache())
                {
                    nicks.put(player.getName(), player.getName());
                }
                return player.getName();
            }
            return nick;
        }
        try
        {
            String nick = ICM_SqlHandler.getNick(player.getName());
            if("".equalsIgnoreCase(nick) || nick == null || "off".equalsIgnoreCase(nick))
            {
                if(shouldCache())
                {
                    nicks.put(player.getName(), player.getName());
                }
                return player.getName();
            }
            else
            {
                if(shouldCache())
                {
                    nicks.put(player.getName(), nick);
                }
                return nick;
            }
        }
        catch(Exception ex)
        {
            IcarusMod.plugin.getPluginLogger().severe(ex.getLocalizedMessage());
            if(shouldCache())
            {
                nicks.put(player.getName(), player.getName());
            }
            return player.getName();
        }
    }

    public static void setRank(Player player, Rank rank, CommandSender sender)
    {
        try
        {
            if(sender == null)
            {
                ICM_SqlHandler.updateInTable("playerName", player.getName(), rank.name, "rank", "players");
                if(shouldCache())
                {
                    ranks.put(player.getName(), rank);
                    if(nicks.containsKey(player.getName()))
                    {
                        nicks.remove(player.getName());
                    }
                    if(tags.containsKey(player.getName()))
                    {
                        tags.remove(player.getName());
                    }
                }
                return;
            }
            if(getRank(player) == Rank.IMPOSTER && !rank.equals(Rank.OP))
            {
                unImposter(player);
                return;
            }
            if(getRank(sender).level <= getRank(player).level && rank != Rank.OP)
            {
                sender.sendMessage(ChatColor.RED + "You can only add people to a rank who are lower than yourself.");
                return;
            }
            if(rank.level >= getRank(sender).level)
            {
                sender.sendMessage(ChatColor.RED + "You can only add people to a rank lower than yourself.");
                return;
            }
            if(rank.level < getRank(player).level && (!rank.equals(Rank.OP)))
            {
                sender.sendMessage(ChatColor.RED + rank.name + " is a lower rank than " + player.getName() + "'s current rank of " + getRank(player).name + ".");
                return;
            }
            String message = sender.getName() + " has promoted " + player.getName() + " to the clearance level of " + rank.level + " as " + rank.name + ".";
            if(rank.equals(Rank.OP))
            {
                message = sender.getName() + " has demoted " + player.getName() + " to the clearance level of 0 as an Op.\nWe hope any issues are resolved shortly.";
            }
            ICM_Utils.adminAction(sender.getName(), message, false);
            ICM_SqlHandler.updateInTable("playerName", player.getName(), rank.name, "rank", "players");
            if(shouldCache())
            {
                ranks.put(player.getName(), rank);
                if(nicks.containsKey(player.getName()))
                {
                    nicks.remove(player.getName());
                }
                if(tags.containsKey(player.getName()))
                {
                    tags.remove(player.getName());
                }
            }
        }
        catch(Exception ex)
        {
            IcarusMod.plugin.getLogger().severe(ex.getLocalizedMessage());
        }
    }
}

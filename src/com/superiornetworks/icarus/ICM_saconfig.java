package com.superiornetworks.icarus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import com.superiornetworks.icarus.ICM_Rank;
import com.superiornetworks.icarus.ICM_Utils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

public class ICM_Saconfig
{
    
    private String username;
    private final boolean SENIOR;
    private final boolean TELNET;
    private final List<String> ips;
    private boolean isActivated;
    private final UUID uuid;
    
    public  UUID getUniqueID()
    {
        return uuid;
    }

    public void setLastLoginName(String lastLoginName)
    {
        this.username = lastLoginName;
    }

    public List<String> getIps()
    {
        return Collections.unmodifiableList(ips);
    }

    public void addIp(String ip)
    {
        if (!ips.contains(ip))
        {
            ips.add(ip);
        }
    }

    public void addIps(List<String> ips)
    {
        for (String ip : ips)
        {
            addIp(ip);
        }
    }

    public void removeIp(String ip)
    {
        if (ips.contains(ip))
        {
            ips.remove(ip);
        }
    }

    public void clearIPs()
    {
        ips.clear();
    }

    public boolean senioradmin()
    {
        return SENIOR;
    }

    public boolean telnetadmin()
    {
        return TELNET;
    }
    
        public boolean isActivated()
    {
        return isActivated;
    }

    public void setActivated(boolean isActivated)
    {
        this.isActivated = isActivated;
    }


    public ICM_Saconfig (UUID uuid, boolean telnetadmin, boolean senioradmin, boolean isActivated)
    {
        this.uuid = uuid;
        this.ips = new ArrayList<String>();
        this.TELNET = telnetadmin;
        this.SENIOR = senioradmin;
        this.isActivated = isActivated;
    }

    public ICM_Saconfig (UUID uuid, ConfigurationSection section)
    {
        this.username = section.getString("username");
        this.ips = section.getStringList("ip_address");
        this.SENIOR = section.getBoolean("senior_admin", false);
        this.TELNET = section.getBoolean("telnet_admin", false);
        this.isActivated = section.getBoolean("is_activated", true);
        this.uuid = uuid;

    
    {
        final StringBuilder output = new StringBuilder();

        output.append("- IPs: ").append(StringUtils.join(ips, ", ")).append("\n");
        output.append("- Senior Admin: ").append(SENIOR).append("\n");
        output.append("- Telnet Admin: ").append(TELNET).append("\n");
        output.append("- Is Active: ").append(isActivated);
        output.append("UUID: ").append(uuid.toString()).append("\n");
    }

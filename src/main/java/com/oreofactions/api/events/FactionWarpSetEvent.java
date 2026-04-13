package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionWarpSetEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String warpName;
    private final Location location;
    private final String password;
    private final Player setter;

    public FactionWarpSetEvent(Faction faction, String warpName, Location location,
                               String password, Player setter) {
        super(faction);
        this.warpName = warpName;
        this.location = location;
        this.password = password;
        this.setter = setter;
    }

    
    public String getWarpName() {
        return warpName;
    }

    
    public Location getLocation() {
        return location;
    }

    
    public String getPassword() {
        return password;
    }

    
    public Player getSetter() {
        return setter;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

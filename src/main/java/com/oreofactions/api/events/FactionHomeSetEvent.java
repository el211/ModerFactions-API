package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionHomeSetEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Location oldHome;
    private final Location newHome;
    private final Player setter;

    public FactionHomeSetEvent(Faction faction, Location oldHome, Location newHome, Player setter) {
        super(faction);
        this.oldHome = oldHome;
        this.newHome = newHome;
        this.setter = setter;
    }

    
    public Location getOldHome() {
        return oldHome;
    }

    
    public Location getNewHome() {
        return newHome;
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

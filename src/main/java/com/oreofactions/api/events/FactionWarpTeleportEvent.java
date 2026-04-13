package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionWarpTeleportEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final String warpName;
    private final Location destination;

    public FactionWarpTeleportEvent(Faction faction, Player player,
                                    String warpName, Location destination) {
        super(faction);
        this.player = player;
        this.warpName = warpName;
        this.destination = destination;
    }

    
    public Player getPlayer() {
        return player;
    }

    
    public String getWarpName() {
        return warpName;
    }

    
    public Location getDestination() {
        return destination;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionHomeTeleportEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Location destination;

    public FactionHomeTeleportEvent(Faction faction, Player player, Location destination) {
        super(faction);
        this.player = player;
        this.destination = destination;
    }

    public Player getPlayer() {
        return player;
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

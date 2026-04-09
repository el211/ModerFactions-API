package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionWarpDeleteEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String warpName;
    private final Player actor;

    public FactionWarpDeleteEvent(Faction faction, String warpName, Player actor) {
        super(faction);
        this.warpName = warpName;
        this.actor = actor;
    }

    
    public String getWarpName() {
        return warpName;
    }

    
    public Player getActor() {
        return actor;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

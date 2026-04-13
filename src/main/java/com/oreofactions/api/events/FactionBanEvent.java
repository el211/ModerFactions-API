package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class FactionBanEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID banned;
    private final Player banner;

    public FactionBanEvent(Faction faction, UUID banned, Player banner) {
        super(faction);
        this.banned = banned;
        this.banner = banner;
    }

    
    public UUID getBannedUUID() {
        return banned;
    }

    
    public Player getBanner() {
        return banner;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

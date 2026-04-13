package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionShieldActivateEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player activator;
    private final long durationMs;

    public FactionShieldActivateEvent(Faction faction, Player activator, long durationMs) {
        super(faction);
        this.activator = activator;
        this.durationMs = durationMs;
    }

    
    public Player getActivator() {
        return activator;
    }

    
    public long getDurationMs() {
        return durationMs;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

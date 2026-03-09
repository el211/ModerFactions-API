package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction grace period is about to be activated.
 * Cancelling prevents the grace period from being applied.
 */
public class FactionGraceActivateEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player activator;
    private final long durationMs;

    public FactionGraceActivateEvent(Faction faction, Player activator, long durationMs) {
        super(faction);
        this.activator = activator;
        this.durationMs = durationMs;
    }

    /** The player activating grace, or null if done by API/admin. */
    public Player getActivator() {
        return activator;
    }

    /** Duration of the grace period in milliseconds. */
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

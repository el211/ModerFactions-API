package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction shield is about to be activated.
 * Cancelling prevents the shield from activating.
 */
public class FactionShieldActivateEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player activator;
    private final long durationMs;

    public FactionShieldActivateEvent(Faction faction, Player activator, long durationMs) {
        super(faction);
        this.activator = activator;
        this.durationMs = durationMs;
    }

    /** The player activating the shield, or null if done by API/admin. */
    public Player getActivator() {
        return activator;
    }

    /** Duration the shield will be active in milliseconds. */
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

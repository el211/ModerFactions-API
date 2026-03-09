package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction becomes raidable (DTR drops to or below 0).
 * Not cancellable — the faction is already raidable when this fires.
 * Use to announce, log, or trigger other effects.
 */
public class FactionRaidableEvent extends FactionEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public FactionRaidableEvent(Faction faction) {
        super(faction);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

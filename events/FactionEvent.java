package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.Event;

/**
 * Base class for all OreoFactions events.
 * Each concrete subclass provides its own HandlerList.
 */
public abstract class FactionEvent extends Event {

    private final Faction faction;

    public FactionEvent(Faction faction) {
        this.faction = faction;
    }

    /**
     * Returns the faction involved in this event.
     */
    public Faction getFaction() {
        return faction;
    }
}

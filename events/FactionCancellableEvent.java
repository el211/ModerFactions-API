package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.Cancellable;

/**
 * Base class for OreoFactions events that can be cancelled.
 * Each concrete subclass provides its own HandlerList.
 */
public abstract class FactionCancellableEvent extends FactionEvent implements Cancellable {

    private boolean cancelled = false;

    public FactionCancellableEvent(Faction faction) {
        super(faction);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

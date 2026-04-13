package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionPeacefulToggleEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeReason { COMMAND, ADMIN, API, SYSTEM }

    private final boolean oldPeaceful;
    private final boolean newPeaceful;
    private final Player actor;
    private final ChangeReason reason;

    public FactionPeacefulToggleEvent(Faction faction, boolean oldPeaceful, boolean newPeaceful,
                                      Player actor, ChangeReason reason) {
        super(faction);
        this.oldPeaceful = oldPeaceful;
        this.newPeaceful = newPeaceful;
        this.actor = actor;
        this.reason = reason;
    }

    public boolean getOldPeaceful() {
        return oldPeaceful;
    }

    public boolean getNewPeaceful() {
        return newPeaceful;
    }

    public Player getActor() {
        return actor;
    }

    public ChangeReason getReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

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

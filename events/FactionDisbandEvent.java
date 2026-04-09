package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionDisbandEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum Reason { COMMAND, ADMIN, EMPTY, API }

    private final Player player;
    private final Reason reason;

    public FactionDisbandEvent(Faction faction, Player player, Reason reason) {
        super(faction);
        this.player = player;
        this.reason = reason;
    }

    
    public Player getPlayer() {
        return player;
    }

    
    public Reason getReason() {
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

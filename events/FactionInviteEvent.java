package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class FactionInviteEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID invited;
    private final Player inviter;

    public FactionInviteEvent(Faction faction, UUID invited, Player inviter) {
        super(faction);
        this.invited = invited;
        this.inviter = inviter;
    }

    
    public UUID getInvitedUUID() {
        return invited;
    }

    
    public Player getInviter() {
        return inviter;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

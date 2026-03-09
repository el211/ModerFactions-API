package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Fired when a player is about to be invited to a faction.
 * Cancelling prevents the invite from being sent.
 */
public class FactionInviteEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID invited;
    private final Player inviter;

    public FactionInviteEvent(Faction faction, UUID invited, Player inviter) {
        super(faction);
        this.invited = invited;
        this.inviter = inviter;
    }

    /** UUID of the player being invited. */
    public UUID getInvitedUUID() {
        return invited;
    }

    /** The player sending the invite. */
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

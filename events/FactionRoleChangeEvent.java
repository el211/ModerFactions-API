package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import com.oreofactions.models.FactionRole;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Fired when a faction member's role is about to change.
 * Cancelling prevents the role change.
 */
public class FactionRoleChangeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID target;
    private final FactionRole oldRole;
    private final FactionRole newRole;
    private final Player actor;

    public FactionRoleChangeEvent(Faction faction, UUID target, FactionRole oldRole,
                                  FactionRole newRole, Player actor) {
        super(faction);
        this.target = target;
        this.oldRole = oldRole;
        this.newRole = newRole;
        this.actor = actor;
    }

    /** UUID of the member whose role is changing. */
    public UUID getTarget() {
        return target;
    }

    /** The role the member had before. */
    public FactionRole getOldRole() {
        return oldRole;
    }

    /** The role the member will have after the change. */
    public FactionRole getNewRole() {
        return newRole;
    }

    /** The player performing the role change, or null if done by API/admin. */
    public Player getActor() {
        return actor;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

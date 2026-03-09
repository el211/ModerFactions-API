package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Fired when a new faction is about to be created.
 * Cancelling prevents the faction from being created.
 */
public class FactionCreateEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID leader;
    private final Player player;

    public FactionCreateEvent(Faction faction, UUID leader, Player player) {
        super(faction);
        this.leader = leader;
        this.player = player;
    }

    /** UUID of the player who is creating the faction. */
    public UUID getLeader() {
        return leader;
    }

    /** The online player creating the faction, or null if called programmatically. */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

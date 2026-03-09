package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Fired when a player is about to be banned from a faction.
 * Cancelling prevents the ban.
 */
public class FactionBanEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID banned;
    private final Player banner;

    public FactionBanEvent(Faction faction, UUID banned, Player banner) {
        super(faction);
        this.banned = banned;
        this.banner = banner;
    }

    /** UUID of the player being banned. */
    public UUID getBannedUUID() {
        return banned;
    }

    /** The player performing the ban, or null if done by admin/API. */
    public Player getBanner() {
        return banner;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

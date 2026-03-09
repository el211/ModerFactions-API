package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction warp is about to be deleted.
 * Cancelling prevents deletion.
 */
public class FactionWarpDeleteEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String warpName;
    private final Player actor;

    public FactionWarpDeleteEvent(Faction faction, String warpName, Player actor) {
        super(faction);
        this.warpName = warpName;
        this.actor = actor;
    }

    /** The name of the warp being deleted. */
    public String getWarpName() {
        return warpName;
    }

    /** The player deleting the warp, or null if done by API/admin. */
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

package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction is about to unclaim a chunk.
 * Cancelling prevents the unclaim.
 */
public class FactionUnclaimEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String claimKey;
    private final Player player;

    public FactionUnclaimEvent(Faction faction, String claimKey, Player player) {
        super(faction);
        this.claimKey = claimKey;
        this.player = player;
    }

    /** The claim key being unclaimed (format: "world:chunkX:chunkZ"). */
    public String getClaimKey() {
        return claimKey;
    }

    /** The player unclaiming, or null if done programmatically. */
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

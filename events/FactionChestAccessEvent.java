package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a player is about to open the faction chest.
 * Cancelling prevents the chest from opening.
 */
public class FactionChestAccessEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final boolean canWrite;

    public FactionChestAccessEvent(Faction faction, Player player, boolean canWrite) {
        super(faction);
        this.player = player;
        this.canWrite = canWrite;
    }

    /** The player attempting to open the chest. */
    public Player getPlayer() {
        return player;
    }

    /** Whether the player has write permission to the chest. */
    public boolean canWrite() {
        return canWrite;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

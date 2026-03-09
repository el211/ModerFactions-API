package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a player is about to receive a combat tag.
 * Cancelling prevents the tag from being applied.
 */
public class FactionCombatTagEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player tagged;
    private final Player attacker;
    private final long durationMs;

    /**
     * @param faction   The faction of the tagged player (may be null if no faction).
     * @param tagged    The player being tagged.
     * @param attacker  The player who triggered the tag (may be null for non-player sources).
     * @param durationMs Combat tag duration in milliseconds.
     */
    public FactionCombatTagEvent(Faction faction, Player tagged, Player attacker, long durationMs) {
        super(faction);
        this.tagged = tagged;
        this.attacker = attacker;
        this.durationMs = durationMs;
    }

    /** The player receiving the combat tag. */
    public Player getTagged() {
        return tagged;
    }

    /** The player who caused the tag (may be null). */
    public Player getAttacker() {
        return attacker;
    }

    /** Duration of the combat tag in milliseconds. */
    public long getDurationMs() {
        return durationMs;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

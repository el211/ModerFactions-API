package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when the relation between two factions is about to change
 * (ally, enemy, truce, or neutral).
 * Cancelling prevents the relation change.
 */
public class FactionRelationChangeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum RelationType { ALLY, ENEMY, TRUCE, NEUTRAL }

    private final Faction targetFaction;
    private final RelationType oldRelation;
    private final RelationType newRelation;
    private final Player actor;

    public FactionRelationChangeEvent(Faction faction, Faction targetFaction,
                                      RelationType oldRelation, RelationType newRelation,
                                      Player actor) {
        super(faction);
        this.targetFaction = targetFaction;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
        this.actor = actor;
    }

    /** The faction whose relation is being changed relative to. */
    public Faction getTargetFaction() {
        return targetFaction;
    }

    /** The current relation before the change. */
    public RelationType getOldRelation() {
        return oldRelation;
    }

    /** The new relation after the change. */
    public RelationType getNewRelation() {
        return newRelation;
    }

    /** The player initiating the relation change, or null if by API/admin. */
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

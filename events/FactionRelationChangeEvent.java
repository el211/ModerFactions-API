package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

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

    
    public Faction getTargetFaction() {
        return targetFaction;
    }

    
    public RelationType getOldRelation() {
        return oldRelation;
    }

    
    public RelationType getNewRelation() {
        return newRelation;
    }

    
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

package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import com.oreofactions.models.FactionRole;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

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

    
    public UUID getTarget() {
        return target;
    }

    
    public FactionRole getOldRole() {
        return oldRole;
    }

    
    public FactionRole getNewRole() {
        return newRole;
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

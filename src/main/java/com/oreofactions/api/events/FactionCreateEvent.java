package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class FactionCreateEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID leader;
    private final Player player;

    public FactionCreateEvent(Faction faction, UUID leader, Player player) {
        super(faction);
        this.leader = leader;
        this.player = player;
    }

    
    public UUID getLeader() {
        return leader;
    }

    
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

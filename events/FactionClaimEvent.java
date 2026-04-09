package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionClaimEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Chunk chunk;
    private final Player player;

    public FactionClaimEvent(Faction faction, Chunk chunk, Player player) {
        super(faction);
        this.chunk = chunk;
        this.player = player;
    }

    
    public Chunk getChunk() {
        return chunk;
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

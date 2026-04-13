package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionChestAccessEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final boolean canWrite;

    public FactionChestAccessEvent(Faction faction, Player player, boolean canWrite) {
        super(faction);
        this.player = player;
        this.canWrite = canWrite;
    }

    
    public Player getPlayer() {
        return player;
    }

    
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

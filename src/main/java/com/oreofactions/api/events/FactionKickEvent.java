package com.oreofactions.api.events;

import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class FactionKickEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID kicked;
    private final FPlayer kickedFPlayer;
    private final Player kicker;

    public FactionKickEvent(Faction faction, UUID kicked, FPlayer kickedFPlayer, Player kicker) {
        super(faction);
        this.kicked = kicked;
        this.kickedFPlayer = kickedFPlayer;
        this.kicker = kicker;
    }

    
    public UUID getKickedUUID() {
        return kicked;
    }

    
    public FPlayer getKickedFPlayer() {
        return kickedFPlayer;
    }

    
    public Player getKicker() {
        return kicker;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

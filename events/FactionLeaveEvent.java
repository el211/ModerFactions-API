package com.oreofactions.api.events;

import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a player is about to leave their faction voluntarily.
 * Cancelling prevents the leave.
 */
public class FactionLeaveEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final FPlayer fPlayer;

    public FactionLeaveEvent(Faction faction, Player player, FPlayer fPlayer) {
        super(faction);
        this.player = player;
        this.fPlayer = fPlayer;
    }

    /** The player leaving the faction. */
    public Player getPlayer() {
        return player;
    }

    /** The FPlayer data of the leaving player. */
    public FPlayer getFPlayer() {
        return fPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

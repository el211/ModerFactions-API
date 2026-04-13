package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

public class FactionDTRChangeEvent extends FactionEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeReason { PLAYER_DEATH, REGEN, ADMIN_SET, FREEZE, API }

    private final double oldDTR;
    private final double newDTR;
    private final ChangeReason reason;

    public FactionDTRChangeEvent(Faction faction, double oldDTR, double newDTR, ChangeReason reason) {
        super(faction);
        this.oldDTR = oldDTR;
        this.newDTR = newDTR;
        this.reason = reason;
    }

    public double getOldDTR() {
        return oldDTR;
    }

    public double getNewDTR() {
        return newDTR;
    }

    public ChangeReason getReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

package com.oreofactions.api.events;

import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

public class FactionPowerChangeEvent extends FactionEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeReason { KILL, DEATH, REGEN, ADMIN_SET, API }

    private final FPlayer fPlayer;
    private final double oldPlayerPower;
    private final double newPlayerPower;
    private final double oldFactionPower;
    private final double newFactionPower;
    private final ChangeReason reason;

    public FactionPowerChangeEvent(Faction faction, FPlayer fPlayer,
                                   double oldPlayerPower, double newPlayerPower,
                                   double oldFactionPower, double newFactionPower,
                                   ChangeReason reason) {
        super(faction);
        this.fPlayer = fPlayer;
        this.oldPlayerPower = oldPlayerPower;
        this.newPlayerPower = newPlayerPower;
        this.oldFactionPower = oldFactionPower;
        this.newFactionPower = newFactionPower;
        this.reason = reason;
    }

    
    public FPlayer getFPlayer() {
        return fPlayer;
    }

    public double getOldPlayerPower() {
        return oldPlayerPower;
    }

    public double getNewPlayerPower() {
        return newPlayerPower;
    }

    public double getOldFactionPower() {
        return oldFactionPower;
    }

    public double getNewFactionPower() {
        return newFactionPower;
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

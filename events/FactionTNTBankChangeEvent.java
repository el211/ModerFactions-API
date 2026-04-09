package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FactionTNTBankChangeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeType { DEPOSIT, WITHDRAW, ADMIN_SET, API }

    private final int oldAmount;
    private int newAmount;
    private final ChangeType changeType;
    private final Player actor;

    public FactionTNTBankChangeEvent(Faction faction, int oldAmount, int newAmount,
                                     ChangeType changeType, Player actor) {
        super(faction);
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.changeType = changeType;
        this.actor = actor;
    }

    
    public int getOldAmount() {
        return oldAmount;
    }

    
    public int getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = Math.max(0, newAmount);
    }

    
    public ChangeType getChangeType() {
        return changeType;
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

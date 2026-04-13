package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

public class FactionPointsChangeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeReason { ADMIN_SET, MISSION_REWARD, UPGRADE_PURCHASE, SHOP_PURCHASE, API }

    private final int oldAmount;
    private int newAmount;
    private final ChangeReason reason;

    public FactionPointsChangeEvent(Faction faction, int oldAmount, int newAmount, ChangeReason reason) {
        super(faction);
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.reason = reason;
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

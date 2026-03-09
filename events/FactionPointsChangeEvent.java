package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction's points balance is about to change.
 * Cancelling prevents the change.
 */
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

    /** Points before the change. */
    public int getOldAmount() {
        return oldAmount;
    }

    /** Points after the change. Can be modified by listeners. */
    public int getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = Math.max(0, newAmount);
    }

    /** Why the points are changing. */
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

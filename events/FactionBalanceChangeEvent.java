package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction's Vault economy balance is about to change.
 * Cancelling prevents the change.
 */
public class FactionBalanceChangeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public enum ChangeType { DEPOSIT, WITHDRAW, ADMIN_SET, API }

    private final double oldBalance;
    private double newBalance;
    private final ChangeType changeType;
    private final Player actor;

    public FactionBalanceChangeEvent(Faction faction, double oldBalance, double newBalance,
                                     ChangeType changeType, Player actor) {
        super(faction);
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.changeType = changeType;
        this.actor = actor;
    }

    /** Balance before the change. */
    public double getOldBalance() {
        return oldBalance;
    }

    /** Balance after the change. Can be modified. */
    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = Math.max(0, newBalance);
    }

    /** Whether this is a deposit, withdrawal, or admin set. */
    public ChangeType getChangeType() {
        return changeType;
    }

    /** The player making the change, or null if done by API/admin. */
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

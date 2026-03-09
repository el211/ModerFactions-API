package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction's TNT bank balance is about to change.
 * Cancelling prevents the change.
 */
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

    /** TNT count before the change. */
    public int getOldAmount() {
        return oldAmount;
    }

    /** TNT count after the change. Can be modified. */
    public int getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = Math.max(0, newAmount);
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

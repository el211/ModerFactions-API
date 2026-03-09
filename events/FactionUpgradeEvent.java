package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction is about to purchase an upgrade.
 * Cancelling prevents the upgrade from being purchased.
 */
public class FactionUpgradeEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String upgradeKey;
    private final int previousLevel;
    private final int newLevel;
    private final int cost;
    private final Player buyer;

    public FactionUpgradeEvent(Faction faction, String upgradeKey, int previousLevel,
                               int newLevel, int cost, Player buyer) {
        super(faction);
        this.upgradeKey = upgradeKey;
        this.previousLevel = previousLevel;
        this.newLevel = newLevel;
        this.cost = cost;
        this.buyer = buyer;
    }

    /** The upgrade key being purchased (e.g. "members", "power", "claims"). */
    public String getUpgradeKey() {
        return upgradeKey;
    }

    /** The upgrade level before the purchase. */
    public int getPreviousLevel() {
        return previousLevel;
    }

    /** The upgrade level after the purchase. */
    public int getNewLevel() {
        return newLevel;
    }

    /** The points cost of this upgrade. */
    public int getCost() {
        return cost;
    }

    /** The player purchasing the upgrade. */
    public Player getBuyer() {
        return buyer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

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

    
    public String getUpgradeKey() {
        return upgradeKey;
    }

    
    public int getPreviousLevel() {
        return previousLevel;
    }

    
    public int getNewLevel() {
        return newLevel;
    }

    
    public int getCost() {
        return cost;
    }

    
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

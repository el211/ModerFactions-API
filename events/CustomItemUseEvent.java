package com.oreofactions.api.events;

import com.oreofactions.customitems.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemUseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private final CustomItem customItem;
    private final String interactType;
    private boolean cancelled = false;

    public CustomItemUseEvent(Player player, ItemStack item, CustomItem customItem, String interactType) {
        this.player = player;
        this.item = item;
        this.customItem = customItem;
        this.interactType = interactType;
    }

    public Player getPlayer() { return player; }
    public ItemStack getItem() { return item; }
    public CustomItem getCustomItem() { return customItem; }
    public String getInteractType() { return interactType; }

    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}

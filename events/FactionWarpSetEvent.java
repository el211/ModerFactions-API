package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Fired when a warp is set or overwritten on a faction.
 * Cancelling prevents the warp from being saved.
 */
public class FactionWarpSetEvent extends FactionCancellableEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String warpName;
    private final Location location;
    private final String password;
    private final Player setter;

    public FactionWarpSetEvent(Faction faction, String warpName, Location location,
                               String password, Player setter) {
        super(faction);
        this.warpName = warpName;
        this.location = location;
        this.password = password;
        this.setter = setter;
    }

    /** The name of the warp being set. */
    public String getWarpName() {
        return warpName;
    }

    /** The location of the new warp. */
    public Location getLocation() {
        return location;
    }

    /** The optional password for the warp, or null if none. */
    public String getPassword() {
        return password;
    }

    /** The player setting the warp. */
    public Player getSetter() {
        return setter;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

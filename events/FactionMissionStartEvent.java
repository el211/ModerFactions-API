package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction starts a new mission.
 * Not cancellable.
 */
public class FactionMissionStartEvent extends FactionEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String missionKey;
    private final String missionType;
    private final int target;

    public FactionMissionStartEvent(Faction faction, String missionKey, String missionType, int target) {
        super(faction);
        this.missionKey = missionKey;
        this.missionType = missionType;
        this.target = target;
    }

    /** The unique key for this mission instance. */
    public String getMissionKey() {
        return missionKey;
    }

    /** The mission type (e.g. "BLOCKS_BROKEN", "ENEMIES_KILLED"). */
    public String getMissionType() {
        return missionType;
    }

    /** The total progress target required to complete the mission. */
    public int getTarget() {
        return target;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

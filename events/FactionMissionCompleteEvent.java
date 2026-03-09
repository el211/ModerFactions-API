package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

/**
 * Fired when a faction completes a mission and receives its reward.
 * Not cancellable.
 */
public class FactionMissionCompleteEvent extends FactionEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String missionKey;
    private final String missionType;
    private final int pointsRewarded;

    public FactionMissionCompleteEvent(Faction faction, String missionKey,
                                       String missionType, int pointsRewarded) {
        super(faction);
        this.missionKey = missionKey;
        this.missionType = missionType;
        this.pointsRewarded = pointsRewarded;
    }

    /** The unique key for the completed mission. */
    public String getMissionKey() {
        return missionKey;
    }

    /** The mission type (e.g. "BLOCKS_BROKEN", "ENEMIES_KILLED"). */
    public String getMissionType() {
        return missionType;
    }

    /** Faction points rewarded upon completion. */
    public int getPointsRewarded() {
        return pointsRewarded;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

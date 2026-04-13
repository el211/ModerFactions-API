package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

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

    
    public String getMissionKey() {
        return missionKey;
    }

    
    public String getMissionType() {
        return missionType;
    }

    
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

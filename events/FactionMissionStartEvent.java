package com.oreofactions.api.events;

import com.oreofactions.models.Faction;
import org.bukkit.event.HandlerList;

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

    
    public String getMissionKey() {
        return missionKey;
    }

    
    public String getMissionType() {
        return missionType;
    }

    
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

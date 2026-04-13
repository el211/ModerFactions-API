package com.oreofactions.messaging;

import java.util.List;
import java.util.Map;

public class CrossServerMessage {

    public String messageType;
    public String eventType;
    public String targetPlayerUUID;
    public Map<String, String> eventData;

    public String serverId;
    public String chatType;
    public List<String> targetFactionTags;

    public String playerUUID;
    public String playerName;
    public String playerDisplayName;
    public String playerPrefix;

    public String factionTag;
    public String factionName;
    public String factionRole;
    public int    factionPoints;
    public double factionBalance;
    public double factionPower;
    public double factionMaxPower;
    public double factionDtr;
    public int    factionMembersOnline;
    public int    factionMembersTotal;
    public boolean factionRaidable;

    public String rawMessage;
    public String formattedMessage;

    public Map<String, String> papiPlaceholders;

    public CrossServerMessage() {}

    public CrossServerMessage(String serverId, String chatType, List<String> targetFactionTags,
                               String playerUUID, String playerName, String playerDisplayName,
                               String playerPrefix,
                               String factionTag, String factionName, String factionRole,
                               int factionPoints, double factionBalance,
                               double factionPower, double factionMaxPower, double factionDtr,
                               int factionMembersOnline, int factionMembersTotal, boolean factionRaidable,
                               String rawMessage, String formattedMessage,
                               Map<String, String> papiPlaceholders) {
        this.serverId           = serverId;
        this.chatType           = chatType;
        this.targetFactionTags  = targetFactionTags;
        this.playerUUID         = playerUUID;
        this.playerName         = playerName;
        this.playerDisplayName  = playerDisplayName;
        this.playerPrefix       = playerPrefix;
        this.factionTag         = factionTag;
        this.factionName        = factionName;
        this.factionRole        = factionRole;
        this.factionPoints      = factionPoints;
        this.factionBalance     = factionBalance;
        this.factionPower       = factionPower;
        this.factionMaxPower    = factionMaxPower;
        this.factionDtr         = factionDtr;
        this.factionMembersOnline = factionMembersOnline;
        this.factionMembersTotal  = factionMembersTotal;
        this.factionRaidable    = factionRaidable;
        this.rawMessage         = rawMessage;
        this.formattedMessage   = formattedMessage;
        this.papiPlaceholders   = papiPlaceholders;
    }
}

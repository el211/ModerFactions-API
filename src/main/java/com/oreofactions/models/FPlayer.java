package com.oreofactions.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FPlayer {

    private UUID uuid;
    private String name;

    private String factionId;
    private String title;

    private double power;
    private double maxPower;
    private double powerBoost;

    private int kills;
    private int deaths;
    private long lastSeen;
    private long lastPointsLoginBonusDay;

    private boolean isAlt;
    private UUID mainAccount;
    private FactionRole role = FactionRole.RECRUIT;

    private ChatMode chatMode = ChatMode.PUBLIC;
    private boolean allyChat = true;
    private boolean truceChat = true;
    private boolean loginNotifications = true;

    private boolean scoreboard = true;
    private boolean autoMap = false;
    private boolean seeChunk = false;
    private int mapHeight = 8;

    private boolean flying = false;
    private boolean autoFly = false;
    private String flyTrail;

    private Map<String, Long> cooldowns = new HashMap<>();
    private Map<String, Integer> commandUsage = new HashMap<>();
    private long lastCommandTime;

    private boolean bypassMode = false;
    private boolean chatSpy = false;

    public FPlayer() {}

    public FPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.power = 0;
        this.maxPower = 10;
        this.powerBoost = 0;
        this.kills = 0;
        this.deaths = 0;
        this.lastSeen = System.currentTimeMillis();
        this.lastPointsLoginBonusDay = -1;
        this.isAlt = false;
        this.chatMode = ChatMode.PUBLIC;
        this.allyChat = true;
        this.truceChat = true;
        this.loginNotifications = true;
        this.scoreboard = true;
        this.autoMap = false;
        this.seeChunk = false;
        this.mapHeight = 8;
        this.flying = false;
        this.autoFly = false;
        this.bypassMode = false;
        this.chatSpy = false;
        this.role = FactionRole.RECRUIT;
    }

    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFactionId() { return factionId; }
    public void setFactionId(String factionId) { this.factionId = factionId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public FactionRole getRole() { return role != null ? role : FactionRole.RECRUIT; }
    public void setRole(FactionRole role) { this.role = role != null ? role : FactionRole.RECRUIT; }

    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }

    public double getMaxPower() { return maxPower; }
    public void setMaxPower(double maxPower) { this.maxPower = maxPower; }

    public double getPowerBoost() { return powerBoost; }
    public void setPowerBoost(double powerBoost) { this.powerBoost = powerBoost; }

    public int getKills() { return kills; }
    public void setKills(int kills) { this.kills = kills; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public long getLastSeen() { return lastSeen; }
    public void setLastSeen(long lastSeen) { this.lastSeen = lastSeen; }

    public long getLastPointsLoginBonusDay() { return lastPointsLoginBonusDay; }
    public void setLastPointsLoginBonusDay(long v) { this.lastPointsLoginBonusDay = v; }

    public boolean isAlt() { return isAlt; }
    public void setAlt(boolean alt) { this.isAlt = alt; }

    public UUID getMainAccount() { return mainAccount; }
    public void setMainAccount(UUID mainAccount) { this.mainAccount = mainAccount; }

    public ChatMode getChatMode() { return chatMode != null ? chatMode : ChatMode.PUBLIC; }
    public void setChatMode(ChatMode chatMode) { this.chatMode = chatMode != null ? chatMode : ChatMode.PUBLIC; }

    public boolean isAllyChat() { return allyChat; }
    public void setAllyChat(boolean allyChat) { this.allyChat = allyChat; }

    public boolean isTruceChat() { return truceChat; }
    public void setTruceChat(boolean truceChat) { this.truceChat = truceChat; }

    public boolean isLoginNotifications() { return loginNotifications; }
    public void setLoginNotifications(boolean loginNotifications) { this.loginNotifications = loginNotifications; }

    public boolean isScoreboard() { return scoreboard; }
    public void setScoreboard(boolean scoreboard) { this.scoreboard = scoreboard; }

    public boolean isAutoMap() { return autoMap; }
    public void setAutoMap(boolean autoMap) { this.autoMap = autoMap; }

    public boolean isSeeChunk() { return seeChunk; }
    public void setSeeChunk(boolean seeChunk) { this.seeChunk = seeChunk; }

    public int getMapHeight() { return mapHeight; }
    public void setMapHeight(int mapHeight) { this.mapHeight = mapHeight; }

    public boolean isFlying() { return flying; }
    public void setFlying(boolean flying) { this.flying = flying; }

    public boolean isAutoFly() { return autoFly; }
    public void setAutoFly(boolean autoFly) { this.autoFly = autoFly; }

    public String getFlyTrail() { return flyTrail; }
    public void setFlyTrail(String flyTrail) { this.flyTrail = flyTrail; }

    public Map<String, Long> getCooldowns() {
        if (cooldowns == null) cooldowns = new HashMap<>();
        return cooldowns;
    }
    public void setCooldowns(Map<String, Long> cooldowns) {
        this.cooldowns = cooldowns != null ? cooldowns : new HashMap<>();
    }

    public Map<String, Integer> getCommandUsage() {
        if (commandUsage == null) commandUsage = new HashMap<>();
        return commandUsage;
    }
    public void setCommandUsage(Map<String, Integer> commandUsage) {
        this.commandUsage = commandUsage != null ? commandUsage : new HashMap<>();
    }

    public long getLastCommandTime() { return lastCommandTime; }
    public void setLastCommandTime(long lastCommandTime) { this.lastCommandTime = lastCommandTime; }

    public boolean isBypassMode() { return bypassMode; }
    public void setBypassMode(boolean bypassMode) { this.bypassMode = bypassMode; }

    public boolean isChatSpy() { return chatSpy; }
    public void setChatSpy(boolean chatSpy) { this.chatSpy = chatSpy; }

    public boolean hasFaction() { return factionId != null && !factionId.isEmpty(); }

    public void updateLastSeen() { this.lastSeen = System.currentTimeMillis(); }

    public boolean isOnCooldown(String command) {
        if (cooldowns == null) { cooldowns = new HashMap<>(); return false; }
        if (!cooldowns.containsKey(command)) return false;
        long end = cooldowns.get(command);
        if (System.currentTimeMillis() >= end) { cooldowns.remove(command); return false; }
        return true;
    }

    public void setCooldown(String command, long duration) {
        if (cooldowns == null) cooldowns = new HashMap<>();
        cooldowns.put(command, System.currentTimeMillis() + duration);
    }

    public long getCooldownRemaining(String command) {
        if (!isOnCooldown(command)) return 0;
        return cooldowns.get(command) - System.currentTimeMillis();
    }

    public void removeCooldown(String command) {
        if (cooldowns != null) cooldowns.remove(command);
    }

    public void clearAllCooldowns() {
        if (cooldowns != null) cooldowns.clear();
    }

    public void incrementCommandUsage(String command) {
        if (commandUsage == null) commandUsage = new HashMap<>();
        commandUsage.put(command, commandUsage.getOrDefault(command, 0) + 1);
    }

    public int getCommandUsageCount(String command) {
        if (commandUsage == null) return 0;
        return commandUsage.getOrDefault(command, 0);
    }

    public void resetCommandUsage() {
        if (commandUsage != null) commandUsage.clear();
    }

    public boolean isAdmin() { return bypassMode; }

    public String getFormattedPower() { return String.format("%.1f/%.1f", power, maxPower); }

    public double getKDRatio() {
        if (deaths == 0) return kills;
        return (double) kills / deaths;
    }

    public String getFormattedKD() { return String.format("%.2f", getKDRatio()); }

    public void addPower(double amount) { this.power = Math.min(this.power + amount, this.maxPower); }
    public void removePower(double amount) { this.power = Math.max(this.power - amount, 0); }
    public boolean hasMaxPower() { return power >= maxPower; }

    public long getTimeSinceLastSeen() { return System.currentTimeMillis() - lastSeen; }
    public boolean isOnline() { return getTimeSinceLastSeen() < 5000; }

    public String getFormattedLastSeen() {
        long diff = getTimeSinceLastSeen();
        if (diff < 60_000)     return "Just now";
        if (diff < 3_600_000)  return (diff / 60_000) + " minutes ago";
        if (diff < 86_400_000) return (diff / 3_600_000) + " hours ago";
        return (diff / 86_400_000) + " days ago";
    }

    public enum ChatMode {
        PUBLIC, FACTION, ALLY, ENEMY, TRUCE, MOD, COLEADER
    }
}

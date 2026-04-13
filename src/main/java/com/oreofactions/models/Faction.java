package com.oreofactions.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class Faction {

    private String tag;
    private String name;
    private String description;
    private String link;

    private UUID leader;
    private Set<UUID> coLeaders    = new HashSet<>();
    private Set<UUID> moderators   = new HashSet<>();
    private Set<UUID> members      = new HashSet<>();
    private Set<UUID> recruits     = new HashSet<>();
    private int chestLevel = 1;

    private Set<UUID>   alts    = new HashSet<>();
    private Set<String> allies  = new HashSet<>();
    private Set<String> enemies = new HashSet<>();
    private Set<String> truces  = new HashSet<>();
    private Set<String> claims  = new HashSet<>();

    private Map<String, String> zones = new HashMap<>();
    private Location home;
    private String homeServer;

    private double power;
    private double maxPower;
    private double powerBoost;
    private double dtr;
    private double maxDtr;
    private long   dtrFreezeTime;
    private boolean raidable;

    private Map<String, Map<String, Boolean>> memberPermissionOverrides = new HashMap<>();
    private Map<String, String>               menuAccess                = new HashMap<>();

    private double balance;
    private int    tntBank;
    private int    maxTntBank;
    private long   lastTntInterestTime;

    private int  factionPoints;
    private long lastFirstMemberLoginBonusDay;
    private long lastTopFactionBonusDay;

    private Map<String, WarpData>   warps    = new HashMap<>();
    private Map<String, Integer>    upgrades = new HashMap<>();

    private boolean open;
    private boolean peaceful;
    private boolean permanent;
    private String  defaultRole = "RECRUIT";

    private Set<String> incomingAllyRequests  = new HashSet<>();
    private Set<String> outgoingAllyRequests  = new HashSet<>();
    private Set<String> incomingTruceRequests = new HashSet<>();
    private Set<String> outgoingTruceRequests = new HashSet<>();

    private List<PermissionSelector> permissionSelectors = new ArrayList<>();

    private long    shieldEndTime;
    private boolean shieldActive;
    private long    shieldCooldownEndTime;
    private long    shieldWindowStartTime;
    private int     shieldsUsedInWindow;
    private long    graceEndTime;

    private Set<UUID> bannedPlayers = new HashSet<>();
    private Set<UUID> invites       = new HashSet<>();

    private int  kills;
    private int  deaths;
    private long creationDate;

    private String discordWebhook;
    private String discordChannelId;

    private Map<String, MissionProgress> missions = new HashMap<>();
    private List<AuditEntry>             auditLog = new ArrayList<>();
    private Set<UUID>                    reservedFor = new HashSet<>();

    public Faction() {}

    public Faction(String tag, UUID leader) {
        this.tag = tag;
        this.name = tag;
        this.leader = leader;
        this.members.add(leader);
        this.creationDate = System.currentTimeMillis();
        this.power = 0;
        this.maxPower = 10;
        this.dtr = 1.0;
        this.maxDtr = 1.0;
        this.balance = 0;
        this.tntBank = 0;
        this.maxTntBank = 1000;
        this.lastTntInterestTime = System.currentTimeMillis();
        this.factionPoints = 0;
        this.lastFirstMemberLoginBonusDay = -1;
        this.lastTopFactionBonusDay = -1;
        this.open = false;
        this.peaceful = false;
        this.permanent = false;
        this.raidable = false;
        this.shieldActive = false;
        this.shieldEndTime = 0;
        this.shieldCooldownEndTime = 0;
        this.shieldWindowStartTime = 0;
        this.shieldsUsedInWindow = 0;
        this.graceEndTime = 0;
    }

    // ── Identity ──────────────────────────────────────────────────────────────

    public String getTag()  { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getId()  { return tag; }
    public void setId(String id) { this.tag = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public long getCreationDate() { return creationDate; }
    public void setCreationDate(long creationDate) { this.creationDate = creationDate; }

    // ── Members ───────────────────────────────────────────────────────────────

    public UUID getLeader() { return leader; }
    public void setLeader(UUID leader) { this.leader = leader; }

    public Set<UUID> getCoLeaders() {
        if (coLeaders == null) coLeaders = new HashSet<>(); return coLeaders;
    }
    public void setCoLeaders(Set<UUID> v) { this.coLeaders = v != null ? v : new HashSet<>(); }

    public Set<UUID> getModerators() {
        if (moderators == null) moderators = new HashSet<>(); return moderators;
    }
    public void setModerators(Set<UUID> v) { this.moderators = v != null ? v : new HashSet<>(); }

    public Set<UUID> getMembers() {
        if (members == null) members = new HashSet<>(); return members;
    }
    public void setMembers(Set<UUID> v) { this.members = v != null ? v : new HashSet<>(); }

    public Set<UUID> getRecruits() {
        if (recruits == null) recruits = new HashSet<>(); return recruits;
    }
    public void setRecruits(Set<UUID> v) { this.recruits = v != null ? v : new HashSet<>(); }

    public Set<UUID> getAllMembers() {
        Set<UUID> all = new HashSet<>();
        if (leader != null)    all.add(leader);
        if (coLeaders != null) all.addAll(coLeaders);
        if (moderators != null) all.addAll(moderators);
        if (members != null)   all.addAll(members);
        if (recruits != null)  all.addAll(recruits);
        return all;
    }

    public int getOnlineCount() {
        return (int) getAllMembers().stream()
                .filter(uuid -> Bukkit.getPlayer(uuid) != null)
                .count();
    }

    public boolean isMember(UUID uuid) { return getAllMembers().contains(uuid); }

    public void addMember(UUID uuid) {
        if (uuid == null) return;
        if (recruits == null) recruits = new HashSet<>();
        recruits.add(uuid);
    }

    public void removeMember(UUID uuid) {
        if (uuid == null) return;
        if (coLeaders  != null) coLeaders.remove(uuid);
        if (moderators != null) moderators.remove(uuid);
        if (members    != null) members.remove(uuid);
        if (recruits   != null) recruits.remove(uuid);
    }

    public FactionRole getRole(UUID player) {
        if (player == null) return null;
        if (player.equals(leader)) return FactionRole.LEADER;
        if (coLeaders  != null && coLeaders.contains(player))  return FactionRole.COLEADER;
        if (moderators != null && moderators.contains(player)) return FactionRole.MODERATOR;
        if (members    != null && members.contains(player))    return FactionRole.MEMBER;
        if (recruits   != null && recruits.contains(player))   return FactionRole.RECRUIT;
        return null;
    }

    public void setRole(UUID player, FactionRole role) {
        if (player == null) return;
        if (coLeaders  != null) coLeaders.remove(player);
        if (moderators != null) moderators.remove(player);
        if (members    != null) members.remove(player);
        if (recruits   != null) recruits.remove(player);
        if (role == null) return;
        switch (role) {
            case LEADER -> {
                UUID old = this.leader;
                this.leader = player;
                if (old != null) { if (members == null) members = new HashSet<>(); members.add(old); }
            }
            case COLEADER  -> { if (coLeaders  == null) coLeaders  = new HashSet<>(); coLeaders.add(player); }
            case MODERATOR -> { if (moderators == null) moderators = new HashSet<>(); moderators.add(player); }
            case MEMBER    -> { if (members    == null) members    = new HashSet<>(); members.add(player); }
            case RECRUIT   -> { if (recruits   == null) recruits   = new HashSet<>(); recruits.add(player); }
        }
    }

    // ── Alts / Relations ──────────────────────────────────────────────────────

    public Set<UUID> getAlts() { if (alts == null) alts = new HashSet<>(); return alts; }
    public void setAlts(Set<UUID> v) { this.alts = v != null ? v : new HashSet<>(); }

    public Set<String> getAllies() { if (allies == null) allies = new HashSet<>(); return allies; }
    public void setAllies(Set<String> v) { this.allies = v != null ? v : new HashSet<>(); }

    public Set<String> getEnemies() { if (enemies == null) enemies = new HashSet<>(); return enemies; }
    public void setEnemies(Set<String> v) { this.enemies = v != null ? v : new HashSet<>(); }

    public Set<String> getTruces() { if (truces == null) truces = new HashSet<>(); return truces; }
    public void setTruces(Set<String> v) { this.truces = v != null ? v : new HashSet<>(); }

    // ── Claims ────────────────────────────────────────────────────────────────

    public Set<String> getClaims() { if (claims == null) claims = new HashSet<>(); return claims; }
    public void setClaims(Set<String> v) { this.claims = v != null ? v : new HashSet<>(); }
    public void addClaim(String claim) { getClaims().add(claim); }
    public void removeClaim(String claim) { getClaims().remove(claim); if (zones != null) zones.remove(claim); }

    public Map<String, String> getZones() { if (zones == null) zones = new HashMap<>(); return zones; }
    public void setZones(Map<String, String> v) { this.zones = v != null ? v : new HashMap<>(); }

    // ── Home ──────────────────────────────────────────────────────────────────

    public Location getHome() { return home; }
    public void setHome(Location home) { this.home = home; }

    public String getHomeServer() { return homeServer; }
    public void setHomeServer(String homeServer) { this.homeServer = homeServer; }

    // ── Power / DTR ───────────────────────────────────────────────────────────

    public double getPower()    { return power; }
    public void setPower(double power) { this.power = power; }

    public double getMaxPower() { return maxPower; }
    public void setMaxPower(double maxPower) { this.maxPower = maxPower; }

    public double getPowerBoost() { return powerBoost; }
    public void setPowerBoost(double powerBoost) { this.powerBoost = powerBoost; }

    public double getDtr()    { return dtr; }
    public void setDtr(double dtr) { this.dtr = dtr; }

    public double getMaxDtr() { return maxDtr; }
    public void setMaxDtr(double maxDtr) { this.maxDtr = maxDtr; }

    public long getDtrFreezeTime() { return dtrFreezeTime; }
    public void setDtrFreezeTime(long dtrFreezeTime) { this.dtrFreezeTime = dtrFreezeTime; }

    public boolean isRaidable() { return raidable; }
    public void setRaidable(boolean raidable) { this.raidable = raidable; }

    // ── Economy ───────────────────────────────────────────────────────────────

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public int getTntBank()    { return tntBank; }
    public void setTntBank(int tntBank) { this.tntBank = tntBank; }

    public int getMaxTntBank() { return maxTntBank; }
    public void setMaxTntBank(int maxTntBank) { this.maxTntBank = maxTntBank; }

    public long getLastTntInterestTime() { return lastTntInterestTime; }
    public void setLastTntInterestTime(long v) { this.lastTntInterestTime = v; }

    // ── Points ────────────────────────────────────────────────────────────────

    public int getFactionPoints() { return factionPoints; }
    public void setFactionPoints(int factionPoints) { this.factionPoints = factionPoints; }

    public long getLastFirstMemberLoginBonusDay() { return lastFirstMemberLoginBonusDay; }
    public void setLastFirstMemberLoginBonusDay(long v) { this.lastFirstMemberLoginBonusDay = v; }

    public long getLastTopFactionBonusDay() { return lastTopFactionBonusDay; }
    public void setLastTopFactionBonusDay(long v) { this.lastTopFactionBonusDay = v; }

    // ── Warps / Upgrades ──────────────────────────────────────────────────────

    public Map<String, WarpData> getWarps() { if (warps == null) warps = new HashMap<>(); return warps; }
    public void setWarps(Map<String, WarpData> v) { this.warps = v != null ? v : new HashMap<>(); }

    public Map<String, Integer> getUpgrades() { if (upgrades == null) upgrades = new HashMap<>(); return upgrades; }
    public void setUpgrades(Map<String, Integer> v) { this.upgrades = v != null ? v : new HashMap<>(); }

    public int getUpgradeLevel(String key) {
        if (key == null || key.isEmpty()) return 0;
        if (upgrades == null) upgrades = new HashMap<>();
        Integer level = upgrades.get(key.toLowerCase(Locale.ROOT));
        return level == null ? 0 : Math.max(0, level);
    }

    public void setUpgradeLevel(String key, int level) {
        if (key == null || key.isEmpty()) return;
        if (upgrades == null) upgrades = new HashMap<>();
        upgrades.put(key.toLowerCase(Locale.ROOT), Math.max(0, level));
    }

    public void addUpgradeLevel(String key, int delta) {
        setUpgradeLevel(key, getUpgradeLevel(key) + delta);
    }

    // ── Flags ─────────────────────────────────────────────────────────────────

    public boolean isOpen()      { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public boolean isPeaceful()  { return peaceful; }
    public void setPeaceful(boolean peaceful) { this.peaceful = peaceful; }

    public boolean isPermanent() { return permanent; }
    public void setPermanent(boolean permanent) { this.permanent = permanent; }

    public String getDefaultRole() { return defaultRole; }
    public void setDefaultRole(String defaultRole) { this.defaultRole = defaultRole; }

    // ── Requests ──────────────────────────────────────────────────────────────

    public Set<String> getIncomingAllyRequests()  { if (incomingAllyRequests  == null) incomingAllyRequests  = new HashSet<>(); return incomingAllyRequests; }
    public void setIncomingAllyRequests(Set<String> v)  { this.incomingAllyRequests  = v != null ? v : new HashSet<>(); }

    public Set<String> getOutgoingAllyRequests()  { if (outgoingAllyRequests  == null) outgoingAllyRequests  = new HashSet<>(); return outgoingAllyRequests; }
    public void setOutgoingAllyRequests(Set<String> v)  { this.outgoingAllyRequests  = v != null ? v : new HashSet<>(); }

    public Set<String> getIncomingTruceRequests() { if (incomingTruceRequests == null) incomingTruceRequests = new HashSet<>(); return incomingTruceRequests; }
    public void setIncomingTruceRequests(Set<String> v) { this.incomingTruceRequests = v != null ? v : new HashSet<>(); }

    public Set<String> getOutgoingTruceRequests() { if (outgoingTruceRequests == null) outgoingTruceRequests = new HashSet<>(); return outgoingTruceRequests; }
    public void setOutgoingTruceRequests(Set<String> v) { this.outgoingTruceRequests = v != null ? v : new HashSet<>(); }

    // ── Shield / Grace ────────────────────────────────────────────────────────

    public long getShieldEndTime()  { return shieldEndTime; }
    public void setShieldEndTime(long shieldEndTime) { this.shieldEndTime = shieldEndTime; }

    public boolean isShieldActive() { return shieldActive && shieldEndTime > System.currentTimeMillis(); }
    public void setShieldActive(boolean shieldActive) { this.shieldActive = shieldActive; }

    public long getShieldCooldownEndTime()  { return shieldCooldownEndTime; }
    public void setShieldCooldownEndTime(long v) { this.shieldCooldownEndTime = v; }

    public long getShieldWindowStartTime()  { return shieldWindowStartTime; }
    public void setShieldWindowStartTime(long v) { this.shieldWindowStartTime = v; }

    public int getShieldsUsedInWindow()  { return shieldsUsedInWindow; }
    public void setShieldsUsedInWindow(int v) { this.shieldsUsedInWindow = v; }

    public long getGraceEndTime()  { return graceEndTime; }
    public void setGraceEndTime(long graceEndTime) { this.graceEndTime = graceEndTime; }

    // ── Bans / Invites ────────────────────────────────────────────────────────

    public Set<UUID> getBannedPlayers() { if (bannedPlayers == null) bannedPlayers = new HashSet<>(); return bannedPlayers; }
    public void setBannedPlayers(Set<UUID> v) { this.bannedPlayers = v != null ? v : new HashSet<>(); }

    public Set<UUID> getInvites() { if (invites == null) invites = new HashSet<>(); return invites; }
    public void setInvites(Set<UUID> v) { this.invites = v != null ? v : new HashSet<>(); }

    // ── Stats ─────────────────────────────────────────────────────────────────

    public int getKills()  { return kills; }
    public void setKills(int kills) { this.kills = kills; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    // ── Discord ───────────────────────────────────────────────────────────────

    public String getDiscordWebhook()    { return discordWebhook; }
    public void setDiscordWebhook(String v) { this.discordWebhook = v; }

    public String getDiscordChannelId()  { return discordChannelId; }
    public void setDiscordChannelId(String v) { this.discordChannelId = v; }

    // ── Chest ─────────────────────────────────────────────────────────────────

    public int getChestLevel() { return Math.max(1, chestLevel); }
    public void setChestLevel(int chestLevel) { this.chestLevel = Math.max(1, chestLevel); }

    // ── Permissions ───────────────────────────────────────────────────────────

    public Map<String, Map<String, Boolean>> getMemberPermissionOverrides() { return memberPermissionOverrides; }
    public void setMemberPermissionOverrides(Map<String, Map<String, Boolean>> v) {
        this.memberPermissionOverrides = v != null ? v : new HashMap<>();
    }

    public Map<String, String> getMenuAccess() { if (menuAccess == null) menuAccess = new HashMap<>(); return menuAccess; }
    public void setMenuAccess(Map<String, String> v) { this.menuAccess = v != null ? v : new HashMap<>(); }

    public FactionRole getMenuAccessRole(String key) {
        if (menuAccess == null || !menuAccess.containsKey(key)) return FactionRole.RECRUIT;
        FactionRole role = FactionRole.fromString(menuAccess.get(key));
        return role != null ? role : FactionRole.RECRUIT;
    }

    public void setMenuAccessRole(String key, FactionRole role) {
        if (menuAccess == null) menuAccess = new HashMap<>();
        if (role == null || role == FactionRole.RECRUIT) menuAccess.remove(key);
        else menuAccess.put(key, role.name());
    }

    public List<PermissionSelector> getPermissionSelectors() {
        if (permissionSelectors == null) permissionSelectors = new ArrayList<>(); return permissionSelectors;
    }
    public void setPermissionSelectors(List<PermissionSelector> v) {
        this.permissionSelectors = v != null ? v : new ArrayList<>();
    }

    // ── Missions / Audit / Reserved ───────────────────────────────────────────

    public Map<String, MissionProgress> getMissions() { if (missions == null) missions = new HashMap<>(); return missions; }
    public void setMissions(Map<String, MissionProgress> v) { this.missions = v != null ? v : new HashMap<>(); }

    public List<AuditEntry> getAuditLog() { if (auditLog == null) auditLog = new ArrayList<>(); return auditLog; }
    public void setAuditLog(List<AuditEntry> v) { this.auditLog = v != null ? v : new ArrayList<>(); }

    public Set<UUID> getReservedFor() { if (reservedFor == null) reservedFor = new HashSet<>(); return reservedFor; }
    public void setReservedFor(Set<UUID> v) { this.reservedFor = v != null ? v : new HashSet<>(); }

    // ── Nested types ──────────────────────────────────────────────────────────

    public static class WarpData {
        private Location location;
        private String   password;
        private String   server;

        public WarpData() {}
        public WarpData(Location location) { this.location = location; }
        public WarpData(Location location, String password) { this.location = location; this.password = password; }

        public Location getLocation() { return location; }
        public void setLocation(Location location) { this.location = location; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public boolean hasPassword() { return password != null && !password.isEmpty(); }

        public String getServer() { return server; }
        public void setServer(String server) { this.server = server; }
    }

    public static class PermissionSelector {
        private String type;
        private String value;
        private Map<String, Boolean> permissions = new HashMap<>();
        private int priority;

        public String getType()   { return type; }
        public void setType(String type) { this.type = type; }

        public String getValue()  { return value; }
        public void setValue(String value) { this.value = value; }

        public Map<String, Boolean> getPermissions() { if (permissions == null) permissions = new HashMap<>(); return permissions; }
        public void setPermissions(Map<String, Boolean> permissions) { this.permissions = permissions != null ? permissions : new HashMap<>(); }

        public int getPriority()  { return priority; }
        public void setPriority(int priority) { this.priority = priority; }
    }

    public static class MissionProgress {
        private String  missionId;
        private int     progress;
        private int     target;
        private boolean completed;
        private long    startTime;

        public String getMissionId()   { return missionId; }
        public void setMissionId(String missionId) { this.missionId = missionId; }

        public int getProgress()       { return progress; }
        public void setProgress(int progress) { this.progress = progress; }

        public int getTarget()         { return target; }
        public void setTarget(int target) { this.target = target; }

        public boolean isCompleted()   { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }

        public long getStartTime()     { return startTime; }
        public void setStartTime(long startTime) { this.startTime = startTime; }
    }

    public static class AuditEntry {
        private long   timestamp;
        private UUID   actor;
        private String action;
        private String details;

        public AuditEntry() {}

        public AuditEntry(UUID actor, String action, String details) {
            this.timestamp = System.currentTimeMillis();
            this.actor     = actor;
            this.action    = action;
            this.details   = details;
        }

        public long getTimestamp()   { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

        public UUID getActor()       { return actor; }
        public void setActor(UUID actor) { this.actor = actor; }

        public String getAction()    { return action; }
        public void setAction(String action) { this.action = action; }

        public String getDetails()   { return details; }
        public void setDetails(String details) { this.details = details; }
    }
}

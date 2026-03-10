package com.oreofactions.api;

import com.oreofactions.OreoFactionsPlugin;
import com.oreofactions.api.events.*;
import com.oreofactions.customitems.CustomItem;
import com.oreofactions.customitems.CustomItemManager;
import com.oreofactions.database.StorageProvider;
import com.oreofactions.features.*;
import com.oreofactions.managers.*;
import com.oreofactions.messaging.CrossServerMessage;
import com.oreofactions.messaging.RabbitMQManager;
import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import com.oreofactions.models.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * OreoFactions public API.
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * OreoFactionsAPI api = OreoFactionsAPI.get();
 *
 * // Faction lookup
 * Faction f = api.getFaction("MyTag");
 *
 * // Player lookup
 * FPlayer fp = api.getFPlayer(player.getUniqueId());
 *
 * // Power
 * api.addPlayerPower(fp, 5.0);
 *
 * // Points
 * api.addFactionPoints(faction, 100);
 * }</pre>
 *
 * <h2>Events</h2>
 * All significant actions fire a Bukkit event in the
 * {@code com.oreofactions.api.events} package.
 * Cancellable events can be cancelled to block the action.
 */
public final class OreoFactionsAPI {

    private static OreoFactionsAPI instance;

    private final OreoFactionsPlugin plugin;

    private OreoFactionsAPI(OreoFactionsPlugin plugin) {
        this.plugin = plugin;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bootstrap
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Called internally by OreoFactionsPlugin during onEnable.
     * External plugins should use {@link #get()} instead.
     */
    public static void init(OreoFactionsPlugin plugin) {
        if (instance == null) {
            instance = new OreoFactionsAPI(plugin);
        }
    }

    /**
     * Returns the API instance.
     * Returns null if OreoFactions has not finished loading yet.
     */
    public static OreoFactionsAPI get() {
        if (instance == null) {
            OreoFactionsPlugin p = (OreoFactionsPlugin) Bukkit.getPluginManager().getPlugin("OreoFactions");
            if (p != null) instance = new OreoFactionsAPI(p);
        }
        return instance;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Raw manager access
    // ─────────────────────────────────────────────────────────────────────────

    /** Direct access to the plugin's FactionManager. */
    public FactionManager getFactionManager() {
        return plugin.getFactionManager();
    }

    /** Direct access to the plugin's PlayerManager. */
    public PlayerManager getPlayerManager() {
        return plugin.getPlayerManager();
    }

    /** Direct access to the plugin's ClaimManager. */
    public ClaimManager getClaimManager() {
        return plugin.getClaimManager();
    }

    /** Direct access to the plugin's RelationManager. */
    public RelationManager getRelationManager() {
        return plugin.getRelationManager();
    }

    /** Direct access to the plugin's PowerManager. */
    public PowerManager getPowerManager() {
        return plugin.getPowerManager();
    }

    /** Direct access to the plugin's CombatManager. */
    public CombatManager getCombatManager() {
        return plugin.getCombatManager();
    }

    /** Direct access to the plugin's PointsManager. */
    public PointsManager getPointsManager() {
        return plugin.getPointsManager();
    }

    /** Direct access to the plugin's UpgradeManager. */
    public UpgradeManager getUpgradeManager() {
        return plugin.getUpgradeManager();
    }

    /** Direct access to the plugin's MissionManager. */
    public MissionManager getMissionManager() {
        return plugin.getMissionManager();
    }

    /** Direct access to the plugin's GraceManager. */
    public GraceManager getGraceManager() {
        return plugin.getGraceManager();
    }

    /** Direct access to the plugin's AuditManager. */
    public AuditManager getAuditManager() {
        return plugin.getAuditManager();
    }

    /** Direct access to the plugin's AltManager. */
    public AltManager getAltManager() {
        return plugin.getAltManager();
    }

    /** Direct access to the plugin's DiscordManager. */
    public DiscordManager getDiscordManager() {
        return plugin.getDiscordManager();
    }

    /** Direct access to the storage backend (MongoDB or SQLite). */
    public StorageProvider getStorageProvider() {
        return plugin.getStorageProvider();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Faction queries
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the faction with the given tag (case-insensitive), or null.
     */
    public Faction getFaction(String tag) {
        return plugin.getFactionManager().getFaction(tag);
    }

    /**
     * Returns all factions currently loaded.
     */
    public Collection<Faction> getAllFactions() {
        return plugin.getFactionManager().getAllFactions();
    }

    /**
     * Returns the top N factions sorted by power (descending).
     */
    public List<Faction> getTopFactionsByPower(int limit) {
        return plugin.getFactionManager().getTopFactionsByPower(limit);
    }

    /**
     * Returns the top N factions sorted by faction points (descending).
     */
    public List<Faction> getTopFactionsByPoints(int limit) {
        return plugin.getFactionManager().getTopFactionsByPoints(limit);
    }

    /**
     * Returns whether a faction with the given tag exists.
     */
    public boolean factionExists(String tag) {
        return plugin.getFactionManager().factionExists(tag);
    }

    /**
     * Returns the total number of factions.
     */
    public int getFactionCount() {
        return plugin.getFactionManager().getFactionCount();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Faction creation / disband
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Creates a new faction programmatically.
     * Fires {@link FactionCreateEvent} — returns null if cancelled.
     *
     * @param tag    Faction tag (unique identifier).
     * @param leader UUID of the leader.
     * @param player The player creating the faction, or null.
     * @return The new Faction, or null if creation was cancelled or invalid.
     */
    public Faction createFaction(String tag, UUID leader, Player player) {
        Faction dummy = new Faction(tag, leader);
        FactionCreateEvent event = new FactionCreateEvent(dummy, leader, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return null;

        return plugin.getFactionManager().createFaction(tag, leader);
    }

    /**
     * Disbands a faction programmatically.
     * Fires {@link FactionDisbandEvent} — does nothing if cancelled.
     *
     * @param faction Faction to disband.
     * @param player  Player performing the disband, or null.
     * @param reason  Reason for the disband.
     * @return true if disbanded, false if cancelled.
     */
    public boolean disbandFaction(Faction faction, Player player, FactionDisbandEvent.Reason reason) {
        FactionDisbandEvent event = new FactionDisbandEvent(faction, player, reason);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getFactionManager().disbandFaction(faction);
        return true;
    }

    /**
     * Saves a faction's current state to storage immediately.
     */
    public void saveFaction(Faction faction) {
        plugin.getFactionManager().saveFaction(faction);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Player (FPlayer) queries
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the FPlayer for the given UUID, or null if not loaded.
     */
    public FPlayer getFPlayer(UUID uuid) {
        return plugin.getPlayerManager().getFPlayer(uuid);
    }

    /**
     * Returns the FPlayer for an online player, auto-creating one if not yet loaded.
     */
    public FPlayer getOrCreateFPlayer(Player player) {
        return plugin.getPlayerManager().getFPlayer(player.getUniqueId());
    }

    /**
     * Returns all loaded FPlayers.
     */
    public Collection<FPlayer> getAllFPlayers() {
        return plugin.getPlayerManager().getAllPlayers();
    }

    /**
     * Returns the faction a player currently belongs to, or null.
     */
    public Faction getPlayerFaction(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        if (fp == null || !fp.hasFaction()) return null;
        return getFaction(fp.getFactionId());
    }

    /**
     * Saves a player's current state to storage immediately.
     */
    public void saveFPlayer(FPlayer fPlayer) {
        plugin.getPlayerManager().saveFPlayer(fPlayer);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Membership
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Adds a player to a faction.
     * Fires {@link FactionJoinEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean addMember(Faction faction, Player player) {
        FPlayer fp = getOrCreateFPlayer(player);
        FactionJoinEvent event = new FactionJoinEvent(faction, player, fp);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getMembers().add(player.getUniqueId());
        fp.setFactionId(faction.getTag());
        fp.setRole(FactionRole.MEMBER);
        saveFaction(faction);
        saveFPlayer(fp);
        return true;
    }

    /**
     * Removes a player from their faction (leave).
     * Fires {@link FactionLeaveEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean removeMember(Faction faction, Player player) {
        FPlayer fp = getFPlayer(player.getUniqueId());
        if (fp == null) return false;

        FactionLeaveEvent event = new FactionLeaveEvent(faction, player, fp);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.removeMember(player.getUniqueId());
        fp.setFactionId(null);
        fp.setRole(FactionRole.RECRUIT);
        saveFaction(faction);
        saveFPlayer(fp);
        return true;
    }

    /**
     * Kicks a player from a faction.
     * Fires {@link FactionKickEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean kickMember(Faction faction, UUID kicked, Player kicker) {
        FPlayer fp = getFPlayer(kicked);
        FactionKickEvent event = new FactionKickEvent(faction, kicked, fp, kicker);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.removeMember(kicked);
        if (fp != null) {
            fp.setFactionId(null);
            fp.setRole(FactionRole.RECRUIT);
            saveFPlayer(fp);
        }
        saveFaction(faction);
        return true;
    }

    /**
     * Bans a player from a faction.
     * Fires {@link FactionBanEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean banPlayer(Faction faction, UUID target, Player banner) {
        FactionBanEvent event = new FactionBanEvent(faction, target, banner);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getBannedPlayers().add(target);
        saveFaction(faction);
        return true;
    }

    /**
     * Invites a player to a faction.
     * Fires {@link FactionInviteEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean invitePlayer(Faction faction, UUID invited, Player inviter) {
        FactionInviteEvent event = new FactionInviteEvent(faction, invited, inviter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getInvites().add(invited);
        saveFaction(faction);
        return true;
    }

    /**
     * Changes a faction member's role.
     * Fires {@link FactionRoleChangeEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean setRole(Faction faction, UUID target, FactionRole newRole, Player actor) {
        FPlayer fp = getFPlayer(target);
        FactionRole oldRole = (fp != null) ? fp.getRole() : faction.getRole(target);
        FactionRoleChangeEvent event = new FactionRoleChangeEvent(faction, target, oldRole, newRole, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setRole(target, newRole);
        if (fp != null) {
            fp.setRole(newRole);
            saveFPlayer(fp);
        }
        saveFaction(faction);
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Claims
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Claims a chunk for a faction.
     * Fires {@link FactionClaimEvent} — does nothing if cancelled.
     *
     * @return true if claimed successfully.
     */
    public boolean claim(Faction faction, Chunk chunk, Player player) {
        FactionClaimEvent event = new FactionClaimEvent(faction, chunk, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        return plugin.getClaimManager().claim(chunk, faction);
    }

    /**
     * Unclaims a chunk from a faction using its claim key ("world:chunkX:chunkZ").
     * Fires {@link FactionUnclaimEvent} — does nothing if cancelled.
     *
     * @return true if unclaimed.
     */
    public boolean unclaim(Faction faction, String claimKey, Player player) {
        FactionUnclaimEvent event = new FactionUnclaimEvent(faction, claimKey, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getClaimManager().unclaim(claimKey);
        return true;
    }

    /**
     * Returns whether a chunk is claimed by any faction.
     */
    public boolean isClaimed(Chunk chunk) {
        return plugin.getClaimManager().isClaimed(chunk);
    }

    /**
     * Returns the faction that owns the chunk at the given location, or null.
     */
    public Faction getFactionAt(Location location) {
        return plugin.getClaimManager().getFactionAt(location);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Relations
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Sets the relation between two factions.
     * Fires {@link FactionRelationChangeEvent} — does nothing if cancelled.
     *
     * @param relation ALLY, ENEMY, TRUCE, or NEUTRAL.
     * @return true if relation was changed.
     */
    public boolean setRelation(Faction from, Faction to,
                               FactionRelationChangeEvent.RelationType relation,
                               Player actor) {
        FactionRelationChangeEvent.RelationType current = getCurrentRelation(from, to);
        FactionRelationChangeEvent event = new FactionRelationChangeEvent(from, to, current, relation, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        applyRelation(from, to, relation);
        saveFaction(from);
        saveFaction(to);
        return true;
    }

    private FactionRelationChangeEvent.RelationType getCurrentRelation(Faction a, Faction b) {
        if (a.getAllies().contains(b.getTag())) return FactionRelationChangeEvent.RelationType.ALLY;
        if (a.getEnemies().contains(b.getTag())) return FactionRelationChangeEvent.RelationType.ENEMY;
        if (a.getTruces().contains(b.getTag())) return FactionRelationChangeEvent.RelationType.TRUCE;
        return FactionRelationChangeEvent.RelationType.NEUTRAL;
    }

    private void applyRelation(Faction from, Faction to,
                               FactionRelationChangeEvent.RelationType relation) {
        String fromTag = from.getTag();
        String toTag = to.getTag();

        // Remove from all relation lists first (both sides)
        from.getAllies().remove(toTag);
        from.getEnemies().remove(toTag);
        from.getTruces().remove(toTag);
        to.getAllies().remove(fromTag);
        to.getEnemies().remove(fromTag);
        to.getTruces().remove(fromTag);

        switch (relation) {
            case ALLY -> {
                from.getAllies().add(toTag);
                to.getAllies().add(fromTag);
            }
            case ENEMY -> {
                from.getEnemies().add(toTag);
                to.getEnemies().add(fromTag);
            }
            case TRUCE -> {
                from.getTruces().add(toTag);
                to.getTruces().add(fromTag);
            }
            case NEUTRAL -> { /* already removed above */ }
        }
    }

    /** Returns whether the two factions are allies. */
    public boolean isAlly(Faction a, Faction b) {
        return plugin.getRelationManager().isAlly(a, b);
    }

    /** Returns whether the two factions are enemies. */
    public boolean isEnemy(Faction a, Faction b) {
        return plugin.getRelationManager().isEnemy(a, b);
    }

    /** Returns whether the two factions have a truce. */
    public boolean isTruce(Faction a, Faction b) {
        return plugin.getRelationManager().isTruce(a, b);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Power
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a player's current power.
     */
    public double getPlayerPower(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        return fp == null ? 0 : plugin.getPowerManager().getPlayerPower(fp);
    }

    /**
     * Adds power to a player (and recalculates faction power).
     * Fires {@link FactionPowerChangeEvent}.
     */
    public void addPlayerPower(FPlayer fPlayer, double amount) {
        if (fPlayer == null) return;
        Faction faction = getPlayerFaction(fPlayer.getUuid());

        double oldPlayerPower = fPlayer.getPower();
        double oldFactionPower = faction != null ? faction.getPower() : 0;

        plugin.getPowerManager().addPlayerPower(fPlayer, amount);
        if (faction != null) plugin.getPowerManager().updateFactionPower(fPlayer);

        double newFactionPower = faction != null ? faction.getPower() : 0;
        FactionPowerChangeEvent event = new FactionPowerChangeEvent(
                faction, fPlayer, oldPlayerPower, fPlayer.getPower(),
                oldFactionPower, newFactionPower, FactionPowerChangeEvent.ChangeReason.API);
        Bukkit.getPluginManager().callEvent(event);
    }

    /**
     * Removes power from a player (and recalculates faction power).
     * Fires {@link FactionPowerChangeEvent}.
     */
    public void removePlayerPower(FPlayer fPlayer, double amount) {
        if (fPlayer == null) return;
        Faction faction = getPlayerFaction(fPlayer.getUuid());

        double oldPlayerPower = fPlayer.getPower();
        double oldFactionPower = faction != null ? faction.getPower() : 0;

        plugin.getPowerManager().removePlayerPower(fPlayer, amount);
        if (faction != null) plugin.getPowerManager().updateFactionPower(fPlayer);

        double newFactionPower = faction != null ? faction.getPower() : 0;
        FactionPowerChangeEvent event = new FactionPowerChangeEvent(
                faction, fPlayer, oldPlayerPower, fPlayer.getPower(),
                oldFactionPower, newFactionPower, FactionPowerChangeEvent.ChangeReason.API);
        Bukkit.getPluginManager().callEvent(event);
    }

    /**
     * Sets a player's power directly (and recalculates faction power).
     * Fires {@link FactionPowerChangeEvent}.
     */
    public void setPlayerPower(FPlayer fPlayer, double amount) {
        if (fPlayer == null) return;
        Faction faction = getPlayerFaction(fPlayer.getUuid());

        double oldPlayerPower = fPlayer.getPower();
        double oldFactionPower = faction != null ? faction.getPower() : 0;

        fPlayer.setPower(amount);
        plugin.getPlayerManager().saveFPlayer(fPlayer);
        if (faction != null) plugin.getPowerManager().updateFactionPower(fPlayer);

        double newFactionPower = faction != null ? faction.getPower() : 0;
        FactionPowerChangeEvent event = new FactionPowerChangeEvent(
                faction, fPlayer, oldPlayerPower, amount,
                oldFactionPower, newFactionPower, FactionPowerChangeEvent.ChangeReason.ADMIN_SET);
        Bukkit.getPluginManager().callEvent(event);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DTR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Sets a faction's DTR directly.
     * Fires {@link FactionDTRChangeEvent} and {@link FactionRaidableEvent} if DTR drops to 0.
     */
    public void setDTR(Faction faction, double newDtr) {
        if (faction == null) return;
        double oldDtr = faction.getDtr();
        faction.setDtr(newDtr);
        boolean nowRaidable = newDtr <= 0;
        faction.setRaidable(nowRaidable);
        saveFaction(faction);

        Bukkit.getPluginManager().callEvent(
                new FactionDTRChangeEvent(faction, oldDtr, newDtr, FactionDTRChangeEvent.ChangeReason.ADMIN_SET));

        if (nowRaidable && !faction.isRaidable()) {
            Bukkit.getPluginManager().callEvent(new FactionRaidableEvent(faction));
        }
    }

    /**
     * Freezes a faction's DTR so it cannot regenerate.
     * Fires {@link FactionDTRChangeEvent} with reason FREEZE.
     */
    public void freezeDTR(Faction faction) {
        if (faction == null) return;
        double oldDtr = faction.getDtr();
        plugin.getFactionManager().freezeDTR(faction);
        Bukkit.getPluginManager().callEvent(
                new FactionDTRChangeEvent(faction, oldDtr, faction.getDtr(), FactionDTRChangeEvent.ChangeReason.FREEZE));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Points
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a faction's current points balance.
     */
    public int getFactionPoints(Faction faction) {
        return plugin.getPointsManager().getPoints(faction);
    }

    /**
     * Adds points to a faction.
     * Fires {@link FactionPointsChangeEvent} — does nothing if cancelled.
     */
    public void addFactionPoints(Faction faction, int amount) {
        if (faction == null) return;
        int oldAmount = faction.getFactionPoints();
        int newAmount = oldAmount + amount;

        FactionPointsChangeEvent event = new FactionPointsChangeEvent(
                faction, oldAmount, newAmount, FactionPointsChangeEvent.ChangeReason.API);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        plugin.getPointsManager().setPoints(faction, event.getNewAmount());
    }

    /**
     * Removes points from a faction.
     * Fires {@link FactionPointsChangeEvent} — does nothing if cancelled.
     *
     * @return true if faction had enough points and points were removed.
     */
    public boolean removeFactionPoints(Faction faction, int amount) {
        if (faction == null) return false;
        int oldAmount = faction.getFactionPoints();
        if (oldAmount < amount) return false;

        int newAmount = oldAmount - amount;
        FactionPointsChangeEvent event = new FactionPointsChangeEvent(
                faction, oldAmount, newAmount, FactionPointsChangeEvent.ChangeReason.API);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getPointsManager().setPoints(faction, event.getNewAmount());
        return true;
    }

    /**
     * Sets a faction's points directly.
     * Fires {@link FactionPointsChangeEvent} — does nothing if cancelled.
     */
    public void setFactionPoints(Faction faction, int amount) {
        if (faction == null) return;
        int oldAmount = faction.getFactionPoints();
        FactionPointsChangeEvent event = new FactionPointsChangeEvent(
                faction, oldAmount, Math.max(0, amount), FactionPointsChangeEvent.ChangeReason.ADMIN_SET);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        plugin.getPointsManager().setPoints(faction, event.getNewAmount());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Economy (Vault balance)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a faction's economy balance.
     */
    public double getFactionBalance(Faction faction) {
        return faction == null ? 0 : faction.getBalance();
    }

    /**
     * Deposits money into a faction's balance.
     * Fires {@link FactionBalanceChangeEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean depositBalance(Faction faction, double amount, Player actor) {
        if (faction == null || amount < 0) return false;
        double oldBalance = faction.getBalance();
        FactionBalanceChangeEvent event = new FactionBalanceChangeEvent(
                faction, oldBalance, oldBalance + amount, FactionBalanceChangeEvent.ChangeType.DEPOSIT, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setBalance(event.getNewBalance());
        saveFaction(faction);
        return true;
    }

    /**
     * Withdraws money from a faction's balance.
     * Fires {@link FactionBalanceChangeEvent} — does nothing if cancelled.
     *
     * @return true if faction had enough funds and amount was withdrawn.
     */
    public boolean withdrawBalance(Faction faction, double amount, Player actor) {
        if (faction == null || amount < 0) return false;
        double oldBalance = faction.getBalance();
        if (oldBalance < amount) return false;

        FactionBalanceChangeEvent event = new FactionBalanceChangeEvent(
                faction, oldBalance, oldBalance - amount, FactionBalanceChangeEvent.ChangeType.WITHDRAW, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setBalance(event.getNewBalance());
        saveFaction(faction);
        return true;
    }

    /**
     * Sets a faction's balance directly (admin).
     * Fires {@link FactionBalanceChangeEvent} — does nothing if cancelled.
     */
    public void setBalance(Faction faction, double amount, Player actor) {
        if (faction == null) return;
        double oldBalance = faction.getBalance();
        FactionBalanceChangeEvent event = new FactionBalanceChangeEvent(
                faction, oldBalance, Math.max(0, amount), FactionBalanceChangeEvent.ChangeType.ADMIN_SET, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        faction.setBalance(event.getNewBalance());
        saveFaction(faction);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TNT Bank
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a faction's TNT bank count.
     */
    public int getTNTBank(Faction faction) {
        return faction == null ? 0 : faction.getTntBank();
    }

    /**
     * Deposits TNT into a faction's bank.
     * Fires {@link FactionTNTBankChangeEvent} — does nothing if cancelled.
     *
     * @return true if successful (not exceeding max).
     */
    public boolean depositTNT(Faction faction, int amount, Player actor) {
        if (faction == null || amount < 0) return false;
        int oldAmount = faction.getTntBank();
        int max = faction.getMaxTntBank();
        if (oldAmount >= max) return false;

        int newAmount = Math.min(oldAmount + amount, max);
        FactionTNTBankChangeEvent event = new FactionTNTBankChangeEvent(
                faction, oldAmount, newAmount, FactionTNTBankChangeEvent.ChangeType.DEPOSIT, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setTntBank(event.getNewAmount());
        saveFaction(faction);
        return true;
    }

    /**
     * Withdraws TNT from a faction's bank.
     * Fires {@link FactionTNTBankChangeEvent} — does nothing if cancelled.
     *
     * @return true if successful.
     */
    public boolean withdrawTNT(Faction faction, int amount, Player actor) {
        if (faction == null || amount < 0) return false;
        int oldAmount = faction.getTntBank();
        if (oldAmount < amount) return false;

        FactionTNTBankChangeEvent event = new FactionTNTBankChangeEvent(
                faction, oldAmount, oldAmount - amount, FactionTNTBankChangeEvent.ChangeType.WITHDRAW, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setTntBank(event.getNewAmount());
        saveFaction(faction);
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Combat tagging
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Combat-tags a player.
     * Fires {@link FactionCombatTagEvent} — does nothing if cancelled.
     */
    public void combatTag(Player player, Player attacker) {
        if (player == null) return;
        Faction faction = getPlayerFaction(player.getUniqueId());
        long durationMs = plugin.getConfig().getLong("fly.combat-tag-duration", 15) * 1000L;

        FactionCombatTagEvent event = new FactionCombatTagEvent(faction, player, attacker, durationMs);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        plugin.getCombatManager().tag(player);
    }

    /**
     * Returns whether a player is currently combat-tagged.
     */
    public boolean isInCombat(UUID uuid) {
        return plugin.getCombatManager().isTagged(uuid);
    }

    /**
     * Returns the remaining combat tag duration in seconds, or 0 if not tagged.
     */
    public long getCombatTagSecondsRemaining(UUID uuid) {
        return plugin.getCombatManager().getTagRemainingSeconds(uuid);
    }

    /**
     * Removes a player's combat tag.
     */
    public void removeCombatTag(UUID uuid) {
        plugin.getCombatManager().untag(uuid);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Shield
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Activates a shield on a faction.
     * Fires {@link FactionShieldActivateEvent} — does nothing if cancelled.
     *
     * @return true if shield was activated.
     */
    public boolean activateShield(Faction faction, long durationMs, Player activator) {
        if (faction == null) return false;
        FactionShieldActivateEvent event = new FactionShieldActivateEvent(faction, activator, durationMs);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setShieldActive(true);
        faction.setShieldEndTime(System.currentTimeMillis() + durationMs);
        saveFaction(faction);
        return true;
    }

    /**
     * Returns whether a faction's shield is currently active.
     */
    public boolean hasShield(Faction faction) {
        return faction != null && faction.isShieldActive()
                && faction.getShieldEndTime() > System.currentTimeMillis();
    }

    /**
     * Deactivates a faction's shield.
     */
    public void deactivateShield(Faction faction) {
        if (faction == null) return;
        faction.setShieldActive(false);
        faction.setShieldEndTime(0);
        saveFaction(faction);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Grace period
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Activates a grace period for a faction.
     * Fires {@link FactionGraceActivateEvent} — does nothing if cancelled.
     *
     * @return true if grace was activated.
     */
    public boolean activateGrace(Faction faction, long durationMs, Player activator) {
        if (faction == null) return false;
        FactionGraceActivateEvent event = new FactionGraceActivateEvent(faction, activator, durationMs);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getGraceManager().setGrace(faction, durationMs);
        return true;
    }

    /**
     * Returns whether a faction is currently in a grace period.
     */
    public boolean hasGrace(Faction faction) {
        return plugin.getGraceManager().hasGrace(faction);
    }

    /**
     * Returns grace period milliseconds remaining, or 0 if not in grace.
     */
    public long getGraceRemainingMs(Faction faction) {
        return plugin.getGraceManager().getGraceRemaining(faction);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Upgrades
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns a faction's current level for the given upgrade key.
     */
    public int getUpgradeLevel(Faction faction, String upgradeKey) {
        return plugin.getUpgradeManager().getLevel(faction, upgradeKey);
    }

    /**
     * Attempts to purchase the next level of an upgrade.
     * Fires {@link FactionUpgradeEvent} — does nothing if cancelled.
     *
     * @return true if purchased successfully.
     */
    public boolean purchaseUpgrade(Faction faction, String upgradeKey, Player buyer) {
        UpgradeManager um = plugin.getUpgradeManager();
        int currentLevel = um.getLevel(faction, upgradeKey);
        int maxLevel = um.getMaxLevel(upgradeKey);
        if (currentLevel >= maxLevel) return false;

        int cost = um.getCostForNextLevel(upgradeKey, currentLevel);
        int newLevel = currentLevel + 1;

        FactionUpgradeEvent event = new FactionUpgradeEvent(faction, upgradeKey, currentLevel, newLevel, cost, buyer);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        return um.attemptUpgrade(buyer, faction, upgradeKey);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Warps
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Sets a warp for a faction.
     * Fires {@link FactionWarpSetEvent} — does nothing if cancelled.
     *
     * @return true if warp was set.
     */
    public boolean setWarp(Faction faction, String name, Location location,
                           String password, Player setter) {
        FactionWarpSetEvent event = new FactionWarpSetEvent(faction, name, location, password, setter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        Faction.WarpData warp = new Faction.WarpData(location, password);
        // Record which server this warp is on for cross-server teleport support
        if (plugin.getRabbitMQManager() != null) {
            warp.setServer(plugin.getRabbitMQManager().getServerId());
        }
        faction.getWarps().put(name, warp);
        saveFaction(faction);
        return true;
    }

    /**
     * Deletes a warp from a faction.
     * Fires {@link FactionWarpDeleteEvent} — does nothing if cancelled.
     *
     * @return true if warp was deleted.
     */
    public boolean deleteWarp(Faction faction, String name, Player actor) {
        if (!faction.getWarps().containsKey(name)) return false;

        FactionWarpDeleteEvent event = new FactionWarpDeleteEvent(faction, name, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getWarps().remove(name);
        saveFaction(faction);
        return true;
    }

    /**
     * Teleports a player to a faction warp.
     * Fires {@link FactionWarpTeleportEvent} — does nothing if cancelled.
     *
     * @return true if teleport occurred.
     */
    public boolean teleportToWarp(Faction faction, String warpName, Player player) {
        if (faction == null || player == null) return false;
        Faction.WarpData warp = faction.getWarps().get(warpName);
        if (warp == null) return false;

        FactionWarpTeleportEvent event = new FactionWarpTeleportEvent(
                faction, player, warpName, warp.getLocation());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        player.teleport(event.getDestination());
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Home
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Sets a faction's home location.
     * Fires {@link FactionHomeSetEvent} — does nothing if cancelled.
     *
     * @return true if home was set.
     */
    public boolean setHome(Faction faction, Location location, Player setter) {
        if (faction == null) return false;
        FactionHomeSetEvent event = new FactionHomeSetEvent(faction, faction.getHome(), location, setter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setHome(location);
        // Record which server the home is on for cross-server teleport support
        if (plugin.getRabbitMQManager() != null) {
            faction.setHomeServer(plugin.getRabbitMQManager().getServerId());
        }
        saveFaction(faction);
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Permissions
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns whether a player has the specified faction permission.
     */
    public boolean hasPermission(Faction faction, UUID player, String permKey) {
        return plugin.getPermissionManager().has(faction, player, permKey);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Missions
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Manually records a block mine (break) for mission tracking.
     */
    public void recordBlockMine(Faction faction, org.bukkit.Material material) {
        plugin.getMissionManager().recordBlockMine(faction, material);
    }

    /**
     * Manually records a kill for mission tracking.
     */
    public void recordKill(Faction faction) {
        plugin.getMissionManager().recordKill(faction);
    }

    /**
     * Manually records an enemy kill for mission tracking.
     */
    public void recordEnemyKill(Faction attacker, Faction victim) {
        plugin.getMissionManager().recordEnemyKill(attacker, victim);
    }

    /**
     * Manually records a claim for mission tracking.
     */
    public void recordClaim(Faction faction) {
        plugin.getMissionManager().recordClaim(faction);
    }

    /**
     * Starts a specific mission for a faction by key.
     *
     * @return true if mission started successfully.
     */
    public boolean startMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().startMission(faction, missionKey);
    }

    /**
     * Cancels an active mission for a faction.
     *
     * @return true if cancelled.
     */
    public boolean cancelMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().cancelMission(faction, missionKey);
    }

    /**
     * Returns whether a specific mission is currently active for a faction.
     */
    public boolean isMissionActive(Faction faction, String missionKey) {
        return plugin.getMissionManager().isMissionActive(faction, missionKey);
    }

    /**
     * Returns the current progress for a mission.
     */
    public int getMissionProgress(Faction faction, String missionKey) {
        return plugin.getMissionManager().getProgress(faction, missionKey);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Audit logging
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Logs an action to a faction's audit log.
     */
    public void auditLog(Faction faction, UUID actor, String action, String details) {
        plugin.getAuditManager().log(faction, actor, action, details);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Alt system
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns whether a UUID belongs to an alt account.
     */
    public boolean isAlt(UUID uuid) {
        return plugin.getAltManager().isAlt(uuid);
    }

    /**
     * Returns the main account UUID for an alt, or null if not an alt.
     */
    public UUID getMainAccount(UUID altUuid) {
        return plugin.getAltManager().getMainAccount(altUuid);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Misc
    // ─────────────────────────────────────────────────────────────────────────

    // ─────────────────────────────────────────────────────────────────────────
    // Custom Items
    // ─────────────────────────────────────────────────────────────────────────

    /** Direct access to the CustomItemManager. */
    public CustomItemManager getCustomItemManager() {
        return plugin.getCustomItemManager();
    }

    /**
     * Returns a CustomItem definition by its config key (case-insensitive).
     */
    public CustomItem getCustomItem(String id) {
        return plugin.getCustomItemManager().getCustomItem(id);
    }

    /**
     * Returns all loaded custom items.
     */
    public Collection<CustomItem> getAllCustomItems() {
        return plugin.getCustomItemManager().getAllItems();
    }

    /**
     * Builds a fresh ItemStack for the given custom item id with PDC tracking.
     * Returns null if the id is unknown.
     */
    public ItemStack buildCustomItemStack(String id) {
        return plugin.getCustomItemManager().buildItemStack(id);
    }

    /**
     * Returns whether an ItemStack is a custom item (has PDC tracking data).
     */
    public boolean isCustomItem(ItemStack stack) {
        return plugin.getCustomItemManager().isCustomItem(stack);
    }

    /**
     * Returns the custom item id stored in an ItemStack's PDC, or null.
     */
    public String getCustomItemId(ItemStack stack) {
        return plugin.getCustomItemManager().getItemId(stack);
    }

    /**
     * Returns remaining uses on a custom item, or -1 for unlimited.
     */
    public int getRemainingUses(ItemStack stack) {
        return plugin.getCustomItemManager().getRemainingUses(stack);
    }

    /**
     * Manually sets remaining uses on a custom item (updates lore bar).
     */
    public void setRemainingUses(ItemStack stack, int uses) {
        plugin.getCustomItemManager().setRemainingUses(stack, uses);
    }

    /**
     * Returns remaining custom durability on a pickaxe item, or -1 if not tracked.
     */
    public int getRemainingDurability(ItemStack stack) {
        return plugin.getCustomItemManager().getRemainingDurability(stack);
    }

    /**
     * Manually sets remaining custom durability (updates lore bar).
     */
    public void setRemainingDurability(ItemStack stack, int durability) {
        plugin.getCustomItemManager().setRemainingDurability(stack, durability);
    }

    /**
     * Returns whether a player is currently on cooldown for the given custom item.
     */
    public boolean isOnCustomItemCooldown(UUID player, String itemId) {
        return plugin.getCustomItemManager().isOnCooldown(player, itemId);
    }

    /**
     * Returns the remaining cooldown in seconds, or 0.
     */
    public long getCustomItemCooldownSeconds(UUID player, String itemId) {
        return plugin.getCustomItemManager().getCooldownRemainingSeconds(player, itemId);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Cross-server (RabbitMQ)
    // ─────────────────────────────────────────────────────────────────────────

    /** Returns the RabbitMQManager, or null if RabbitMQ is disabled. */
    public RabbitMQManager getRabbitMQManager() {
        return plugin.getRabbitMQManager();
    }

    /** Returns true if RabbitMQ is enabled and connected. */
    public boolean isRabbitMQConnected() {
        return plugin.isRabbitMQEnabled();
    }

    /**
     * Publishes an arbitrary cross-server message directly.
     * Does nothing if RabbitMQ is disabled.
     */
    public void publishCrossServerMessage(CrossServerMessage message) {
        if (!plugin.isRabbitMQEnabled()) return;
        plugin.getRabbitMQManager().publish(message);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Misc
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the raw plugin instance.
     */
    public OreoFactionsPlugin getPlugin() {
        return plugin;
    }
}

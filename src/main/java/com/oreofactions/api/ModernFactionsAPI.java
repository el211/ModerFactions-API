package com.oreofactions.api;

import com.oreofactions.api.events.*;
import com.oreofactions.dynamite.DynamiteConfig;
import com.oreofactions.messaging.CrossServerMessage;
import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import com.oreofactions.models.FactionRole;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * ModernFactions public API.
 *
 * <p>Retrieve the instance via {@link #get()} after the plugin has enabled.
 * All significant actions fire a Bukkit event in {@code com.oreofactions.api.events};
 * cancellable events can be cancelled to block the action.
 *
 * <p>Declare {@code depend: [ModernFactions]} in your {@code plugin.yml} to
 * guarantee load order.
 */
public interface ModernFactionsAPI {

    /** Returns the active API instance, or {@code null} if not yet loaded. */
    static ModernFactionsAPI get() {
        return ModernFactionsProvider.get();
    }

    // ── Faction queries ───────────────────────────────────────────────────────
    Faction getFaction(String tag);
    Collection<Faction> getAllFactions();
    List<Faction> getTopFactionsByPower(int limit);
    List<Faction> getTopFactionsByPoints(int limit);
    boolean factionExists(String tag);
    int getFactionCount();

    // ── Faction lifecycle ─────────────────────────────────────────────────────
    Faction createFaction(String tag, UUID leader, Player player);
    boolean disbandFaction(Faction faction, Player player, FactionDisbandEvent.Reason reason);
    void saveFaction(Faction faction);

    // ── Player (FPlayer) ──────────────────────────────────────────────────────
    FPlayer getFPlayer(UUID uuid);
    FPlayer getOrCreateFPlayer(Player player);
    Collection<FPlayer> getAllFPlayers();
    Faction getPlayerFaction(UUID uuid);
    void saveFPlayer(FPlayer fPlayer);

    // ── Members ───────────────────────────────────────────────────────────────
    boolean addMember(Faction faction, Player player);
    boolean removeMember(Faction faction, Player player);
    boolean kickMember(Faction faction, UUID kicked, Player kicker);
    boolean banPlayer(Faction faction, UUID target, Player banner);
    boolean invitePlayer(Faction faction, UUID invited, Player inviter);
    boolean setRole(Faction faction, UUID target, FactionRole newRole, Player actor);

    // ── Claims ────────────────────────────────────────────────────────────────
    boolean claim(Faction faction, Chunk chunk, Player player);
    boolean unclaim(Faction faction, String claimKey, Player player);
    boolean isClaimed(Chunk chunk);
    Faction getFactionAt(Location location);

    // ── Relations ─────────────────────────────────────────────────────────────
    boolean setRelation(Faction from, Faction to,
                        FactionRelationChangeEvent.RelationType relation, Player actor);
    boolean isAlly(Faction a, Faction b);
    boolean isEnemy(Faction a, Faction b);
    boolean isTruce(Faction a, Faction b);

    // ── Power ─────────────────────────────────────────────────────────────────
    double getPlayerPower(UUID uuid);
    void addPlayerPower(FPlayer fPlayer, double amount);
    void removePlayerPower(FPlayer fPlayer, double amount);
    void setPlayerPower(FPlayer fPlayer, double amount);
    void setFactionPower(Faction faction, double amount, FactionPowerChangeEvent.ChangeReason reason);
    void setFactionPower(Faction faction, double amount);

    // ── DTR ───────────────────────────────────────────────────────────────────
    void setDTR(Faction faction, double newDtr);
    void freezeDTR(Faction faction);

    // ── Points ────────────────────────────────────────────────────────────────
    int getFactionPoints(Faction faction);
    void addFactionPoints(Faction faction, int amount);
    boolean removeFactionPoints(Faction faction, int amount);
    void setFactionPoints(Faction faction, int amount);

    // ── Balance ───────────────────────────────────────────────────────────────
    double getFactionBalance(Faction faction);
    boolean depositBalance(Faction faction, double amount, Player actor);
    boolean withdrawBalance(Faction faction, double amount, Player actor);
    void setBalance(Faction faction, double amount, Player actor);

    // ── TNT bank ──────────────────────────────────────────────────────────────
    int getTNTBank(Faction faction);
    boolean depositTNT(Faction faction, int amount, Player actor);
    boolean withdrawTNT(Faction faction, int amount, Player actor);

    // ── Combat tag ────────────────────────────────────────────────────────────
    void combatTag(Player player, Player attacker);
    boolean isInCombat(UUID uuid);
    long getCombatTagSecondsRemaining(UUID uuid);
    void removeCombatTag(UUID uuid);

    // ── Peaceful ──────────────────────────────────────────────────────────────
    boolean setPeaceful(Faction faction, boolean peaceful, Player actor,
                        FactionPeacefulToggleEvent.ChangeReason reason);
    boolean setPeaceful(Faction faction, boolean peaceful, Player actor);

    // ── Shield ────────────────────────────────────────────────────────────────
    boolean activateShield(Faction faction, long durationMs, Player activator);
    boolean hasShield(Faction faction);
    void deactivateShield(Faction faction);

    // ── Grace ─────────────────────────────────────────────────────────────────
    boolean activateGrace(Faction faction, long durationMs, Player activator);
    boolean hasGrace(Faction faction);
    long getGraceRemainingMs(Faction faction);

    // ── Upgrades ──────────────────────────────────────────────────────────────
    int getUpgradeLevel(Faction faction, String upgradeKey);
    boolean purchaseUpgrade(Faction faction, String upgradeKey, Player buyer);

    // ── Warps ─────────────────────────────────────────────────────────────────
    boolean setWarp(Faction faction, String name, Location location, String password, Player setter);
    boolean deleteWarp(Faction faction, String name, Player actor);
    boolean teleportToWarp(Faction faction, String warpName, Player player);

    // ── Home ──────────────────────────────────────────────────────────────────
    boolean setHome(Faction faction, Location location, Player setter);
    boolean teleportToHome(Faction faction, Player player);

    // ── Permissions ───────────────────────────────────────────────────────────
    boolean hasPermission(Faction faction, UUID player, String permKey);

    // ── Missions ──────────────────────────────────────────────────────────────
    void recordBlockMine(Faction faction, Material material);
    void recordKill(Faction faction);
    void recordEnemyKill(Faction attacker, Faction victim);
    void recordClaim(Faction faction);
    boolean startMission(Faction faction, String missionKey);
    boolean cancelMission(Faction faction, String missionKey);
    boolean isMissionActive(Faction faction, String missionKey);
    int getMissionProgress(Faction faction, String missionKey);

    // ── Audit log ─────────────────────────────────────────────────────────────
    void auditLog(Faction faction, UUID actor, String action, String details);

    // ── Alt detection ─────────────────────────────────────────────────────────
    boolean isAlt(UUID uuid);
    UUID getMainAccount(UUID altUuid);

    // ── Faction chest ─────────────────────────────────────────────────────────
    ItemStack[] loadFactionChest(Faction faction);
    ItemStack[] loadFactionChest(String factionTag);
    void saveFactionChest(Faction faction, ItemStack[] items);
    void saveFactionChest(String factionTag, ItemStack[] items);
    void deleteFactionChest(Faction faction);
    void deleteFactionChest(String factionTag);
    void invalidateFactionChestCache(Faction faction);
    void invalidateFactionChestCache(String factionTag);

    // ── Tag reserves ──────────────────────────────────────────────────────────
    boolean isReserveSystemEnabled();
    boolean isReservedTag(String tag);
    boolean canClaimReservedTag(String tag, UUID playerUuid);
    void reserveTag(String tag, UUID... allowedUuids);
    void reserveTag(String tag, int durationDays, UUID... allowedUuids);
    void unreserveTag(String tag);
    void reloadReserves();
    int getReserveDurationDays();
    double getReserveCost();

    // ── CaveBlock ─────────────────────────────────────────────────────────────
    boolean isPlayerInCaveBlock(UUID playerUuid);
    boolean isTrackedCaveBlock(Location location);
    void placeCaveBlock(Player player, Location blockLocation, int usesFromItem);
    boolean interactWithCaveBlock(Player player, Location blockLocation);
    boolean breakCaveBlock(Player player, Location blockLocation);
    void exitCaveBlock(Player player);
    void exitCaveBlockSilently(Player player);

    // ── Dynamite ──────────────────────────────────────────────────────────────
    DynamiteConfig getDynamiteConfig();
    void reloadDynamiteConfig();
    void throwDynamite(Player thrower, DynamiteConfig config);
    boolean isDynamiteProjectile(Entity entity);
    DynamiteConfig getDynamiteProjectileConfig(UUID projectileUuid);

    // ── Scoreboard ────────────────────────────────────────────────────────────
    void refreshFactionScoreboards();
    void refreshFactionScoreboard(Player player);
    void clearFactionScoreboard(Player player);
    void reloadFactionScoreboards();

    // ── Custom items ──────────────────────────────────────────────────────────
    boolean isCustomItem(ItemStack stack);
    String getCustomItemId(ItemStack stack);
    int getRemainingUses(ItemStack stack);
    void setRemainingUses(ItemStack stack, int uses);
    int getRemainingDurability(ItemStack stack);
    void setRemainingDurability(ItemStack stack, int durability);
    boolean isOnCustomItemCooldown(UUID player, String itemId);
    long getCustomItemCooldownSeconds(UUID player, String itemId);

    // ── Cross-server (RabbitMQ) ───────────────────────────────────────────────
    boolean isRabbitMQConnected();
    void publishCrossServerMessage(CrossServerMessage message);
}

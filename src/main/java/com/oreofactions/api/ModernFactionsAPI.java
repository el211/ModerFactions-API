package com.oreofactions.api;

import com.oreofactions.ModernFactions;
import com.oreofactions.api.events.*;
import com.oreofactions.caveblock.CaveBlockManager;
import com.oreofactions.customitems.CustomItem;
import com.oreofactions.customitems.CustomItemManager;
import com.oreofactions.database.MongoDBManager;
import com.oreofactions.database.StorageProvider;
import com.oreofactions.dynamite.DynamiteConfig;
import com.oreofactions.dynamite.DynamiteManager;
import com.oreofactions.features.*;
import com.oreofactions.gui.GUIConfigHelper;
import com.oreofactions.gui.GUIManager;
import com.oreofactions.managers.*;
import com.oreofactions.messaging.CrossServerMessage;
import com.oreofactions.messaging.RabbitMQManager;
import com.oreofactions.models.FPlayer;
import com.oreofactions.models.Faction;
import com.oreofactions.models.FactionRole;
import com.oreofactions.scheduler.TaskScheduler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Public integration surface for ModernFactions.
 *
 * <p>Use the convenience methods in this class as the stable API whenever possible.
 * They are intended to remain easier to consume from external plugins and scripts.
 *
 * <p>Methods that expose raw managers, configs, schedulers, or plugin internals are
 * provided as advanced escape hatches. Those are useful for deep integrations, but
 * they couple callers to internal implementation details more tightly than the
 * wrapper methods in this class.
 */
public final class ModernFactionsAPI {

    private static ModernFactionsAPI instance;

    private final ModernFactions plugin;

    private ModernFactionsAPI(ModernFactions plugin) {
        this.plugin = plugin;
    }

    
    
    

    

    /**
     * Initializes the singleton API instance during plugin startup.
     *
     * <p>This is called by ModernFactions itself and normally should not be invoked
     * by external plugins.
     */
    public static void init(ModernFactions plugin) {
        if (instance == null) {
            instance = new ModernFactionsAPI(plugin);
        }
    }

    

    /**
     * Returns the active API singleton.
     *
     * <p>This is the main entrypoint external plugins should use.
     */
    public static ModernFactionsAPI get() {
        if (instance == null) {
            ModernFactions p = (ModernFactions) Bukkit.getPluginManager().getPlugin("ModernFactions");
            if (p != null) instance = new ModernFactionsAPI(p);
        }
        return instance;
    }

    
    
    

    
    /**
     * Advanced escape hatch to the raw faction manager.
     *
     * <p>Prefer the higher-level faction wrapper methods in this API unless you
     * specifically need internal manager behavior.
     */
    public FactionManager getFactionManager() {
        return plugin.getFactionManager();
    }

    /**
     * Advanced escape hatch to the raw player manager.
     */
    public PlayerManager getPlayerManager() {
        return plugin.getPlayerManager();
    }

    /**
     * Advanced escape hatch to the raw claim manager.
     */
    public ClaimManager getClaimManager() {
        return plugin.getClaimManager();
    }

    /**
     * Advanced escape hatch to the raw faction permission manager.
     */
    public PermissionManager getPermissionManager() {
        return plugin.getPermissionManager();
    }

    /**
     * Advanced escape hatch to the raw member-permissions manager.
     */
    public MemberPermissionsManager getMemberPermissionsManager() {
        return plugin.getMemberPermissionsManager();
    }

    /**
     * Advanced escape hatch to the raw relation manager.
     */
    public RelationManager getRelationManager() {
        return plugin.getRelationManager();
    }

    /**
     * Advanced escape hatch to the chat manager.
     */
    public ChatManager getChatManager() {
        return plugin.getChatManager();
    }

    /**
     * Advanced escape hatch to the scoreboard manager.
     *
     * <p>Prefer the scoreboard wrapper methods in this API for refresh and clear
     * operations when possible.
     */
    public FactionScoreboardManager getFactionScoreboardManager() {
        return plugin.getFactionScoreboardManager();
    }

    /**
     * Advanced escape hatch to the GUI manager.
     */
    public GUIManager getGuiManager() {
        return plugin.getGuiManager();
    }

    /**
     * Advanced escape hatch to the bundled SmartInvs inventory manager.
     */
    public fr.minuskube.inv.InventoryManager getInventoryManager() {
        return plugin.getInventoryManager();
    }

    /**
     * Advanced escape hatch to the block protection manager.
     */
    public BlockProtectionManager getBlockProtectionManager() {
        return plugin.getBlockProtectionManager();
    }

    
    public PowerManager getPowerManager() {
        return plugin.getPowerManager();
    }

    
    public CombatManager getCombatManager() {
        return plugin.getCombatManager();
    }

    
    public PointsManager getPointsManager() {
        return plugin.getPointsManager();
    }

    
    public UpgradeManager getUpgradeManager() {
        return plugin.getUpgradeManager();
    }

    
    public MissionManager getMissionManager() {
        return plugin.getMissionManager();
    }

    
    public GraceManager getGraceManager() {
        return plugin.getGraceManager();
    }

    
    public AuditManager getAuditManager() {
        return plugin.getAuditManager();
    }

    
    public AltManager getAltManager() {
        return plugin.getAltManager();
    }

    public AntiSpamManager getAntiSpamManager() {
        return plugin.getAntiSpamManager();
    }

    
    public DiscordManager getDiscordManager() {
        return plugin.getDiscordManager();
    }

    public ShopManager getShopManager() {
        return plugin.getShopManager();
    }

    
    public StorageProvider getStorageProvider() {
        return plugin.getStorageProvider();
    }

    public MongoDBManager getMongoDBManager() {
        return plugin.getMongoDBManager();
    }

    public FactionChestManager getFactionChestManager() {
        return plugin.getFactionChestManager();
    }

    public CheckManager getCheckManager() {
        return plugin.getCheckManager();
    }

    public ReserveManager getReserveManager() {
        return plugin.getReserveManager();
    }

    public WarpTeleportManager getWarpTeleportManager() {
        return plugin.getWarpTeleportManager();
    }

    public CaveBlockManager getCaveBlockManager() {
        return plugin.getCaveBlockManager();
    }

    public DynamiteManager getDynamiteManager() {
        return plugin.getDynamiteManager();
    }

    public TaskScheduler getTaskScheduler() {
        return plugin.getTaskScheduler();
    }

    public Economy getVaultEconomy() {
        return plugin.getVaultEconomy();
    }

    public BukkitAudiences getAdventure() {
        return plugin.getAdventure();
    }

    public LangManager getLangManager() {
        return plugin.getLangManager();
    }

    public GUIConfigHelper getGuiConfigHelper() {
        return plugin.getGuiConfigHelper();
    }

    public FileConfiguration getUpgradesConfig() {
        return plugin.getUpgradesConfig();
    }

    public FileConfiguration getPowerConfig() {
        return plugin.getPowerConfig();
    }

    public FileConfiguration getPermissionsConfig() {
        return plugin.getPermissionsConfig();
    }

    public FileConfiguration getDiscordConfig() {
        return plugin.getDiscordConfig();
    }

    public FileConfiguration getFactionShopConfig() {
        return plugin.getFactionShopConfig();
    }

    public FileConfiguration getMissionsConfig() {
        return plugin.getMissionsConfig();
    }

    public FileConfiguration getChatConfig() {
        return plugin.getChatConfig();
    }

    public FileConfiguration getLangConfig() {
        return plugin.getLangConfig();
    }

    public FileConfiguration getGuiConfig() {
        return plugin.getGuiConfig();
    }

    
    
    

    

    /**
     * Stable public API wrapper that looks up a faction by tag.
     */
    public Faction getFaction(String tag) {
        return plugin.getFactionManager().getFaction(tag);
    }

    

    /**
     * Stable public API wrapper that returns all loaded factions.
     */
    public Collection<Faction> getAllFactions() {
        return plugin.getFactionManager().getAllFactions();
    }

    

    /**
     * Stable public API wrapper that returns top factions ordered by power.
     */
    public List<Faction> getTopFactionsByPower(int limit) {
        return plugin.getFactionManager().getTopFactionsByPower(limit);
    }

    

    /**
     * Stable public API wrapper that returns top factions ordered by points.
     */
    public List<Faction> getTopFactionsByPoints(int limit) {
        return plugin.getFactionManager().getTopFactionsByPoints(limit);
    }

    

    /**
     * Stable public API wrapper that checks whether a faction exists.
     */
    public boolean factionExists(String tag) {
        return plugin.getFactionManager().factionExists(tag);
    }

    

    /**
     * Stable public API wrapper that returns the number of loaded factions.
     */
    public int getFactionCount() {
        return plugin.getFactionManager().getFactionCount();
    }

    
    
    

    

    /**
     * Stable public API wrapper that creates a faction and fires the public create event.
     */
    public Faction createFaction(String tag, UUID leader, Player player) {
        Faction dummy = new Faction(tag, leader);
        FactionCreateEvent event = new FactionCreateEvent(dummy, leader, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return null;

        return plugin.getFactionManager().createFaction(tag, leader);
    }

    

    /**
     * Stable public API wrapper that disbands a faction and fires the public disband event.
     */
    public boolean disbandFaction(Faction faction, Player player, FactionDisbandEvent.Reason reason) {
        FactionDisbandEvent event = new FactionDisbandEvent(faction, player, reason);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getFactionManager().disbandFaction(faction);
        return true;
    }

    

    /**
     * Stable public API wrapper that persists a faction immediately.
     */
    public void saveFaction(Faction faction) {
        plugin.getFactionManager().saveFaction(faction);
    }

    
    
    

    

    /**
     * Stable public API wrapper that returns a faction player record by UUID.
     */
    public FPlayer getFPlayer(UUID uuid) {
        return plugin.getPlayerManager().getFPlayer(uuid);
    }

    

    /**
     * Stable public API wrapper that returns the current faction player record for a Bukkit player.
     */
    public FPlayer getOrCreateFPlayer(Player player) {
        return plugin.getPlayerManager().getFPlayer(player.getUniqueId());
    }

    

    /**
     * Stable public API wrapper that returns all loaded faction player records.
     */
    public Collection<FPlayer> getAllFPlayers() {
        return plugin.getPlayerManager().getAllPlayers();
    }

    

    /**
     * Stable public API wrapper that resolves a player's current faction, if any.
     */
    public Faction getPlayerFaction(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        if (fp == null || !fp.hasFaction()) return null;
        return getFaction(fp.getFactionId());
    }

    

    /**
     * Stable public API wrapper that persists a faction player record immediately.
     */
    public void saveFPlayer(FPlayer fPlayer) {
        plugin.getPlayerManager().saveFPlayer(fPlayer);
    }

    
    
    

    

    /**
     * Stable public API wrapper that adds a player to a faction and fires the public join event.
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
     * Stable public API wrapper that removes a player from a faction and fires the public leave event.
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
     * Stable public API wrapper that kicks a member from a faction and fires the public kick event.
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
     * Stable public API wrapper that bans a player from a faction and fires the public ban event.
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
     * Stable public API wrapper that invites a player to a faction and fires the public invite event.
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
     * Stable public API wrapper that changes a faction role and fires the public role-change event.
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

    
    
    

    

    /**
     * Stable public API wrapper that claims a chunk for a faction and fires the public claim event.
     */
    public boolean claim(Faction faction, Chunk chunk, Player player) {
        FactionClaimEvent event = new FactionClaimEvent(faction, chunk, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        return plugin.getClaimManager().claim(chunk, faction);
    }

    

    /**
     * Stable public API wrapper that unclaims land for a faction and fires the public unclaim event.
     */
    public boolean unclaim(Faction faction, String claimKey, Player player) {
        FactionUnclaimEvent event = new FactionUnclaimEvent(faction, claimKey, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getClaimManager().unclaim(claimKey);
        return true;
    }

    

    /**
     * Stable public API wrapper that checks whether a chunk is claimed.
     */
    public boolean isClaimed(Chunk chunk) {
        return plugin.getClaimManager().isClaimed(chunk);
    }

    

    /**
     * Stable public API wrapper that resolves the faction owning a location, if any.
     */
    public Faction getFactionAt(Location location) {
        return plugin.getClaimManager().getFactionAt(location);
    }

    
    
    

    

    /**
     * Stable public API wrapper that updates the relation between two factions and fires the public relation-change event.
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
            case NEUTRAL -> {  }
        }
    }

    
    /**
     * Stable public API wrapper that checks whether two factions are allied.
     */
    public boolean isAlly(Faction a, Faction b) {
        return plugin.getRelationManager().isAlly(a, b);
    }

    
    /**
     * Stable public API wrapper that checks whether two factions are enemies.
     */
    public boolean isEnemy(Faction a, Faction b) {
        return plugin.getRelationManager().isEnemy(a, b);
    }

    
    /**
     * Stable public API wrapper that checks whether two factions are in a truce.
     */
    public boolean isTruce(Faction a, Faction b) {
        return plugin.getRelationManager().isTruce(a, b);
    }

    
    
    

    

    /**
     * Stable public API wrapper that returns the current power for a player UUID.
     */
    public double getPlayerPower(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        return fp == null ? 0 : plugin.getPowerManager().getPlayerPower(fp);
    }

    

    /**
     * Stable public API wrapper that adds power to a player and emits the public power-change event.
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
     * Stable public API wrapper that removes power from a player and emits the public power-change event.
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
     * Stable public API wrapper that sets player power directly and emits the public power-change event.
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

    /**
     * Stable public API wrapper that sets a faction's total power directly and emits the public power-change event.
     */
    public void setFactionPower(Faction faction, double amount, FactionPowerChangeEvent.ChangeReason reason) {
        if (faction == null) return;

        double oldFactionPower = faction.getPower();
        faction.setPower(amount);
        saveFaction(faction);

        FactionPowerChangeEvent event = new FactionPowerChangeEvent(
                faction, null, 0, 0,
                oldFactionPower, amount, reason);
        Bukkit.getPluginManager().callEvent(event);
    }

    /**
     * Stable public API wrapper that sets a faction's total power directly and emits the public power-change event.
     */
    public void setFactionPower(Faction faction, double amount) {
        setFactionPower(faction, amount, FactionPowerChangeEvent.ChangeReason.ADMIN_SET);
    }

    
    
    

    

    /**
     * Stable public API wrapper that sets faction DTR directly and emits the public DTR event.
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
     * Stable public API wrapper that freezes faction DTR and emits the public DTR event.
     */
    public void freezeDTR(Faction faction) {
        if (faction == null) return;
        double oldDtr = faction.getDtr();
        plugin.getFactionManager().freezeDTR(faction);
        Bukkit.getPluginManager().callEvent(
                new FactionDTRChangeEvent(faction, oldDtr, faction.getDtr(), FactionDTRChangeEvent.ChangeReason.FREEZE));
    }

    
    
    

    

    /**
     * Stable public API wrapper that returns a faction's current points.
     */
    public int getFactionPoints(Faction faction) {
        return plugin.getPointsManager().getPoints(faction);
    }

    

    /**
     * Stable public API wrapper that adds faction points and emits the public points-change event.
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
     * Stable public API wrapper that removes faction points and emits the public points-change event.
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
     * Stable public API wrapper that sets faction points directly and emits the public points-change event.
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

    
    
    

    

    /**
     * Stable public API wrapper that returns a faction's current balance.
     */
    public double getFactionBalance(Faction faction) {
        return faction == null ? 0 : faction.getBalance();
    }

    

    /**
     * Stable public API wrapper that deposits into faction balance and emits the public balance-change event.
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
     * Stable public API wrapper that withdraws from faction balance and emits the public balance-change event.
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
     * Stable public API wrapper that sets faction balance directly and emits the public balance-change event.
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

    
    
    

    

    /**
     * Stable public API wrapper that returns the faction TNT bank amount.
     */
    public int getTNTBank(Faction faction) {
        return faction == null ? 0 : faction.getTntBank();
    }

    

    /**
     * Stable public API wrapper that deposits TNT into the faction bank and emits the public TNT-bank event.
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
     * Stable public API wrapper that withdraws TNT from the faction bank and emits the public TNT-bank event.
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

    
    
    

    

    /**
     * Stable public API wrapper that combat-tags a player and emits the public combat-tag event.
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
     * Stable public API wrapper that checks whether a player UUID is combat-tagged.
     */
    public boolean isInCombat(UUID uuid) {
        return plugin.getCombatManager().isTagged(uuid);
    }

    

    /**
     * Stable public API wrapper that returns remaining combat-tag time in seconds.
     */
    public long getCombatTagSecondsRemaining(UUID uuid) {
        return plugin.getCombatManager().getTagRemainingSeconds(uuid);
    }

    

    /**
     * Stable public API wrapper that removes a combat tag from a player UUID.
     */
    public void removeCombatTag(UUID uuid) {
        plugin.getCombatManager().untag(uuid);
    }

    /**
     * Stable public API wrapper that toggles a faction's peaceful state and emits the public peaceful-toggle event.
     */
    public boolean setPeaceful(Faction faction, boolean peaceful, Player actor,
                               FactionPeacefulToggleEvent.ChangeReason reason) {
        if (faction == null) return false;

        boolean oldPeaceful = faction.isPeaceful();
        if (oldPeaceful == peaceful) return true;

        FactionPeacefulToggleEvent event = new FactionPeacefulToggleEvent(
                faction, oldPeaceful, peaceful, actor, reason);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setPeaceful(event.getNewPeaceful());
        saveFaction(faction);
        return true;
    }

    /**
     * Stable public API wrapper that toggles a faction's peaceful state and emits the public peaceful-toggle event.
     */
    public boolean setPeaceful(Faction faction, boolean peaceful, Player actor) {
        return setPeaceful(faction, peaceful, actor, FactionPeacefulToggleEvent.ChangeReason.API);
    }

    /**
     * Stable public API wrapper that activates a faction shield and emits the public shield event.
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
     * Stable public API wrapper that checks whether a faction currently has an active shield.
     */
    public boolean hasShield(Faction faction) {
        return faction != null && faction.isShieldActive()
                && faction.getShieldEndTime() > System.currentTimeMillis();
    }

    

    /**
     * Stable public API wrapper that deactivates a faction shield immediately.
     */
    public void deactivateShield(Faction faction) {
        if (faction == null) return;
        faction.setShieldActive(false);
        faction.setShieldEndTime(0);
        saveFaction(faction);
    }

    
    
    

    

    /**
     * Stable public API wrapper that activates faction grace and emits the public grace event.
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
     * Stable public API wrapper that checks whether a faction currently has grace.
     */
    public boolean hasGrace(Faction faction) {
        return plugin.getGraceManager().hasGrace(faction);
    }

    

    /**
     * Stable public API wrapper that returns remaining grace time in milliseconds.
     */
    public long getGraceRemainingMs(Faction faction) {
        return plugin.getGraceManager().getGraceRemaining(faction);
    }

    
    
    

    

    /**
     * Stable public API wrapper that returns the current level of a faction upgrade.
     */
    public int getUpgradeLevel(Faction faction, String upgradeKey) {
        return plugin.getUpgradeManager().getLevel(faction, upgradeKey);
    }

    

    /**
     * Stable public API wrapper that attempts to purchase a faction upgrade and emits the public upgrade event.
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

    
    
    

    

    /**
     * Stable public API wrapper that sets a faction warp and emits the public warp-set event.
     */
    public boolean setWarp(Faction faction, String name, Location location,
                           String password, Player setter) {
        FactionWarpSetEvent event = new FactionWarpSetEvent(faction, name, location, password, setter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        Faction.WarpData warp = new Faction.WarpData(location, password);
        
        if (plugin.getRabbitMQManager() != null) {
            warp.setServer(plugin.getRabbitMQManager().getServerId());
        }
        faction.getWarps().put(name, warp);
        saveFaction(faction);
        return true;
    }

    

    /**
     * Stable public API wrapper that deletes a faction warp and emits the public warp-delete event.
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
     * Stable public API wrapper that teleports a player to a faction warp and emits the public warp-teleport event.
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

    
    
    

    

    /**
     * Stable public API wrapper that sets faction home and emits the public home-set event.
     */
    public boolean setHome(Faction faction, Location location, Player setter) {
        if (faction == null) return false;
        FactionHomeSetEvent event = new FactionHomeSetEvent(faction, faction.getHome(), location, setter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.setHome(location);
        
        if (plugin.getRabbitMQManager() != null) {
            faction.setHomeServer(plugin.getRabbitMQManager().getServerId());
        }
        saveFaction(faction);
        return true;
    }

    /**
     * Stable public API wrapper that teleports a player to faction home and emits the public home-teleport event.
     */
    public boolean teleportToHome(Faction faction, Player player) {
        if (faction == null || player == null || faction.getHome() == null) return false;

        FactionHomeTeleportEvent event = new FactionHomeTeleportEvent(faction, player, faction.getHome());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        player.teleport(event.getDestination());
        return true;
    }

    
    
    

    

    /**
     * Stable public API wrapper that checks a faction permission node for a player UUID.
     */
    public boolean hasPermission(Faction faction, UUID player, String permKey) {
        return plugin.getPermissionManager().has(faction, player, permKey);
    }

    
    
    

    

    /**
     * Stable public API wrapper that records mined blocks toward faction missions.
     */
    public void recordBlockMine(Faction faction, org.bukkit.Material material) {
        plugin.getMissionManager().recordBlockMine(faction, material);
    }

    

    /**
     * Stable public API wrapper that records a generic faction kill toward missions.
     */
    public void recordKill(Faction faction) {
        plugin.getMissionManager().recordKill(faction);
    }

    

    /**
     * Stable public API wrapper that records an enemy kill toward missions.
     */
    public void recordEnemyKill(Faction attacker, Faction victim) {
        plugin.getMissionManager().recordEnemyKill(attacker, victim);
    }

    

    /**
     * Stable public API wrapper that records a faction claim toward missions.
     */
    public void recordClaim(Faction faction) {
        plugin.getMissionManager().recordClaim(faction);
    }

    

    /**
     * Stable public API wrapper that starts a faction mission.
     */
    public boolean startMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().startMission(faction, missionKey);
    }

    

    /**
     * Stable public API wrapper that cancels a faction mission.
     */
    public boolean cancelMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().cancelMission(faction, missionKey);
    }

    

    /**
     * Stable public API wrapper that checks whether a mission is currently active for a faction.
     */
    public boolean isMissionActive(Faction faction, String missionKey) {
        return plugin.getMissionManager().isMissionActive(faction, missionKey);
    }

    

    /**
     * Stable public API wrapper that returns current mission progress for a faction.
     */
    public int getMissionProgress(Faction faction, String missionKey) {
        return plugin.getMissionManager().getProgress(faction, missionKey);
    }

    
    
    

    

    /**
     * Stable public API wrapper that writes a faction audit-log entry.
     */
    public void auditLog(Faction faction, UUID actor, String action, String details) {
        plugin.getAuditManager().log(faction, actor, action, details);
    }

    
    
    

    

    /**
     * Stable public API wrapper that checks whether a UUID is marked as an alt account.
     */
    public boolean isAlt(UUID uuid) {
        return plugin.getAltManager().isAlt(uuid);
    }

    

    /**
     * Stable public API wrapper that resolves the main account UUID for an alt account.
     */
    public UUID getMainAccount(UUID altUuid) {
        return plugin.getAltManager().getMainAccount(altUuid);
    }

    /**
     * Stable public API wrapper that loads a faction chest by faction object.
     */
    public ItemStack[] loadFactionChest(Faction faction) {
        if (faction == null) return new ItemStack[0];
        return plugin.getFactionChestManager().loadChest(faction.getTag());
    }

    /**
     * Stable public API wrapper that loads a faction chest by faction tag.
     */
    public ItemStack[] loadFactionChest(String factionTag) {
        return plugin.getFactionChestManager().loadChest(factionTag);
    }

    /**
     * Stable public API wrapper that saves a faction chest by faction object.
     */
    public void saveFactionChest(Faction faction, ItemStack[] items) {
        if (faction == null) return;
        plugin.getFactionChestManager().saveChest(faction.getTag(), items);
    }

    /**
     * Stable public API wrapper that saves a faction chest by faction tag.
     */
    public void saveFactionChest(String factionTag, ItemStack[] items) {
        plugin.getFactionChestManager().saveChest(factionTag, items);
    }

    /**
     * Stable public API wrapper that deletes a faction chest by faction object.
     */
    public void deleteFactionChest(Faction faction) {
        if (faction == null) return;
        plugin.getFactionChestManager().deleteChest(faction.getTag());
    }

    /**
     * Stable public API wrapper that deletes a faction chest by faction tag.
     */
    public void deleteFactionChest(String factionTag) {
        plugin.getFactionChestManager().deleteChest(factionTag);
    }

    /**
     * Stable public API wrapper that invalidates the cached chest for a faction.
     */
    public void invalidateFactionChestCache(Faction faction) {
        if (faction == null) return;
        plugin.getFactionChestManager().invalidateCache(faction.getTag());
    }

    /**
     * Stable public API wrapper that invalidates the cached chest for a faction tag.
     */
    public void invalidateFactionChestCache(String factionTag) {
        plugin.getFactionChestManager().invalidateCache(factionTag);
    }

    /**
     * Stable public API wrapper that reports whether tag reserves are enabled.
     */
    public boolean isReserveSystemEnabled() {
        return plugin.getReserveManager().isEnabled();
    }

    /**
     * Stable public API wrapper that checks whether a faction tag is reserved.
     */
    public boolean isReservedTag(String tag) {
        return plugin.getReserveManager().isReserved(tag);
    }

    /**
     * Stable public API wrapper that checks whether a specific player UUID may claim a reserved tag.
     */
    public boolean canClaimReservedTag(String tag, UUID playerUuid) {
        return plugin.getReserveManager().canClaim(tag, playerUuid);
    }

    /**
     * Stable public API wrapper that reserves a faction tag for the default configured duration.
     */
    public void reserveTag(String tag, UUID... allowedUuids) {
        plugin.getReserveManager().reserve(tag, allowedUuids);
    }

    /**
     * Stable public API wrapper that reserves a faction tag for a custom number of days.
     */
    public void reserveTag(String tag, int durationDays, UUID... allowedUuids) {
        plugin.getReserveManager().reserve(tag, durationDays, allowedUuids);
    }

    /**
     * Stable public API wrapper that removes a faction tag reservation.
     */
    public void unreserveTag(String tag) {
        plugin.getReserveManager().unreserve(tag);
    }

    /**
     * Stable public API wrapper that reloads the reserve registry from disk.
     */
    public void reloadReserves() {
        plugin.getReserveManager().reload();
    }

    /**
     * Stable public API wrapper that returns the default reserve duration in days.
     */
    public int getReserveDurationDays() {
        return plugin.getReserveManager().getDurationDays();
    }

    /**
     * Stable public API wrapper that returns the configured reserve purchase cost.
     */
    public double getReserveCost() {
        return plugin.getReserveManager().getCost();
    }

    /**
     * Stable public API wrapper that checks whether a player is currently inside a caveblock session.
     */
    public boolean isPlayerInCaveBlock(UUID playerUuid) {
        return plugin.getCaveBlockManager().isInSession(playerUuid);
    }

    /**
     * Stable public API wrapper that checks whether a location contains a tracked caveblock.
     */
    public boolean isTrackedCaveBlock(Location location) {
        return plugin.getCaveBlockManager().isTrackedBlock(location);
    }

    /**
     * Stable public API wrapper that registers a placed caveblock and starts its session flow.
     */
    public void placeCaveBlock(Player player, Location blockLocation, int usesFromItem) {
        plugin.getCaveBlockManager().onBlockPlaced(player, blockLocation, usesFromItem);
    }

    /**
     * Stable public API wrapper that handles interaction with an existing caveblock.
     */
    public boolean interactWithCaveBlock(Player player, Location blockLocation) {
        return plugin.getCaveBlockManager().onBlockInteract(player, blockLocation);
    }

    /**
     * Stable public API wrapper that handles caveblock breaking rules and drops.
     */
    public boolean breakCaveBlock(Player player, Location blockLocation) {
        return plugin.getCaveBlockManager().onBlockBreak(player, blockLocation);
    }

    /**
     * Stable public API wrapper that cleanly exits a caveblock session.
     */
    public void exitCaveBlock(Player player) {
        plugin.getCaveBlockManager().exitSpectator(player);
    }

    /**
     * Stable public API wrapper that exits a caveblock session without a player-facing message.
     */
    public void exitCaveBlockSilently(Player player) {
        plugin.getCaveBlockManager().exitSpectatorSilent(player);
    }

    /**
     * Stable public API wrapper that returns the currently loaded dynamite configuration.
     */
    public DynamiteConfig getDynamiteConfig() {
        return plugin.getDynamiteManager().getConfig();
    }

    /**
     * Stable public API wrapper that reloads the dynamite configuration.
     */
    public void reloadDynamiteConfig() {
        plugin.getDynamiteManager().reloadConfig();
    }

    /**
     * Stable public API wrapper that throws a dynamite projectile with the supplied runtime config.
     */
    public void throwDynamite(Player thrower, DynamiteConfig config) {
        plugin.getDynamiteManager().throwDynamite(thrower, config);
    }

    /**
     * Stable public API wrapper that checks whether an entity belongs to the dynamite system.
     */
    public boolean isDynamiteProjectile(Entity entity) {
        return plugin.getDynamiteManager().isDynamiteProjectile(entity);
    }

    /**
     * Stable public API wrapper that returns the config associated with a tracked dynamite projectile.
     */
    public DynamiteConfig getDynamiteProjectileConfig(UUID projectileUuid) {
        return plugin.getDynamiteManager().getProjectileConfig(projectileUuid);
    }

    /**
     * Stable public API wrapper that refreshes all faction scoreboards immediately.
     */
    public void refreshFactionScoreboards() {
        plugin.getFactionScoreboardManager().refreshAll();
    }

    /**
     * Stable public API wrapper that refreshes one player's faction scoreboard immediately.
     */
    public void refreshFactionScoreboard(Player player) {
        plugin.getFactionScoreboardManager().refreshPlayer(player);
    }

    /**
     * Stable public API wrapper that removes the Modern Factions scoreboard from one player.
     */
    public void clearFactionScoreboard(Player player) {
        plugin.getFactionScoreboardManager().clearPlayer(player);
    }

    /**
     * Stable public API wrapper that reloads scoreboard scheduling and rendering state.
     */
    public void reloadFactionScoreboards() {
        plugin.getFactionScoreboardManager().reload();
    }

    
    
    

    
    
    

    
    /**
     * Advanced escape hatch to the raw custom-item manager.
     *
     * <p>Prefer the custom-item wrapper methods in this API for item lookups and
     * stack state changes when they are sufficient.
     */
    public CustomItemManager getCustomItemManager() {
        return plugin.getCustomItemManager();
    }

    

    public CustomItem getCustomItem(String id) {
        return plugin.getCustomItemManager().getCustomItem(id);
    }

    

    public Collection<CustomItem> getAllCustomItems() {
        return plugin.getCustomItemManager().getAllItems();
    }

    

    public ItemStack buildCustomItemStack(String id) {
        return plugin.getCustomItemManager().buildItemStack(id);
    }

    

    public boolean isCustomItem(ItemStack stack) {
        return plugin.getCustomItemManager().isCustomItem(stack);
    }

    

    public String getCustomItemId(ItemStack stack) {
        return plugin.getCustomItemManager().getItemId(stack);
    }

    

    public int getRemainingUses(ItemStack stack) {
        return plugin.getCustomItemManager().getRemainingUses(stack);
    }

    

    public void setRemainingUses(ItemStack stack, int uses) {
        plugin.getCustomItemManager().setRemainingUses(stack, uses);
    }

    

    public int getRemainingDurability(ItemStack stack) {
        return plugin.getCustomItemManager().getRemainingDurability(stack);
    }

    

    public void setRemainingDurability(ItemStack stack, int durability) {
        plugin.getCustomItemManager().setRemainingDurability(stack, durability);
    }

    

    public boolean isOnCustomItemCooldown(UUID player, String itemId) {
        return plugin.getCustomItemManager().isOnCooldown(player, itemId);
    }

    

    public long getCustomItemCooldownSeconds(UUID player, String itemId) {
        return plugin.getCustomItemManager().getCooldownRemainingSeconds(player, itemId);
    }

    
    
    

    
    /**
     * Advanced escape hatch to the RabbitMQ manager.
     */
    public RabbitMQManager getRabbitMQManager() {
        return plugin.getRabbitMQManager();
    }

    
    public boolean isRabbitMQConnected() {
        return plugin.isRabbitMQEnabled();
    }

    

    public void publishCrossServerMessage(CrossServerMessage message) {
        if (!plugin.isRabbitMQEnabled()) return;
        plugin.getRabbitMQManager().publish(message);
    }

    
    
    

    

    /**
     * Advanced escape hatch to the owning plugin instance.
     *
     * <p>Avoid depending on this unless the public API wrappers and manager getters
     * are not sufficient for your integration.
     */
    public ModernFactions getPlugin() {
        return plugin;
    }
}

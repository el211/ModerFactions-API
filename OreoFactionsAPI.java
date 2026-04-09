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

public final class OreoFactionsAPI {

    private static OreoFactionsAPI instance;

    private final OreoFactionsPlugin plugin;

    private OreoFactionsAPI(OreoFactionsPlugin plugin) {
        this.plugin = plugin;
    }

    
    
    

    

    public static void init(OreoFactionsPlugin plugin) {
        if (instance == null) {
            instance = new OreoFactionsAPI(plugin);
        }
    }

    

    public static OreoFactionsAPI get() {
        if (instance == null) {
            OreoFactionsPlugin p = (OreoFactionsPlugin) Bukkit.getPluginManager().getPlugin("ModernFactions");
            if (p != null) instance = new OreoFactionsAPI(p);
        }
        return instance;
    }

    
    
    

    
    public FactionManager getFactionManager() {
        return plugin.getFactionManager();
    }

    
    public PlayerManager getPlayerManager() {
        return plugin.getPlayerManager();
    }

    
    public ClaimManager getClaimManager() {
        return plugin.getClaimManager();
    }

    
    public RelationManager getRelationManager() {
        return plugin.getRelationManager();
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

    
    public DiscordManager getDiscordManager() {
        return plugin.getDiscordManager();
    }

    
    public StorageProvider getStorageProvider() {
        return plugin.getStorageProvider();
    }

    
    
    

    

    public Faction getFaction(String tag) {
        return plugin.getFactionManager().getFaction(tag);
    }

    

    public Collection<Faction> getAllFactions() {
        return plugin.getFactionManager().getAllFactions();
    }

    

    public List<Faction> getTopFactionsByPower(int limit) {
        return plugin.getFactionManager().getTopFactionsByPower(limit);
    }

    

    public List<Faction> getTopFactionsByPoints(int limit) {
        return plugin.getFactionManager().getTopFactionsByPoints(limit);
    }

    

    public boolean factionExists(String tag) {
        return plugin.getFactionManager().factionExists(tag);
    }

    

    public int getFactionCount() {
        return plugin.getFactionManager().getFactionCount();
    }

    
    
    

    

    public Faction createFaction(String tag, UUID leader, Player player) {
        Faction dummy = new Faction(tag, leader);
        FactionCreateEvent event = new FactionCreateEvent(dummy, leader, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return null;

        return plugin.getFactionManager().createFaction(tag, leader);
    }

    

    public boolean disbandFaction(Faction faction, Player player, FactionDisbandEvent.Reason reason) {
        FactionDisbandEvent event = new FactionDisbandEvent(faction, player, reason);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getFactionManager().disbandFaction(faction);
        return true;
    }

    

    public void saveFaction(Faction faction) {
        plugin.getFactionManager().saveFaction(faction);
    }

    
    
    

    

    public FPlayer getFPlayer(UUID uuid) {
        return plugin.getPlayerManager().getFPlayer(uuid);
    }

    

    public FPlayer getOrCreateFPlayer(Player player) {
        return plugin.getPlayerManager().getFPlayer(player.getUniqueId());
    }

    

    public Collection<FPlayer> getAllFPlayers() {
        return plugin.getPlayerManager().getAllPlayers();
    }

    

    public Faction getPlayerFaction(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        if (fp == null || !fp.hasFaction()) return null;
        return getFaction(fp.getFactionId());
    }

    

    public void saveFPlayer(FPlayer fPlayer) {
        plugin.getPlayerManager().saveFPlayer(fPlayer);
    }

    
    
    

    

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

    

    public boolean banPlayer(Faction faction, UUID target, Player banner) {
        FactionBanEvent event = new FactionBanEvent(faction, target, banner);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getBannedPlayers().add(target);
        saveFaction(faction);
        return true;
    }

    

    public boolean invitePlayer(Faction faction, UUID invited, Player inviter) {
        FactionInviteEvent event = new FactionInviteEvent(faction, invited, inviter);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getInvites().add(invited);
        saveFaction(faction);
        return true;
    }

    

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

    
    
    

    

    public boolean claim(Faction faction, Chunk chunk, Player player) {
        FactionClaimEvent event = new FactionClaimEvent(faction, chunk, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        return plugin.getClaimManager().claim(chunk, faction);
    }

    

    public boolean unclaim(Faction faction, String claimKey, Player player) {
        FactionUnclaimEvent event = new FactionUnclaimEvent(faction, claimKey, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getClaimManager().unclaim(claimKey);
        return true;
    }

    

    public boolean isClaimed(Chunk chunk) {
        return plugin.getClaimManager().isClaimed(chunk);
    }

    

    public Faction getFactionAt(Location location) {
        return plugin.getClaimManager().getFactionAt(location);
    }

    
    
    

    

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

    
    public boolean isAlly(Faction a, Faction b) {
        return plugin.getRelationManager().isAlly(a, b);
    }

    
    public boolean isEnemy(Faction a, Faction b) {
        return plugin.getRelationManager().isEnemy(a, b);
    }

    
    public boolean isTruce(Faction a, Faction b) {
        return plugin.getRelationManager().isTruce(a, b);
    }

    
    
    

    

    public double getPlayerPower(UUID uuid) {
        FPlayer fp = getFPlayer(uuid);
        return fp == null ? 0 : plugin.getPowerManager().getPlayerPower(fp);
    }

    

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

    

    public void freezeDTR(Faction faction) {
        if (faction == null) return;
        double oldDtr = faction.getDtr();
        plugin.getFactionManager().freezeDTR(faction);
        Bukkit.getPluginManager().callEvent(
                new FactionDTRChangeEvent(faction, oldDtr, faction.getDtr(), FactionDTRChangeEvent.ChangeReason.FREEZE));
    }

    
    
    

    

    public int getFactionPoints(Faction faction) {
        return plugin.getPointsManager().getPoints(faction);
    }

    

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

    

    public void setFactionPoints(Faction faction, int amount) {
        if (faction == null) return;
        int oldAmount = faction.getFactionPoints();
        FactionPointsChangeEvent event = new FactionPointsChangeEvent(
                faction, oldAmount, Math.max(0, amount), FactionPointsChangeEvent.ChangeReason.ADMIN_SET);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        plugin.getPointsManager().setPoints(faction, event.getNewAmount());
    }

    
    
    

    

    public double getFactionBalance(Faction faction) {
        return faction == null ? 0 : faction.getBalance();
    }

    

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

    
    
    

    

    public int getTNTBank(Faction faction) {
        return faction == null ? 0 : faction.getTntBank();
    }

    

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

    
    
    

    

    public void combatTag(Player player, Player attacker) {
        if (player == null) return;
        Faction faction = getPlayerFaction(player.getUniqueId());
        long durationMs = plugin.getConfig().getLong("fly.combat-tag-duration", 15) * 1000L;

        FactionCombatTagEvent event = new FactionCombatTagEvent(faction, player, attacker, durationMs);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        plugin.getCombatManager().tag(player);
    }

    

    public boolean isInCombat(UUID uuid) {
        return plugin.getCombatManager().isTagged(uuid);
    }

    

    public long getCombatTagSecondsRemaining(UUID uuid) {
        return plugin.getCombatManager().getTagRemainingSeconds(uuid);
    }

    

    public void removeCombatTag(UUID uuid) {
        plugin.getCombatManager().untag(uuid);
    }

    
    
    

    

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

    

    public boolean hasShield(Faction faction) {
        return faction != null && faction.isShieldActive()
                && faction.getShieldEndTime() > System.currentTimeMillis();
    }

    

    public void deactivateShield(Faction faction) {
        if (faction == null) return;
        faction.setShieldActive(false);
        faction.setShieldEndTime(0);
        saveFaction(faction);
    }

    
    
    

    

    public boolean activateGrace(Faction faction, long durationMs, Player activator) {
        if (faction == null) return false;
        FactionGraceActivateEvent event = new FactionGraceActivateEvent(faction, activator, durationMs);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        plugin.getGraceManager().setGrace(faction, durationMs);
        return true;
    }

    

    public boolean hasGrace(Faction faction) {
        return plugin.getGraceManager().hasGrace(faction);
    }

    

    public long getGraceRemainingMs(Faction faction) {
        return plugin.getGraceManager().getGraceRemaining(faction);
    }

    
    
    

    

    public int getUpgradeLevel(Faction faction, String upgradeKey) {
        return plugin.getUpgradeManager().getLevel(faction, upgradeKey);
    }

    

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

    

    public boolean deleteWarp(Faction faction, String name, Player actor) {
        if (!faction.getWarps().containsKey(name)) return false;

        FactionWarpDeleteEvent event = new FactionWarpDeleteEvent(faction, name, actor);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;

        faction.getWarps().remove(name);
        saveFaction(faction);
        return true;
    }

    

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

    
    
    

    

    public boolean hasPermission(Faction faction, UUID player, String permKey) {
        return plugin.getPermissionManager().has(faction, player, permKey);
    }

    
    
    

    

    public void recordBlockMine(Faction faction, org.bukkit.Material material) {
        plugin.getMissionManager().recordBlockMine(faction, material);
    }

    

    public void recordKill(Faction faction) {
        plugin.getMissionManager().recordKill(faction);
    }

    

    public void recordEnemyKill(Faction attacker, Faction victim) {
        plugin.getMissionManager().recordEnemyKill(attacker, victim);
    }

    

    public void recordClaim(Faction faction) {
        plugin.getMissionManager().recordClaim(faction);
    }

    

    public boolean startMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().startMission(faction, missionKey);
    }

    

    public boolean cancelMission(Faction faction, String missionKey) {
        return plugin.getMissionManager().cancelMission(faction, missionKey);
    }

    

    public boolean isMissionActive(Faction faction, String missionKey) {
        return plugin.getMissionManager().isMissionActive(faction, missionKey);
    }

    

    public int getMissionProgress(Faction faction, String missionKey) {
        return plugin.getMissionManager().getProgress(faction, missionKey);
    }

    
    
    

    

    public void auditLog(Faction faction, UUID actor, String action, String details) {
        plugin.getAuditManager().log(faction, actor, action, details);
    }

    
    
    

    

    public boolean isAlt(UUID uuid) {
        return plugin.getAltManager().isAlt(uuid);
    }

    

    public UUID getMainAccount(UUID altUuid) {
        return plugin.getAltManager().getMainAccount(altUuid);
    }

    
    
    

    
    
    

    
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

    
    
    

    

    public OreoFactionsPlugin getPlugin() {
        return plugin;
    }
}

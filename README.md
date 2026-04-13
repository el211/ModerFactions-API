# ModernFactions API

[![](https://jitpack.io/v/el211/ModerFactions-API.svg)](https://jitpack.io/#el211/ModerFactions-API)

The official public API for **ModernFactions** — a full-featured, GUI-first factions plugin for Paper/Spigot 1.20+.

Use this dependency to listen to faction events, integrate with the faction system, and access the full manager API from any external plugin.

---

## Requirements

- Java 17+
- Paper / Spigot 1.20.4+
- [ModernFactions](https://github.com/el211/OreoFactions) installed on the server

---

## Installation

### Maven

Add the JitPack repository and the API dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.el211</groupId>
        <artifactId>ModerFactions-API</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.el211:ModerFactions-API:1.0.0'
}
```

> Use `scope provided` / `compileOnly` — the full implementation is provided by the ModernFactions plugin JAR at runtime.

---

## Getting the API instance

```java
import com.oreofactions.api.ModernFactionsAPI;

ModernFactionsAPI api = ModernFactionsAPI.get();
```

Always call `get()` after the plugin has enabled. The safest place is in your own plugin's `onEnable` or in response to a `PluginEnableEvent`.

---

## API Overview

### Faction Queries

```java
// Get a faction by tag
Faction faction = api.getFaction("MyTag");

// Get all factions
Collection<Faction> all = api.getAllFactions();

// Get top N factions by power
List<Faction> top = api.getTopFactionsByPower(10);

// Get the faction at a location
Faction at = api.getFactionAt(player.getLocation());

// Check if a chunk is claimed
boolean claimed = api.isClaimed(chunk);
```

### Player (FPlayer) Queries

```java
FPlayer fp = api.getFPlayer(player.getUniqueId());
FPlayer fp = api.getOrCreateFPlayer(player);
Faction playerFaction = api.getPlayerFaction(player.getUniqueId());
```

### Faction Lifecycle

```java
// Create a faction (fires FactionCreateEvent)
Faction f = api.createFaction("Tag", leader.getUniqueId(), leader);

// Disband a faction (fires FactionDisbandEvent)
api.disbandFaction(faction, actor, FactionDisbandEvent.Reason.COMMAND);

// Persist changes
api.saveFaction(faction);
api.saveFPlayer(fPlayer);
```

### Members

```java
api.addMember(faction, player);            // fires FactionJoinEvent
api.removeMember(faction, player);         // fires FactionLeaveEvent
api.kickMember(faction, target, kicker);   // fires FactionKickEvent
api.banPlayer(faction, target, banner);    // fires FactionBanEvent
api.invitePlayer(faction, invited, inviter); // fires FactionInviteEvent
api.setRole(faction, target, FactionRole.OFFICER, actor); // fires FactionRoleChangeEvent
```

### Claims

```java
api.claim(faction, chunk, player);          // fires FactionClaimEvent
api.unclaim(faction, claimKey, player);     // fires FactionUnclaimEvent
api.isClaimed(chunk);
api.getFactionAt(location);
```

### Relations

```java
// Set relation between two factions (fires FactionRelationChangeEvent)
api.setRelation(from, to, FactionRelationType.ALLY, actor);

api.isAlly(a, b);
api.isEnemy(a, b);
api.isTruce(a, b);
```

### Power

```java
api.addPlayerPower(fPlayer, 5.0);
api.removePlayerPower(fPlayer, 5.0);
api.setPlayerPower(fPlayer, 10.0);
api.setFactionPower(faction, 100.0);
api.getPlayerPower(uuid);
```

### DTR

```java
api.setDTR(faction, 3.0);
api.freezeDTR(faction);
```

### Faction Balance

```java
api.depositBalance(faction, 500.0, player);
api.withdrawBalance(faction, 100.0, player);
api.setBalance(faction, 1000.0, player);
api.getFactionBalance(faction);
```

### TNT Bank

```java
api.depositTNT(faction, 64, player);
api.withdrawTNT(faction, 32, player);
api.getTNTBank(faction);
```

### Points

```java
api.addFactionPoints(faction, 100);
api.removeFactionPoints(faction, 50);
api.setFactionPoints(faction, 500);
api.getFactionPoints(faction);
```

### Combat Tag

```java
api.combatTag(player, attacker);
api.isInCombat(uuid);
api.getCombatTagSecondsRemaining(uuid);
api.removeCombatTag(uuid);
```

### Shield & Grace

```java
api.activateShield(faction, 3_600_000L, player);  // 1 hour
api.hasShield(faction);
api.deactivateShield(faction);

api.activateGrace(faction, 1_800_000L, player);   // 30 min
api.hasGrace(faction);
api.getGraceRemainingMs(faction);
```

### Peaceful Toggle

```java
// fires FactionPeacefulToggleEvent
api.setPeaceful(faction, true, player);
api.setPeaceful(faction, false, player, FactionPeacefulToggleEvent.ChangeReason.ADMIN);
```

### Warps & Home

```java
api.setWarp(faction, "base", location, null, player);
api.deleteWarp(faction, "base", player);
api.teleportToWarp(faction, "base", player);

api.setHome(faction, location, player);
api.teleportToHome(faction, player);
```

### Upgrades

```java
api.getUpgradeLevel(faction, "tnt_capacity");
api.purchaseUpgrade(faction, "tnt_capacity", buyer);
```

### Missions

```java
api.startMission(faction, "kill_enemies");
api.isMissionActive(faction, "kill_enemies");
api.getMissionProgress(faction, "kill_enemies");
api.cancelMission(faction, "kill_enemies");
```

### Permissions

```java
api.hasPermission(faction, playerUuid, "access_chest");
```

### Faction Chest

```java
ItemStack[] contents = api.loadFactionChest(faction);
api.saveFactionChest(faction, contents);
api.deleteFactionChest(faction);
api.invalidateFactionChestCache(faction);
```

### Tag Reserves

```java
api.isReserveSystemEnabled();
api.isReservedTag("MyTag");
api.canClaimReservedTag("MyTag", playerUuid);
api.reserveTag("MyTag", playerUuid);
api.unreserveTag("MyTag");
```

### CaveBlock

```java
api.isPlayerInCaveBlock(playerUuid);
api.isTrackedCaveBlock(location);
api.placeCaveBlock(player, blockLocation, uses);
api.interactWithCaveBlock(player, blockLocation);
api.breakCaveBlock(player, blockLocation);
api.exitCaveBlock(player);
```

### Dynamite

```java
DynamiteConfig cfg = api.getDynamiteConfig();
api.throwDynamite(player, cfg);
api.isDynamiteProjectile(entity);
api.getDynamiteProjectileConfig(projectileUuid);
```

### Scoreboard

```java
api.refreshFactionScoreboards();
api.refreshFactionScoreboard(player);
api.clearFactionScoreboard(player);
api.reloadFactionScoreboards();
```

### Custom Items

```java
api.isCustomItem(stack);
api.getCustomItemId(stack);
api.getRemainingUses(stack);
api.setRemainingUses(stack, 3);
api.isOnCustomItemCooldown(playerUuid, "my_item");
```

### Audit Log

```java
api.auditLog(faction, actorUuid, "KICK", "Kicked for inactivity");
```

### Alt Detection

```java
api.isAlt(uuid);
api.getMainAccount(altUuid);
```

### Cross-Server (RabbitMQ)

```java
api.isRabbitMQConnected();
api.publishCrossServerMessage(new CrossServerMessage(...));
```

### Raw Manager Access

Every internal manager is also directly accessible if you need deeper control:

```java
api.getFactionManager()
api.getPlayerManager()
api.getClaimManager()
api.getRelationManager()
api.getPowerManager()
api.getCombatManager()
api.getUpgradeManager()
api.getMissionManager()
api.getGraceManager()
api.getShieldManager()
api.getAuditManager()
api.getAltManager()
api.getDiscordManager()
api.getAntiSpamManager()
api.getCheckManager()
api.getReserveManager()
api.getCaveBlockManager()
api.getDynamiteManager()
api.getFactionChestManager()
api.getWarpTeleportManager()
api.getFactionScoreboardManager()
api.getCustomItemManager()
api.getStorageProvider()
api.getMongoDBManager()     // null when using SQLite
api.getVaultEconomy()
api.getLangManager()
api.getGuiManager()
api.getAdventure()
```

### Config Files

```java
api.getUpgradesConfig()
api.getPowerConfig()
api.getPermissionsConfig()
api.getDiscordConfig()
api.getFactionShopConfig()
api.getMissionsConfig()
api.getChatConfig()
api.getLangConfig()
api.getGuiConfig()
```

---

## Events

All events are in the `com.oreofactions.api.events` package. Cancellable events extend `FactionCancellableEvent` and can be cancelled to block the action.

| Event | Cancellable | Fired when |
|---|---|---|
| `FactionCreateEvent` | ✅ | A faction is created |
| `FactionDisbandEvent` | ✅ | A faction is disbanded |
| `FactionJoinEvent` | ✅ | A player joins a faction |
| `FactionLeaveEvent` | ✅ | A player leaves a faction |
| `FactionKickEvent` | ✅ | A player is kicked |
| `FactionBanEvent` | ✅ | A player is banned |
| `FactionInviteEvent` | ✅ | A player is invited |
| `FactionRoleChangeEvent` | ✅ | A member's role changes |
| `FactionClaimEvent` | ✅ | Land is claimed |
| `FactionUnclaimEvent` | ✅ | Land is unclaimed |
| `FactionRelationChangeEvent` | ✅ | Ally / enemy / truce state changes |
| `FactionPowerChangeEvent` | ❌ | A player or faction's power changes |
| `FactionDTRChangeEvent` | ❌ | DTR changes |
| `FactionPointsChangeEvent` | ❌ | Points change |
| `FactionBalanceChangeEvent` | ❌ | Faction bank balance changes |
| `FactionTNTBankChangeEvent` | ❌ | TNT bank changes |
| `FactionCombatTagEvent` | ✅ | A player is combat-tagged |
| `FactionShieldActivateEvent` | ✅ | Shield is activated |
| `FactionGraceActivateEvent` | ✅ | Grace period starts |
| `FactionPeacefulToggleEvent` | ✅ | Peaceful state toggled |
| `FactionRaidableEvent` | ❌ | A faction becomes raidable |
| `FactionUpgradeEvent` | ✅ | An upgrade is purchased |
| `FactionMissionStartEvent` | ✅ | A mission starts |
| `FactionMissionCompleteEvent` | ❌ | A mission is completed |
| `FactionWarpSetEvent` | ✅ | A warp is set |
| `FactionWarpDeleteEvent` | ✅ | A warp is deleted |
| `FactionWarpTeleportEvent` | ✅ | A player teleports to a warp |
| `FactionHomeSetEvent` | ✅ | Faction home is set |
| `FactionHomeTeleportEvent` | ✅ | A player teleports to faction home |
| `FactionChestAccessEvent` | ✅ | Faction chest is opened |
| `CustomItemUseEvent` | ✅ | A custom item is used |

### Example — listening to an event

```java
import com.oreofactions.api.events.FactionCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MyListener implements Listener {

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        // Cancel faction creation
        event.setCancelled(true);

        // Or read the data
        String tag = event.getFaction().getTag();
    }
}
```

---

## plugin.yml dependency

Add ModernFactions as a dependency in your `plugin.yml` to ensure it loads first:

```yaml
depend:
  - ModernFactions
```

---

## License

This API is provided for integration use with the ModernFactions plugin.

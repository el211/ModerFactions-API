# ModernFactions API

[![](https://jitpack.io/v/el211/ModerFactions-API.svg)](https://jitpack.io/#el211/ModerFactions-API)

Official public API for **ModernFactions**.

Use this artifact to:
- depend on the shared ModernFactions model and event classes
- retrieve the live `ModernFactionsAPI` instance from your plugin
- call stable public integration methods without depending on plugin internals

---

## Requirements

- Java 17+
- Paper / Spigot 1.20.4+
- [ModernFactions](https://github.com/el211/ModerFactions) installed on the server

---

## Installation

### Maven

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
        <version>2.0.0</version>
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
    compileOnly 'com.github.el211:ModerFactions-API:2.0.0'
}
```

Use `provided` / `compileOnly`. The implementation is supplied by the ModernFactions plugin at runtime.

---

## plugin.yml

Load after ModernFactions:

```yaml
depend:
  - ModernFactions
```

---

## Getting the API

```java
import com.oreofactions.api.ModernFactionsAPI;

ModernFactionsAPI api = ModernFactionsAPI.get();
```

Call `ModernFactionsAPI.get()` only after ModernFactions has enabled, typically in your own plugin's `onEnable()`.

---

## API Overview

### Faction queries

```java
Faction faction = api.getFaction("MyTag");
Collection<Faction> all = api.getAllFactions();
List<Faction> topPower = api.getTopFactionsByPower(10);
List<Faction> topPoints = api.getTopFactionsByPoints(10);
boolean exists = api.factionExists("MyTag");
int count = api.getFactionCount();
Faction at = api.getFactionAt(player.getLocation());
boolean claimed = api.isClaimed(chunk);
```

### Player queries

```java
FPlayer fPlayer = api.getFPlayer(player.getUniqueId());
FPlayer current = api.getOrCreateFPlayer(player);
Faction playerFaction = api.getPlayerFaction(player.getUniqueId());
```

### Faction lifecycle

```java
Faction created = api.createFaction("Tag", player.getUniqueId(), player);
api.disbandFaction(faction, player, FactionDisbandEvent.Reason.COMMAND);
api.saveFaction(faction);
api.saveFPlayer(fPlayer);
```

### Members

```java
api.addMember(faction, player);
api.removeMember(faction, player);
api.kickMember(faction, targetUuid, player);
api.banPlayer(faction, targetUuid, player);
api.invitePlayer(faction, targetUuid, player);
api.setRole(faction, targetUuid, FactionRole.MODERATOR, player);
```

### Claims and relations

```java
api.claim(faction, chunk, player);
api.unclaim(faction, claimKey, player);
api.setRelation(from, to, FactionRelationChangeEvent.RelationType.ALLY, player);
api.isAlly(a, b);
api.isEnemy(a, b);
api.isTruce(a, b);
```

### Power, DTR, points, balance, TNT

```java
api.getPlayerPower(player.getUniqueId());
api.addPlayerPower(fPlayer, 5.0);
api.removePlayerPower(fPlayer, 2.5);
api.setPlayerPower(fPlayer, 10.0);
api.setFactionPower(faction, 100.0);

api.setDTR(faction, 3.0);
api.freezeDTR(faction);

api.addFactionPoints(faction, 100);
api.removeFactionPoints(faction, 25);
api.setFactionPoints(faction, 500);
api.getFactionPoints(faction);

api.depositBalance(faction, 500.0, player);
api.withdrawBalance(faction, 100.0, player);
api.setBalance(faction, 1000.0, player);
api.getFactionBalance(faction);

api.depositTNT(faction, 64, player);
api.withdrawTNT(faction, 32, player);
api.getTNTBank(faction);
```

### Combat, peaceful, shield, grace

```java
api.combatTag(player, attacker);
api.isInCombat(player.getUniqueId());
api.getCombatTagSecondsRemaining(player.getUniqueId());
api.removeCombatTag(player.getUniqueId());

api.setPeaceful(faction, true, player);
api.setPeaceful(faction, false, player, FactionPeacefulToggleEvent.ChangeReason.ADMIN);

api.activateShield(faction, 3_600_000L, player);
api.hasShield(faction);
api.deactivateShield(faction);

api.activateGrace(faction, 1_800_000L, player);
api.hasGrace(faction);
api.getGraceRemainingMs(faction);
```

### Warps, home, upgrades, permissions

```java
api.setWarp(faction, "base", location, null, player);
api.deleteWarp(faction, "base", player);
api.teleportToWarp(faction, "base", player);

api.setHome(faction, location, player);
api.teleportToHome(faction, player);

api.getUpgradeLevel(faction, "tnt_capacity");
api.purchaseUpgrade(faction, "tnt_capacity", player);

api.hasPermission(faction, player.getUniqueId(), "access_chest");
```

### Missions and audit

```java
api.recordBlockMine(faction, material);
api.recordKill(faction);
api.recordEnemyKill(attackerFaction, victimFaction);
api.recordClaim(faction);

api.startMission(faction, "kill_enemies");
api.cancelMission(faction, "kill_enemies");
api.isMissionActive(faction, "kill_enemies");
api.getMissionProgress(faction, "kill_enemies");

api.auditLog(faction, actorUuid, "KICK", "Kicked for inactivity");
```

### Faction chest, reserves, caveblock, dynamite

```java
ItemStack[] contents = api.loadFactionChest(faction);
api.saveFactionChest(faction, contents);
api.deleteFactionChest(faction);
api.invalidateFactionChestCache(faction);

api.isReserveSystemEnabled();
api.isReservedTag("MyTag");
api.canClaimReservedTag("MyTag", playerUuid);
api.reserveTag("MyTag", playerUuid);
api.unreserveTag("MyTag");

api.isPlayerInCaveBlock(playerUuid);
api.isTrackedCaveBlock(location);
api.placeCaveBlock(player, blockLocation, uses);
api.interactWithCaveBlock(player, blockLocation);
api.breakCaveBlock(player, blockLocation);
api.exitCaveBlock(player);

DynamiteConfig cfg = api.getDynamiteConfig();
api.throwDynamite(player, cfg);
api.isDynamiteProjectile(entity);
api.getDynamiteProjectileConfig(projectileUuid);
```

### Scoreboard, custom items, cross-server

```java
api.refreshFactionScoreboards();
api.refreshFactionScoreboard(player);
api.clearFactionScoreboard(player);
api.reloadFactionScoreboards();

api.isCustomItem(stack);
api.getCustomItemId(stack);
api.getRemainingUses(stack);
api.setRemainingUses(stack, 3);
api.isOnCustomItemCooldown(playerUuid, "my_item");

api.isRabbitMQConnected();
api.publishCrossServerMessage(new CrossServerMessage());
```

---

## Events

All events are in `com.oreofactions.api.events`.

Cancellable events extend `FactionCancellableEvent` and can be cancelled to block the underlying action.

| Event | Cancellable | Fired when |
|---|---|---|
| `FactionCreateEvent` | yes | A faction is created |
| `FactionDisbandEvent` | yes | A faction is disbanded |
| `FactionJoinEvent` | yes | A player joins a faction |
| `FactionLeaveEvent` | yes | A player leaves a faction |
| `FactionKickEvent` | yes | A player is kicked |
| `FactionBanEvent` | yes | A player is banned |
| `FactionInviteEvent` | yes | A player is invited |
| `FactionRoleChangeEvent` | yes | A member role changes |
| `FactionClaimEvent` | yes | Land is claimed |
| `FactionUnclaimEvent` | yes | Land is unclaimed |
| `FactionRelationChangeEvent` | yes | Relation state changes |
| `FactionPowerChangeEvent` | no | Power changes |
| `FactionDTRChangeEvent` | no | DTR changes |
| `FactionPointsChangeEvent` | no | Points change |
| `FactionBalanceChangeEvent` | no | Balance changes |
| `FactionTNTBankChangeEvent` | no | TNT bank changes |
| `FactionCombatTagEvent` | yes | A player is combat tagged |
| `FactionShieldActivateEvent` | yes | A shield is activated |
| `FactionGraceActivateEvent` | yes | Grace starts |
| `FactionPeacefulToggleEvent` | yes | Peaceful state changes |
| `FactionRaidableEvent` | no | A faction becomes raidable |
| `FactionUpgradeEvent` | yes | An upgrade is purchased |
| `FactionMissionStartEvent` | yes | A mission starts |
| `FactionMissionCompleteEvent` | no | A mission completes |
| `FactionWarpSetEvent` | yes | A warp is set |
| `FactionWarpDeleteEvent` | yes | A warp is deleted |
| `FactionWarpTeleportEvent` | yes | A player teleports to a warp |
| `FactionHomeSetEvent` | yes | Faction home is set |
| `FactionHomeTeleportEvent` | yes | A player teleports home |
| `FactionChestAccessEvent` | yes | Faction chest access is attempted |
| `CustomItemUseEvent` | yes | A custom item is used |

### Example listener

```java
import com.oreofactions.api.events.FactionCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MyListener implements Listener {

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        event.setCancelled(true);
        String tag = event.getFaction().getTag();
    }
}
```

---

## API Scope

This artifact exposes the stable public contract:
- `ModernFactionsAPI`
- `ModernFactionsProvider`
- shared models
- shared DTOs
- public events

It does not expose the plugin's internal manager or config objects to external plugins.

---

## License

This API is provided for integration use with the ModernFactions plugin.

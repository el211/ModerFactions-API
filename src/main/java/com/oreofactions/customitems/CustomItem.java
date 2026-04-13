package com.oreofactions.customitems;

import org.bukkit.Material;

import java.util.List;

public class CustomItem {

    private final String id;
    private final Material type;
    private final String name;
    private final List<String> lore;
    private final int modelData;
    private final ActionConfig action;
    private final int cooldownSeconds;
    private final int maxUses;
    private final List<String> interacts;

    private String nexoId;
    private String itemsAdderId;
    private boolean furniture = false;

    public CustomItem(String id, Material type, String name, List<String> lore,
                      int modelData, ActionConfig action,
                      int cooldownSeconds, int maxUses, List<String> interacts) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.modelData = modelData;
        this.action = action;
        this.cooldownSeconds = cooldownSeconds;
        this.maxUses = maxUses;
        this.interacts = interacts;
    }

    public String getId() { return id; }
    public Material getType() { return type; }
    public String getName() { return name; }
    public List<String> getLore() { return lore; }
    public int getModelData() { return modelData; }
    public ActionConfig getAction() { return action; }
    public int getCooldownSeconds() { return cooldownSeconds; }
    public int getMaxUses() { return maxUses; }
    public List<String> getInteracts() { return interacts; }

    public String getNexoId() { return nexoId; }
    public void setNexoId(String nexoId) { this.nexoId = nexoId; }

    public String getItemsAdderId() { return itemsAdderId; }
    public void setItemsAdderId(String itemsAdderId) { this.itemsAdderId = itemsAdderId; }

    public boolean isFurniture() { return furniture; }
    public void setFurniture(boolean furniture) { this.furniture = furniture; }

    public boolean isNexoBased() { return nexoId != null && !nexoId.isEmpty(); }
    public boolean isItemsAdderBased() { return itemsAdderId != null && !itemsAdderId.isEmpty(); }

    public enum ActionType {
        COMMAND, HEAL, JUMP, SPEED, REGEN, GOD, TELEPORT, MONEY, RANDOMMONEY,
        UNCLAIM, CAVEBLOCK, DYNAMITE, PICKAXE
    }

    public static class ActionConfig {
        public ActionType type;

        public String command;
        public boolean runAsConsole = false;

        public int duration = 4;
        public int amplifier = 0;

        public String teleportLocation;

        public double amount;
        public double maxAmount;

        public int area = 3;
        public boolean useSpecificChunk = false;
        public int chunkX = 0;
        public int chunkZ = 0;

        public String msgHeader        = "&6&l⚑ Storage Finder &7({radius} chunk radius)&6:";
        public String msgHeaderChunk   = "&6&l⚑ Storage Finder &7(chunk &8{cx}&7,&8{cz}&7)&6:";
        public String msgChunkLabel    = " &8[{cx},{cz}]";
        public String msgContainerLine = "  &e{type}&7: &6{count}";
        public String msgNoResults     = "&7  No storage containers found nearby.";
        public String msgFooter        = "&6Total: &e{total} &6container(s) across &e{chunks} &6chunk(s).";

        public String effectOnHold;
        public boolean twoBlocks;
        public int customRadius = 1;
        public int customDurability = -1;

        public int fuseTicks = 60;
        public double velocityMultiplier = 1.4;
        public float explosionPower = 3.0f;
        public boolean explodeOnImpact = false;
        public boolean breakBlocks = true;
        public boolean damageEntities = true;
    }
}

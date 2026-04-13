package com.oreofactions.dynamite;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Runtime configuration snapshot for a dynamite item.
 * Populated by the plugin from config.yml and passed through the API.
 */
public class DynamiteConfig {

    public boolean enabled;
    public Material material;
    public int customModelData;
    public String displayName;
    public List<String> lore;
    public boolean consumeItem;
    public int cooldownSeconds;
    public int fuseTicks;
    public double velocityMultiplier;
    public float explosionPower;
    public boolean explodeOnImpact;
    public boolean breakBlocks;
    public boolean damageEntities;
    public String permission;

    public transient ItemStack displayItem;

    public DynamiteConfig() {}

    /** Builds a displayable ItemStack for this dynamite type. */
    public ItemStack buildItem(int amount) {
        if (material == null) return new ItemStack(Material.PAPER, Math.max(1, amount));
        ItemStack stack = new ItemStack(material, Math.max(1, amount));
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;
        if (displayName != null) meta.setDisplayName(displayName);
        if (customModelData > 0) meta.setCustomModelData(customModelData);
        List<String> fullLore = lore != null ? new ArrayList<>(lore) : new ArrayList<>();
        fullLore.add("§7Uses: §fThrowable (§e1§7 per throw)");
        meta.setLore(fullLore);
        stack.setItemMeta(meta);
        return stack;
    }

    /** Returns true if the given ItemStack matches this dynamite type. */
    public boolean matches(ItemStack item) {
        if (item == null || material == null || item.getType() != material) return false;
        if (customModelData > 0) {
            if (!item.hasItemMeta()) return false;
            ItemMeta m = item.getItemMeta();
            if (!m.hasCustomModelData() || m.getCustomModelData() != customModelData) return false;
        }
        return true;
    }
}

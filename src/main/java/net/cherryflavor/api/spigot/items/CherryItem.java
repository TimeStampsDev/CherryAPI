package net.cherryflavor.api.spigot.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import net.cherryflavor.api.tools.RomanNumber;
import net.cherryflavor.api.tools.TextFormat;

/**
 * Created on 4/5/2021
 * Time 12:31 AM
 */

 @SuppressWarnings("deprecation")
public class CherryItem {

    private Material material;
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public CherryItem(Material material) {
        this.material = material;
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public CherryItem(Material material, int amount) {
        this.material = material;
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    @Deprecated
    public CherryItem(Material material, int amount, short damage) {
        this.material = material;
        this.itemStack = new ItemStack(material, amount, damage);
        this.itemMeta = itemStack.getItemMeta();
    }

    @Deprecated
    public CherryItem(Material material, int amount, short damage, Byte data) {
        this.material = material;
        this.itemStack = new ItemStack(material, amount, damage, data);
        this.itemMeta = itemStack.getItemMeta();
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Item will never break!
     * @param unbreakable
     * @return
     */
    @Deprecated
    public CherryItem setUnbreakable(boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Adds ItemFlags, eg (HIDE_ATTRIBUTES, HIDE_ENCHANTS)
     * @param flags
     * @return
     */
    public CherryItem addItemFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes ItemFlags
     * @param flags
     * @return
     */
    public CherryItem removeItemFlags(ItemFlag... flags) {
        this.itemMeta.removeItemFlags(flags);
        return this;
    }

    /**
     * Sets the data, only works for multiple-versioned items (eg, wool, carpet, beds, doors)
     * @param data
     * @return
     */
    @Deprecated
    public CherryItem setData(Byte data) {
        MaterialData mData = new MaterialData(material, data);
        this.itemStack.setData(mData);
        return this;
    }

    /**
     * Adds enchantment
     * @param enchantment
     * @param level
     * @return
     */
    public CherryItem addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Adds enchantment unsafely
     * @param enchantment
     * @param level
     * @return
     */
    public CherryItem addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes enchant
     * @param enchantment
     * @return
     */
    public CherryItem removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Sets Item name
     * @param name
     * @param color
     * @return
     */
    public CherryItem setDisplayName(String name, boolean color) {
        if (color == true) {
            this.itemMeta.setDisplayName(TextFormat.colorize(name));
        } else {
            this.itemMeta.setDisplayName(name);
        }
        return this;
    }

    /**
     * Sets the lore
     * @param color
     * @param lore
     * @return
     */
    public CherryItem setLore(boolean color, String... lore) {
        List<String> before = TextFormat.convertArrayToList(lore);

        if (color == true) {
            List<String> after = new ArrayList<>();
            for (String b : before) {
                after.add(TextFormat.colorize(b));
            }
            itemMeta.setLore(after);
        } else {
            itemMeta.setLore(before);
        }
        return this;
    }

    /**
     * Sets durability, will not work if unbreakable is set to true
     * @param damage
     * @return
     */
    @Deprecated
    public CherryItem setDurability(short damage) {
        this.itemStack.setDurability(damage);
        return this;
    }

    /**
     * Sets amount of item
     * @param amount
     * @return
     */
    public CherryItem setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Creates the itemstack after additions have been made.
     * @return
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
    
    /**
     * Previews the item in console (used mainly for debugging)
     * @param itemStack
     */
    public static void previewItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> info = new ArrayList<>();

        info.addAll(TextFormat.convertArrayStringToList(
            "Name: " + itemMeta.getDisplayName(),
            "Material: " + TextFormat.toCamelCase(String.valueOf(itemStack.getType()).replace("LEGACY_", "")),
            "Durability: " + String.valueOf(itemStack.getDurability()),
            "Stack size: " + String.valueOf(itemStack.getAmount()),
            "Unbreakable: " + (itemMeta.isUnbreakable() ? "true" : "false")));


        if (itemMeta.hasLore()) {
            info.add("Lore:");
            for (String L : itemMeta.getLore()) {
                info.add("  - " + L);
            }
        } else {
            info.add("Lore: None");
        }

        if (itemMeta.hasEnchants()) {
            info.add("Enchantments:");

            Map<Enchantment, Integer> eMap = itemMeta.getEnchants();
            int longestE = TextFormat.getLongestString(ItemManipulator.enchantListToStringList(eMap.keySet())).length();
            
            info.add("  " + TextFormat.addRightPadding("Enchant:", ' ', longestE) + " Level:");
    
            for (Enchantment E : eMap.keySet()) {
    
                String enchant = TextFormat.toCamelCase(E.getName());
                String level = RomanNumber.toRoman(eMap.get(E))  + " (" + eMap.get(E) + ")";
    
                info.add("  " + TextFormat.addRightPadding(enchant, ' ', longestE) + " " + level);
            }
        } else {
            info.add("Enchantments: None");
        }

        if (!itemMeta.getItemFlags().isEmpty()) {
            info.add("Item Flags: ");

            for (ItemFlag flag : itemMeta.getItemFlags()) {
                info.add("  " + TextFormat.toCamelCase(String.valueOf(flag)));
            }
        } else {
            info.add("Item Flags: None");
        }

        System.out.println(TextFormat.addLeftPadding("", '=', 30));
        for (String i : info) {
            System.out.println("  " + i);
        }
        System.out.println(TextFormat.addLeftPadding("", '=', 30));
    }

}

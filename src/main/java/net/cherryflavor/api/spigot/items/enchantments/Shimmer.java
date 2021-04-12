package net.cherryflavor.api.spigot.items.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created on 4/5/2021
 * Time 12:31 AM
 */

@SuppressWarnings("deprecation")
public class Shimmer extends Enchantment {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public Shimmer(NamespacedKey i) {
        super(i);
    }


    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public int ID = 70;

    @Override
    public boolean canEnchantItem(ItemStack arg0) {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment arg0) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public String getName() {
        return "SHIMMER";
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

}
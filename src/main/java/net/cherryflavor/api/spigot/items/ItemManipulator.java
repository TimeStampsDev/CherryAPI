package net.cherryflavor.api.spigot.items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import net.cherryflavor.api.spigot.items.enchantments.Shimmer;
import net.cherryflavor.api.tools.RomanNumber;
import net.cherryflavor.api.tools.TextFormat;

/**
 * Created on 4/5/2021
 * Time 12:31 AM
 */

@SuppressWarnings("deprecation")
public class ItemManipulator {

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Displays all enchantments and their start level, and highest level in console (debugging tool)
     */
    public static void displayAllEnchantments() {
        int c = 1;

        int longest = TextFormat.getLongestString(enchantListToStringList(Enchantment.values())).length();

        longest = 25;

        System.out.println(longest);
        System.out.println("Enchantment:".length());

        System.out.println("     " + TextFormat.addRightPadding("Enchantment: ", ' ', longest) + TextFormat.addRightPadding("Start Lvl:", ' ', 15) + TextFormat.addRightPadding("Max Lvl:", ' ', 15));

        for (Enchantment e : Enchantment.values()) {
            String ench = TextFormat.toCamelCase(e.getKey().toString().replace("minecraft:", "").replace("bukkit:", ""));
            System.out.println(TextFormat.addRightPadding("#"+ c, ' ', 5) + TextFormat.addRightPadding(ench, ' ', longest) + " " + TextFormat.addRightPadding(RomanNumber.toRoman(e.getStartLevel()) + " (" + e.getStartLevel() + ")", ' ', 15) + TextFormat.addRightPadding(RomanNumber.toRoman(e.getMaxLevel()) + " (" + e.getMaxLevel() + ")", ' ', 15));
            c++;
        }
    }

    public static List<Enchantment> enchantArrayToList(Enchantment... enchantments) {
        List<Enchantment> list = new ArrayList<>();
        for (Enchantment ench : enchantments) {
            list.add(ench);
        }
        return list;
    }

    /**
     * Converts enchantment list to a string list
     * @param set
     * @return
     */
    public static List<String> enchantListToStringList(Set<Enchantment> set) {
        List<String> eList = new ArrayList<>();
        for (Enchantment e : set) {
            eList.add(e.getName());
        }
        return eList;
    }

        /**
     * Converts enchantment list to a string list
     * @param set
     * @return
     */
    public static List<String> enchantListToStringList(Enchantment[] set) {
        List<String> eList = new ArrayList<>();
        for (Enchantment e : set) {
            eList.add(e.getName());
        }
        return eList;
    }
    
    /**
     * Converts enchantment list to a string list
     * @param enchantments
     * @return
     */
    public static List<String> enchantListToStringList(List<Enchantment> enchantments) {
        List<String> eList = new ArrayList<>();
        for (Enchantment e : enchantments) {
            eList.add(e.getName());
        }
        return eList;
    }

    /**
     * Converts item flag list to a string list
     * @param flags
     * @return
     */
    public static List<String> itemFlagListToStringList(Set<ItemFlag> flags) {
        List<String> iFList = new ArrayList<>();
        for (ItemFlag flag : flags) {
            iFList.add(flag.name());
        }
        return iFList;
    }

    private static NamespacedKey shimmerEnchantKey;

    public static void registerCustomEnchants() {
        shimmerEnchantKey = NamespacedKey.fromString("shimmer");
         
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Shimmer shimmer = new Shimmer(shimmerEnchantKey);
            if (!enchantArrayToList(Enchantment.values()).contains(shimmer)) {
                Enchantment.registerEnchantment(shimmer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

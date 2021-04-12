package net.cherryflavor.api.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */

public class TextFormat {

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Provides pluralization to text based off int
     * @param size
     * @return
     */
    public static String pluralization(int size) {
        if (size == 1) {
            return "";
        } else {
            return "s";
        }
    }

    /**
     * Colorizes provided string
     * @param message
     * @return
     */
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Adds 'c' # of times = length of maxLength to the LEFT
     * @param string
     * @param c
     * @param maxLength
     * @return
     */
    public static String addLeftPadding(String string, char c, int maxLength) {
        String result =
                String.format("%" + (maxLength) + "s", string).replace(' ', c);
        return result;
    }

    /**
     * Adds 'c' # of times = length of maxLength to the RIGHT
     * @param string
     * @param c
     * @param maxLength
     * @return
     */
    public static String addRightPadding(String string, char c, int maxLength) {
        String result =
                String.format("%" + (-maxLength) + "s", string).replace(' ', c);
        return result;
    }

    /**
     * Returns string to
     * <blockquote><pre>
     * "hello, world"   to   "ello, worl" </pre></blockquote>
     * @param string
     * @return String
     */
    public static String stripOutliers(String string) {
        string = string.substring(1);
        string = string.substring(0, string.length() - 1);
        return string;
    }

    /**
     * Convert String Array to List
     * @return
     */
    public static List<String> convertArrayToList(String[] array) {
        List<String> list = new ArrayList<>();

        for (String s : array) {
            list.add(s);
        }

        return list;
    }

     /**
     * Convert String Array to List
     * @return
     */
    public static List<String> convertArrayStringToList(String... array) {
        List<String> list = new ArrayList<>();

        for (String s : array) {
            list.add(s);
        }

        return list;
    }

    /**
     * Ex. "TESTING_THIS_NEW_PLUGIN" => "Testing This New Plugin"
     * @param s
     * @return
     */
    public static String toCamelCase(String s){
        String[] parts = s.split("_");
        String camelCaseString = "";
        int n = 0;
        for (String part : parts){
            n++;
            if (n == parts.length) {
                camelCaseString = camelCaseString + toProperCase(part);
            } else {
                camelCaseString = camelCaseString + toProperCase(part) + " ";
            }
        }
        return camelCaseString;
     }

     /**
      * Upper Case first letter, then Lower case the rest
      * @param s
      * @return
      */
     public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                   s.substring(1).toLowerCase();
    }

    /**
     * Gets Shortest String in a list
     * @param stringList
     * @return
     */
    public static String getShortestString(List<String> stringList) {
        String shortest = stringList.get(0);

        for (String str : stringList) {
            if (str.length() < shortest.length()) {
                shortest = str;
            }
        }

        return shortest;
    }

    /**
     * Gets Longest String in a list
     * @param stringList
     * @return
     */
    public static String getLongestString(List<String> stringList) {

        String longest = stringList.get(0);

        for (String str : stringList) {
            if (str.length() > longest.length()) {
                longest = str;
            }
        }

        return longest;
    }

}

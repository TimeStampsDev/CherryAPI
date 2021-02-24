package net.cherryflavor.api.tools;

import net.cherryflavor.api.chat.tools.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.chat.ComponentSerializer;

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

}

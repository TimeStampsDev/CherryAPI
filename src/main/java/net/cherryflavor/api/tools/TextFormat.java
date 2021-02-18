package net.cherryflavor.api.tools;

import jdk.internal.vm.compiler.collections.EconomicMap;
import net.cherryflavor.api.tools.chat.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TextFormat {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String addLeftPadding(String string, char c, int maxLength) {
        String result =
                String.format("%" + (maxLength) + "s", string).replace(' ', c);
        return result;
    }

    public static String addRightPadding(String string, char c, int maxLength) {
        String result =
                String.format("%" + (-maxLength) + "s", string).replace(' ', c);
        return result;
    }

}

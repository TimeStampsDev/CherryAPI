package net.cherryflavor.api.tools;

import net.cherryflavor.api.tools.chat.ChatColor;

public class TextFormat {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}

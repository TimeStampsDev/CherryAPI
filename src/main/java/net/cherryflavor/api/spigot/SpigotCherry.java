package net.cherryflavor.api.spigot;

import net.cherryflavor.api.chat.tools.ComponentManipulator;
import net.cherryflavor.api.chat.tools.HoverComponent;
import net.cherryflavor.api.mojang.GameAchievements;
import net.cherryflavor.api.mojang.MojangAPI;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class SpigotCherry extends JavaPlugin {

    private ServerAPI serverAPI;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public void onEnable() {
        serverAPI = new ServerAPI(this);

        MojangAPI.printServiceStatus();

        if (serverAPI.isOnline("PoppingCherries")) {
            OnlinePlayer player = serverAPI.getOnlinePlayer("PoppingCherries");
            TextComponent component = new HoverComponent(TextFormat.colorize("&e[" + GameAchievements.GETTING_WOOD.getAchievementLabel() + "]")).showAchievement(GameAchievements.GETTING_WOOD);
            ComponentManipulator.addOpenURL(component, "https://minecraft.gamepedia.com/Achievement");
            player.sendMessage(component);
        }
    }

    public void onDisable() {

    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

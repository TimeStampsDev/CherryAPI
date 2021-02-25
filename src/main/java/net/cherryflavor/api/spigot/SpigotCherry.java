package net.cherryflavor.api.spigot;

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

    }

    public void onDisable() {

        serverAPI.getWorldManager().saveWorlds();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

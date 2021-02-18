package net.cherryflavor.api.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCherry extends JavaPlugin {

    private ServerAPI serverAPI;

    public void onEnable() {
        serverAPI = new ServerAPI(this);
    }

    public void onDisable() {

    }

}

package net.cherryflavor.api.bungee;

import net.cherryflavor.api.database.DatabaseManager;
import net.md_5.bungee.api.plugin.Plugin;

public class ProxyAPI {

    public Plugin plugin;

    public ProxyAPI(Plugin plugin) {
        this.plugin = plugin;

        DatabaseManager.createFolder();
    }

}

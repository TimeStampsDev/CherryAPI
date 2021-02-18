package net.cherryflavor.api.bungee;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.configuration.Configuration;
import net.cherryflavor.api.database.DatabaseManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public class ProxyAPI {

    private Plugin plugin;
    private static ProxyAPI api;
    private static DatabaseManager databaseManager;

    private static Configuration basicMessagesConfig;
    private static Configuration config;

    public ProxyAPI(Plugin plugin) {
        this.plugin = plugin;

        CherryConfig.makeFolder("plugins/CherryAPI");

        databaseManager = new DatabaseManager(this);

        CherryConfig.createResource("bungee/basic-messages.yml",new File("plugins/CherryAPI/basic-messages.yml"));
        CherryConfig.createResource("bungee/config.yml",new File("plugins/CherryAPI/config.yml"));

        basicMessagesConfig = new CherryConfig(new File("plugins/CherryAPI/basic-messages.yml")).getConfig();
        config = new CherryConfig(new File("plugins/CherryAPI/config.yml")).getConfig();
    }

    public static ProxyAPI getAPI() { return api; }
    public static DatabaseManager getDatabaseManager() { return databaseManager; }

    public Plugin getPlugin() { return plugin; }
    public Configuration getBasicMessages() { return basicMessagesConfig; }
    public Configuration getConfig() { return config; }

    public Collection<ProxiedPlayer> getOnlinePlayers() {
        return plugin.getProxy().getPlayers();
    }

    public ProxiedPlayer getOnlinePlayer(UUID uuid) {
        for (ProxiedPlayer onlinePlayer : getOnlinePlayers()) {
            if (onlinePlayer.getUniqueId().equals(uuid)) {
                return onlinePlayer;
            }
        }
        return null;
    }

    public void registerCommand(BungeeCherryCommand... commands) {
        for (BungeeCherryCommand command : commands) {
            plugin.getProxy().getPluginManager().registerCommand(plugin, command);
        }
    }

}

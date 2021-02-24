package net.cherryflavor.api.bungee;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.command.BungeeCommandManager;
import net.cherryflavor.api.bungee.event.BungeeListenerManager;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.bungee.plugin.commands.ServerCommand;
import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.configuration.Configuration;
import net.cherryflavor.api.database.DatabaseManager;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.*;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ProxyAPI {

    private Plugin plugin;
    private static ProxyAPI api;
    private static DatabaseManager databaseManager;
    private static BungeeCommandManager commandManager;
    private static BungeeListenerManager listenerManager;

    private static Configuration basicMessagesConfig;
    private static Configuration config;

    private static Map<ServerInfo, String> serverPermissionsMap;
    private static Map<ServerInfo, Boolean> serverStatusMap;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     *
     * @param plugin
     */
    public ProxyAPI(Plugin plugin) {
        this.plugin = plugin;
        this.api = this;

        CherryConfig.makeFolder("plugins/CherryAPI");

        databaseManager = new DatabaseManager(this);
        commandManager = new BungeeCommandManager(this);
        listenerManager = new BungeeListenerManager(this);

        CherryConfig.createResource("bungee/basic-messages.yml",new File("plugins/CherryAPI/basic-messages.yml"));
        CherryConfig.createResource("bungee/config.yml",new File("plugins/CherryAPI/config.yml"));

        basicMessagesConfig = new CherryConfig(new File("plugins/CherryAPI/basic-messages.yml")).getConfig();
        config = new CherryConfig(new File("plugins/CherryAPI/config.yml")).getConfig();

        serverPermissionsMap = new HashMap<>();
        serverStatusMap = new HashMap<>();

        checkAllServers();

        registerCommand(new ServerCommand());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * @return
     */
    public Plugin getPlugin() { return plugin; }

    /**
     *
     * @return
     */
    public static ProxyAPI getAPI() { return api; }

    /**
     * Gets DatabaseManager
     * @return
     */
    public static DatabaseManager getDatabaseManager() { return databaseManager; }

    /**
     * Gets CommandManager
     * @return
     */
    public static BungeeCommandManager getCommandManager() { return commandManager; }

    /**
     * Gets ListenerManager
     * @return
     */
    public static BungeeListenerManager getListenerManager() { return listenerManager; }

    /**
     * Gets Basic-Messages Config
     * @return
     */
    public Configuration getBasicMessages() { return basicMessagesConfig; }

    /**
     * Gets Config
     * @return
     */
    public Configuration getConfig() { return config; }

    /**
     * Get collect of online players
     * @return
     */
    public Collection<ProxiedPlayer> getOnlinePlayers() {
        return plugin.getProxy().getPlayers();
    }

    /**
     * Method #1
     * Returns player with uuid
     * @param uuid
     * @return
     */
    public ProxiedPlayer getPlayer(UUID uuid) {
        return plugin.getProxy().getPlayer(uuid);
    }

    /**
     * Method #2
     * Returns player with playerName
     * @param playerName
     * @return
     */
    public ProxiedPlayer getPlayer(String playerName) {
        return plugin.getProxy().getPlayer(playerName);
    }

    /**
     * Must check if player is online before using
     * Use isOnline(player/playerName) to check
     * @param name
     * @return
     */
    public BungeePlayer getBungeePlayer(String name) {
        return new BungeePlayer(name);
    }

    /**
     * Method #1
     * Check if player is online
     * @param playerName
     * @return
     */
    public boolean isOnline(String playerName) {
        return (plugin.getProxy().getPlayer(playerName) != null);
    }

    /**
     * Method #2
     * Check if player is online
     * @param player
     * @return
     */
    public boolean isOnline(ProxiedPlayer player) {
        return (player != null);
    }

    /**
     * Returns a string list of servers that are online
     * @return
     */
    public List<String> getStringListServers() {
        List<String> stringList = new ArrayList<>();
        for (ServerInfo server : getPlugin().getProxy().getServers().values()) {
            stringList.add(server.getName().toLowerCase());
        }
        return stringList;
    }

    /**
     * Returns ServerPermissionsMap
     * @return
     */
    public static Map<ServerInfo, String> getServerPermissionsMap() {
        return serverPermissionsMap;
    }

    /**
     * Returns ServerPermissionsMap
     * @return
     */
    public static Map<ServerInfo, Boolean> getServerStatusMap() {
        return serverStatusMap;
    }

    public boolean isServerOnline(ServerInfo info) {
        return serverStatusMap.get(info);
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * If debug is set to true in config.yml
     * Debug will send message
     * @param message
     */
    public void debug(String... message) {
        if (config.getBoolean("debug") == true) {
            for (String m : message) {
                plugin.getLogger().info(m);
            }
        }
    }

    /**
     * Register command
     * @param commands
     */
    public void registerCommand(BungeeCherryCommand... commands) {
        for (BungeeCherryCommand command : commands) {
            plugin.getProxy().getPluginManager().registerCommand(plugin, command);
        }
    }

    /**
     * Unregister command
     * @param commands
     */
    public void unregisterCommand(BungeeCherryCommand... commands) {
        for (BungeeCherryCommand command : commands) {
            plugin.getProxy().getPluginManager().unregisterCommand(command);
        }
    }

    private void checkAllServers() {
        for (ServerInfo server : getPlugin().getProxy().getServers().values()) {
            serverPermissionsMap.put(server, server.getName().toLowerCase() + ".cherryservers");
            server.ping(new Callback<ServerPing>() {
                @Override
                public void done(ServerPing serverPing, Throwable throwable) {
                    if (throwable != null) {
                        serverStatusMap.put(server, false);
                        return;
                    } else {
                        serverStatusMap.put(server, true);
                        return;
                    }
                }
            });
        }
    }

}

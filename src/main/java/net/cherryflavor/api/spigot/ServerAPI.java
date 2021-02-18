package net.cherryflavor.api.spigot;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.configuration.Configuration;
import net.cherryflavor.api.database.DatabaseManager;
import net.cherryflavor.api.spigot.comms.ServerMessageListener;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class ServerAPI {

    public JavaPlugin plugin;
    private static ServerAPI api;
    private static DatabaseManager databaseManager;

    private static Configuration basicMessagesConfig;
    private static Configuration config;

    private static Map<UUID, PermissionAttachment> permissionsMap;

    public ServerAPI(JavaPlugin plugin) {
        this.plugin = plugin;

        CherryConfig.makeFolder("plugins/CherryAPI");

        databaseManager = new DatabaseManager(this);

        CherryConfig.createResource("spigot/basic-messages.yml",new File("plugins/CherryAPI/basic-messages.yml"));
        CherryConfig.createResource("spigot/config.yml",new File("plugins/CherryAPI/config.yml"));

        basicMessagesConfig = new CherryConfig(new File("plugins/CherryAPI/basic-messages.yml")).getConfig();
        config = new CherryConfig(new File("plugins/CherryAPI/config.yml")).getConfig();

        permissionsMap = new HashMap<>();

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", new ServerMessageListener(this));
    }

    public static ServerAPI getAPI() { return api; }
    public static DatabaseManager getDatabaseManager() { return databaseManager; }

    public JavaPlugin getPlugin() { return plugin; }
    public Configuration getBasicMessages() { return basicMessagesConfig; }
    public Configuration getConfig() { return config; }

    public Map<UUID, PermissionAttachment> getPermissionsMap() { return permissionsMap; }

    public Collection<? extends Player> getOnlinePlayers() {
        return plugin.getServer().getOnlinePlayers();
    }

    public Player getOnlinePlayer(UUID uuid) {
        for (Player onlinePlayer : getOnlinePlayers()) {
            if (onlinePlayer.getUniqueId() == uuid) {
                return onlinePlayer;
            }
        }
        return null;
    }



}

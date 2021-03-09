package net.cherryflavor.api.spigot;

import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.configuration.Configuration;
import net.cherryflavor.api.database.DatabaseManager;
import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.spigot.command.ServerCherryCommand;
import net.cherryflavor.api.spigot.command.ServerCommandManager;
import net.cherryflavor.api.spigot.comms.ServerMessageController;
import net.cherryflavor.api.spigot.comms.ServerMessageListener;
import net.cherryflavor.api.spigot.event.ServerListenerManager;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.spigot.plugin.commands.WorldManageCommand;
import net.cherryflavor.api.spigot.plugin.commands.WorldTeleportCommand;
import net.cherryflavor.api.spigot.plugin.events.JoinEvent;
import net.cherryflavor.api.spigot.world.WorldManager;
import net.cherryflavor.api.spigot.world.generation.WorldType;
import net.cherryflavor.api.tools.TextFormat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerAPI {

    public static JavaPlugin plugin;
    private static ServerAPI api;
    private DatabaseManager databaseManager;
    private ServerCommandManager commandManager;
    private ServerListenerManager listenerManager;
    private WorldManager worldManager;
    private ServerMessageController serverMessageController;

    private static Configuration basicMessagesConfig;
    private static Configuration config;

    private static Map<UUID, PermissionAttachment> permissionsMap;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        api = this;

        CherryConfig.makeFolder("plugins/CherryAPI");
        CherryConfig.makeFolder("plugins/CherryAPI/worlds");

        CherryConfig.createResource("spigot/basic-messages.yml",new File("plugins/CherryAPI/basic-messages.yml"));
        CherryConfig.createResource("spigot/config.yml",new File("plugins/CherryAPI/config.yml"));
        CherryConfig.createResource("spigot/worldmanage.yml",new File("plugins/CherryAPI/worldmanage.yml"));

        basicMessagesConfig = new CherryConfig(new File("plugins/CherryAPI/basic-messages.yml")).getConfig();
        config = new CherryConfig(new File("plugins/CherryAPI/config.yml")).getConfig();

        databaseManager = new DatabaseManager(this);
        commandManager = new ServerCommandManager(this);
        listenerManager = new ServerListenerManager(this);
        worldManager = new WorldManager(this);
        serverMessageController = new ServerMessageController(this);

        permissionsMap = new HashMap<>();

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", new ServerMessageListener(this));

        getCommandManager().registerCommand(
                new WorldTeleportCommand(),
                new WorldManageCommand()
        );

        getListenerManager().registerListener(
                new JoinEvent()
        );

        worldManager.loadWorlds();
        worldManager.registerVanillaWorlds();

        updateConnectedPlayers();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public static ServerAPI getAPI() { return api; }
    public static JavaPlugin getPlugin() { return plugin; }

    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public ServerCommandManager getCommandManager() { return commandManager; }
    public ServerListenerManager getListenerManager() { return listenerManager; }
    public WorldManager getWorldManager() { return worldManager; }
    public ServerMessageController getServerMessageController() {
        return serverMessageController;
    }

    public Configuration getBasicMessages() { return basicMessagesConfig; }
    public Configuration getConfig() { return config; }

    public Map<UUID, PermissionAttachment> getPermissionsMap() { return permissionsMap; }

    /**
     * Get collect of online players
     * @return
     */
    public Collection<? extends Player> getOnlinePlayers() {
        return plugin.getServer().getOnlinePlayers();
    }

    /**
     * Method #1
     * Returns player with uuid
     * @param uuid
     * @return
     */
    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Method #2
     * Returns player with playerName
     * @param playerName
     * @return
     */
    public Player getPlayer(String playerName) {
        return Bukkit.getPlayer(playerName);
    }

    /**
     * Must check if player is online before using
     * Use isOnline(player/playerName) to check
     * @param name
     * @return
     */
    public OnlinePlayer getOnlinePlayer(String name) {
        return new OnlinePlayer(name);
    }

    /**
     * Gets server
     * @return
     */
    public Server getServer() { return plugin.getServer(); }

    //==================================================================================================================
    // BOOLEAN METHODS
    //==================================================================================================================

    /**
     * Method #1
     * Check if player is online
     * @param playerName
     * @return
     */
    public boolean isOnline(String playerName) {
        return (Bukkit.getPlayer(playerName) != null);
    }

    /**
     * Method #2
     * Check if player is online
     * @param player
     * @return
     */
    public boolean isOnline(Player player) {
        return (player != null);
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    private void updateConnectedPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User.serverPlayerUsernameMap.put(player.getUniqueId(), player.getName());
            User.serverPlayerUUIDMap.put(player, player.getUniqueId());
        }
    }

    /**
     * If debug is set to true in config.yml
     * Debug will send message
     * @param message
     */
    public void debug(String... message) {
        if (config.getBoolean("debug") == true) {
            for (String m : message) {
                plugin.getLogger().info(TextFormat.colorize("&9" + m));
            }
        }
    }

    /**
     * Register command
     * @param commands
     */
    public void registerCommand(ServerCherryCommand... commands) {
        for (ServerCherryCommand command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(command);
            plugin.getCommand(command.getCommand()).setAliases(TextFormat.convertArrayToList(command.getAliases()));
        }
    }


}

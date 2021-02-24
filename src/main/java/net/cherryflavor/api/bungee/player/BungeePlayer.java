package net.cherryflavor.api.bungee.player;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class BungeePlayer extends User {

    private ProxiedPlayer player;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * Constructor #1
     * @param uuid
     */
    public BungeePlayer(UUID uuid) {
        super(uuid);
        this.player = ProxyAPI.getAPI().getPlayer(uuid);
    }

    /**
     * Constructor #2
     * @param playerName
     */
    public BungeePlayer(String playerName) {
        super (ProxyAPI.getAPI().getPlugin().getProxy().getPlayer(playerName));
        this.player = ProxyAPI.getAPI().getPlugin().getProxy().getPlayer(playerName);
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Gets user attribute
     * @return
     */
    public User getUser() { return this; }

    /**
     * Checks if player has permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) { return player.hasPermission(permission); }

    /**
     * Gets current ServerInfo
     * @return
     */
    public ServerInfo getCurrentServerInfo() {
        return this.player.getServer().getInfo();
    }

    /**
     * Checks if player is not null
     * @return
     */
    public boolean exists() { return (player != null); }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Gives player permission
     * @param permission
     */
    public void addPermission(String permission) {
        player.getPermissions().add(permission);
    }

    /**
     * Removes permission from player
     * @param permission
     */
    public void removePermission(String permission) {
        player.getPermissions().remove(permission);
    }

    /**
     * Sends player message
     * @param message
     */
    public void sendMessage(String... message) {
        for (String m : message) {
            player.sendMessage(new TextComponent(m));
        }
    }

    /**
     * Send player message
     * @param components
     */
    public void sendMessage(TextComponent[] components) {
        player.sendMessage(components);
    }

    /**
     * Send player message
     * @param components
     */
    public void sendMessage(BaseComponent... components) {
        player.sendMessage(components);
    }

    /**
     * Send player colored message
     * @param message
     */
    public void sendColorMessage(String... message) {
        for (String m : message) {
            player.sendMessage(TextFormat.colorize(m));
        }
    }

    /**
     * Sends player to server
     * @param info
     */
    public void sendTo(ServerInfo info) {
        player.connect(info);
    }

}

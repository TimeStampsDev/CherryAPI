package net.cherryflavor.api.spigot.player;

import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.tools.TextFormat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class OnlinePlayer extends User {

    private Player player;
    private PermissionAttachment permissionAttachment;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * Constructor #1
     * @param uuid
     */
    public OnlinePlayer(UUID uuid) {
        super(uuid);
        this.player = Bukkit.getPlayer(uuid);

        estPermissionAttachment();

    }

    /**
     * Constructor #2
     * @param playerName
     */
    public OnlinePlayer(String playerName) {
        super(Bukkit.getPlayer(playerName));
        this.player = Bukkit.getPlayer(playerName);

        estPermissionAttachment();

    }

    /**
     * Constructor #3
     * @param player
     */
    public OnlinePlayer(Player player) {
        super(player);
        this.player = player;

        estPermissionAttachment();

    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Gets user attribute
     * @return
     */
    public User getUser() { return getUser(); }

    /**
     * Checks if player has permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
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
        estPermissionAttachment();
        permissionAttachment.setPermission(permission, true);
    }

    /**
     * Removes permission from player
     * @param permission
     */
    public void removePermission(String permission) {
        estPermissionAttachment();
        permissionAttachment.unsetPermission(permission);
    }

    /**
     * Sends player message
     * @param message
     */
    public void sendMessage(String... message) {
        for (String m : message) {
            player.sendMessage(m);
        }
    }

    /**
     * Send player message
     * @param components
     */
    public void sendMessage(BaseComponent... components) {
        player.spigot().sendMessage(components);
    }

    /**
     * Send player message
     * @param components
     */
    public void sendMessage(TextComponent... components) {
        player.spigot().sendMessage(components);
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
     * Establishes permission map for player
     */
    private void estPermissionAttachment() {
        if (permissionAttachment == null) {
            permissionAttachment = player.addAttachment(ServerAPI.getAPI().getPlugin());
            ServerAPI.getAPI().getPermissionsMap().put(player.getUniqueId(), permissionAttachment);
        } else {
            permissionAttachment = ServerAPI.getAPI().getPermissionsMap().get(player.getUniqueId());
        }
    }


}

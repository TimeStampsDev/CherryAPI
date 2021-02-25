package net.cherryflavor.api.database.users;

import net.cherryflavor.api.exceptions.MojangAPIException;
import net.cherryflavor.api.mojang.MojangAPI;
import net.cherryflavor.api.mojang.resources.TimeStampName;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class User {

    public static Map<ProxiedPlayer, UUID> bungeePlayerUUIDMap = new HashMap<>();
    public static Map<UUID, String> bungeePlayerUsernameMap = new HashMap<>();

    public static Map<Player, UUID> serverPlayerUUIDMap = new HashMap<>();
    public static Map<UUID, String> serverPlayerUsernameMap = new HashMap<>();

    private UUID uuid;
    private String username;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param uuid
     */
    public User(UUID uuid) {
        this.uuid = uuid;
        this.username = callUsername(this.uuid);
    }

    /**
     * @param bukkitPlayer
     */
    public User(Player bukkitPlayer) {
        this.uuid = bukkitPlayer.getUniqueId();
        this.username = bukkitPlayer.getName();
    }

    /**
     * @param proxiedPlayer
     */
    public User(ProxiedPlayer proxiedPlayer) {
        this.uuid = proxiedPlayer.getUniqueId();
        this.username = proxiedPlayer.getName();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * @return UUID
     */
    public UUID getUniqueId() { return uuid; }

    /**
     * @return Returns UUID as String
     */
    public String getStringId() { return uuid.toString(); }

    /**
     * @return username retrieved by @MojangAPI
     */
    public String getUsername() { return username; }

    /**
     * In order from oldest to newest.
     * @return name history retrieved by @MojangAPI
     */
    public TimeStampName[] getNameHistory() {
        return callNameHistory(this.uuid);
    }

    /**
     * Calls from api.mojang.com and gets player name history
     * @param uuid
     * @return
     */
    private TimeStampName[] callNameHistory(UUID uuid) {
        TimeStampName[] nameHistory = null;
        try {
            nameHistory = MojangAPI.getUsernameHistory(this.uuid);
        } catch (MojangAPIException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return nameHistory;
    }

    /**
     * Calls from api.mojang.com and gets username
     * @param uuid
     * @return
     */
    private String callUsername(UUID uuid) {
        String username = null;
        if (bungeePlayerUsernameMap.get(uuid) != null) {
            return bungeePlayerUsernameMap.get(uuid);
        } else if(serverPlayerUsernameMap.get(uuid) != null) {
            return serverPlayerUsernameMap.get(uuid);
        } else {
            try {
                username = MojangAPI.getCurrentName(uuid);
            } catch (MojangAPIException e) {
                e.printStackTrace();
            }
            return username;
        }
    }

}

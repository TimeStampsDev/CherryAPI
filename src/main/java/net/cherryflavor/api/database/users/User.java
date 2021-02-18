package net.cherryflavor.api.database.users;

import net.cherryflavor.api.exceptions.MojangAPIException;
import net.cherryflavor.api.mojang.MojangAPI;
import net.cherryflavor.api.mojang.resources.TimeStampName;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String username;

    public User(UUID uuid) {
        this.uuid = uuid;
        try {
            this.username = MojangAPI.getCurrentName(uuid);
        } catch (MojangAPIException e) {
            e.printStackTrace();
        }
    }

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
        try {
            return MojangAPI.getUsernameHistory(this.uuid);
        } catch (IOException | MojangAPIException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package net.cherryflavor.api.spigot.player;

import net.cherryflavor.api.database.users.User;

import java.util.UUID;

public class OfflinePlayer extends User {

    public OfflinePlayer(UUID uuid) {
        super(uuid);
    }

}

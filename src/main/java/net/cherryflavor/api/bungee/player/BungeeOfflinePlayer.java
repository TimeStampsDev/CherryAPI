package net.cherryflavor.api.bungee.player;

import net.cherryflavor.api.database.users.User;

import java.util.UUID;

public class BungeeOfflinePlayer extends User {

    public BungeeOfflinePlayer(UUID uuid) {
        super(uuid);
    }

}

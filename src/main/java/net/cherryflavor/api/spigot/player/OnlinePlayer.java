package net.cherryflavor.api.spigot.player;

import net.cherryflavor.api.database.users.User;

import java.util.UUID;

public class OnlinePlayer extends User {

    public OnlinePlayer(UUID uuid) {
        super(uuid);
    }

}

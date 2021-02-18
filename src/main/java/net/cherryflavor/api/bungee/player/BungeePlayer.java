package net.cherryflavor.api.bungee.player;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayer extends User {

    private ProxiedPlayer player;

    public BungeePlayer(UUID uuid) {
        super(uuid);
        this.player = ProxyAPI.getAPI().getOnlinePlayer(uuid);
    }
    public BungeePlayer(ProxiedPlayer player) {
        super (player.getUniqueId());
        this.player = player;
    }

    public void addPermission(String permission) {
        player.getPermissions().add(permission);
    }

    public void removePermission(String permission) {
        player.getPermissions().remove(permission);
    }

    public boolean hasPermission(String permission) { return player.hasPermission(permission); }

    public void sendMessage(String... message) {
        for (String m : message) {
            player.sendMessage(new TextComponent(m));
        }
    }

    public void sendColorMessage(String... message) {
        for (String m : message) {
            player.sendMessage(TextFormat.colorize(m));
        }
    }

    public void sendTextComponents(TextComponent[] textComponents) {
        player.sendMessage(textComponents);
    }

    public void sendBaseComponents(BaseComponent... baseComponents) {
        player.sendMessage(baseComponents);
    }



}

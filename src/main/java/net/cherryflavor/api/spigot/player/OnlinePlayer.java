package net.cherryflavor.api.spigot.player;

import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class OnlinePlayer extends User {

    private Player player;
    private PermissionAttachment permissionAttachment;

    public OnlinePlayer(UUID uuid) {
        super(uuid);
        this.player = ServerAPI.getAPI().getOnlinePlayer(uuid);

        estPermissionAttachment();

    }

    public OnlinePlayer(Player player) {
        super(player.getUniqueId());
        this.player = player;

        estPermissionAttachment();

    }

    public void addPermission(String permission) {
        estPermissionAttachment();
        permissionAttachment.setPermission(permission, true);
    }

    public void removePermission(String permission) {
        estPermissionAttachment();
        permissionAttachment.unsetPermission(permission);
    }

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public void sendMessage(String... message) {
        for (String m : message) {
            player.sendMessage(m);
        }
    }

    public void sendColorMessage(String... message) {
        for (String m : message) {
            player.sendMessage(TextFormat.colorize(m));
        }
    }

    private void estPermissionAttachment() {
        if (permissionAttachment == null) {
            permissionAttachment = player.addAttachment(ServerAPI.getAPI().getPlugin());
            ServerAPI.getAPI().getPermissionsMap().put(player.getUniqueId(), permissionAttachment);
        } else {
            permissionAttachment = ServerAPI.getAPI().getPermissionsMap().get(player.getUniqueId());
        }
    }

}

package net.cherryflavor.api.spigot.plugin.events;

import net.cherryflavor.api.database.users.User;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.SpigotCherry;
import net.cherryflavor.api.spigot.event.ServerCherryListener;

import org.bukkit.Server.Spigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created on 3/2/2021
 * Time 12:44 AM
 */

public class JoinEvent extends ServerCherryListener {

    public JoinEvent() {
        super("API_Join", false);
    }

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    @EventHandler
    public void event(PlayerJoinEvent event) {
        if (isCancelled() == false) {
            Player player = event.getPlayer();

            User.serverPlayerUsernameMap.put(player.getUniqueId(), player.getName());
            User.serverPlayerUUIDMap.put(player, player.getUniqueId());

            SpigotCherry.session.getLobby().addPlayer(player);

            if (!SpigotCherry.session.inSession()) {
                SpigotCherry.session.begin();
            }

            SpigotCherry.session.openBallot(player);
        }
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

package net.cherryflavor.api.bungee.plugin.listeners;

import net.cherryflavor.api.bungee.event.BungeeCherryListener;
import net.cherryflavor.api.database.users.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;

/**
 * Created on 2/24/2021
 * Time 10:26 PM
 */

public class JoinEvent extends BungeeCherryListener {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public JoinEvent() {
        super("API_Join", false);
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @EventHandler
    public void event(PostLoginEvent event) {
        if (isCancelled() == false) {
            ProxiedPlayer proxiedPlayer = event.getPlayer();

            User.bungeePlayerUUIDMap.put(proxiedPlayer, proxiedPlayer.getUniqueId());
            User.bungeePlayerUsernameMap.put(proxiedPlayer.getUniqueId(), proxiedPlayer.getDisplayName());
        }
    }


}

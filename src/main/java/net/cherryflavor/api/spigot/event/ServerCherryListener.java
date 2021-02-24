package net.cherryflavor.api.spigot.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public abstract class ServerCherryListener implements Listener {

    private String listenerName;
    private boolean isCancelled;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerCherryListener(String listenerName, boolean isCancelled) {
        this.listenerName = listenerName;
        this.isCancelled = isCancelled;
    }


    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public String getListenerName() {
        return listenerName;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    @EventHandler
    public abstract void event();

}

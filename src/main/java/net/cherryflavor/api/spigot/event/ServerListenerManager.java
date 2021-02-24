package net.cherryflavor.api.spigot.event;

import net.cherryflavor.api.spigot.ServerAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerListenerManager {

    private List<ServerCherryListener> listenerList;

    private ServerAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerListenerManager(ServerAPI api) {
        this.api = api;
        listenerList = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public List<ServerCherryListener> getListenerList() {
        return listenerList;
    }

    public List<ServerCherryListener> getEnabledListeners() {
        List<ServerCherryListener> listeners = new ArrayList<>();
        for (ServerCherryListener listener : listenerList) {
            if (listener.isCancelled() == false) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    public List<ServerCherryListener> getCancelledListeners() {
        List<ServerCherryListener> listeners = new ArrayList<>();
        for (ServerCherryListener listener : listenerList) {
            if (listener.isCancelled() == true) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    public void registerListener(ServerCherryListener... listener) {
        for (ServerCherryListener l : listener) {
            api.getPlugin().getServer().getPluginManager().registerEvents(l, api.getPlugin());
            api.debug("[ListenerManager] " + l.getClass().getName() + " has been registered");
        }
    }

}

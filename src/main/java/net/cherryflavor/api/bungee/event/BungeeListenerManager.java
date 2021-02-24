package net.cherryflavor.api.bungee.event;

import net.cherryflavor.api.bungee.ProxyAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class BungeeListenerManager {

    private List<BungeeCherryListener> listenerList;

    private ProxyAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public BungeeListenerManager(ProxyAPI api) {
        this.api = api;
        listenerList = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public List<BungeeCherryListener> getListenerList() {
        return listenerList;
    }

    public List<BungeeCherryListener> getEnabledListeners() {
        List<BungeeCherryListener> listeners = new ArrayList<>();
        for (BungeeCherryListener listener : listenerList) {
            if (listener.isCancelled() == false) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    public List<BungeeCherryListener> getCancelledListeners() {
        List<BungeeCherryListener> listeners = new ArrayList<>();
        for (BungeeCherryListener listener : listenerList) {
            if (listener.isCancelled() == true) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    public void registerListener(BungeeCherryListener... listener) {
        for (BungeeCherryListener l : listener) {
            api.getPlugin().getProxy().getPluginManager().registerListener(api.getPlugin(), l);
            api.debug("[ListenerManager] " + l.getClass().getName() + " has been registered");
        }
    }

    public void unregisterListener(BungeeCherryListener... listener) {
        for (BungeeCherryListener l : listener) {
            api.getPlugin().getProxy().getPluginManager().unregisterListener(l);
            api.debug("[ListenerManager] " + l.getClass().getName() + " has been unregistered");
        }
    }

}

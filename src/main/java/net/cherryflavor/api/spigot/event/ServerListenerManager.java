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

    private List<String> listenerNameList;

    private ServerAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerListenerManager(ServerAPI api) {
        this.api = api;
        listenerList = new ArrayList<>();
        listenerNameList = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================


    /**
     *
     * @return
     */
    public ServerAPI getApi() {
        return api;
    }

    /**
     *
     * @return
     */
    public List<ServerCherryListener> getListenerList() {
        return listenerList;
    }

    /**
     *
     * @return
     */
    public List<ServerCherryListener> getEnabledListeners() {
        List<ServerCherryListener> listeners = new ArrayList<>();
        for (ServerCherryListener listener : listenerList) {
            if (listener.isCancelled() == false) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    /**
     *
     * @return
     */
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
            if (!listenerNameList.contains(l.getListenerName())) {
                api.getPlugin().getServer().getPluginManager().registerEvents(l, api.getPlugin());
                api.debug("[ListenerManager] " + l.getListenerName() + " has been registered");
            }
        }
    }

}

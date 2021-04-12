package net.cherryflavor.api.spigot.event;

import net.cherryflavor.api.spigot.ServerAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerListenerManager {

    private String debugPrefix = "[ListenerManager]";

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
    public String getDebugPrefix() {
        return debugPrefix;
    }

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

    public void debug(String message) {
        api.debug(getDebugPrefix() + " " + message);
    }

    public void registerListener(ServerCherryListener... listener) {
        for (ServerCherryListener l : listener) {
            if (!listenerNameList.contains(l.getListenerName())) {
                listenerNameList.add(l.getListenerName());
                ServerAPI.getPlugin().getServer().getPluginManager().registerEvents(l, ServerAPI.getPlugin());
                debug(getDebugPrefix() + " " + l.getListenerName() + " has been registered");
            }
        }
    }

    public void silentRegisterListener(ServerCherryListener... listener) {
        for (ServerCherryListener l : listener) {
            if (!listenerNameList.contains(l.getListenerName())) {
                listenerNameList.add(l.getListenerName());
                ServerAPI.getPlugin().getServer().getPluginManager().registerEvents(l, ServerAPI.getPlugin());
            }
        }
    }

}

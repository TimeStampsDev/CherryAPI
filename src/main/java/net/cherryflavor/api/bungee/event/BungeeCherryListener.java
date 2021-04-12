package net.cherryflavor.api.bungee.event;

import net.md_5.bungee.api.plugin.Listener;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public abstract class BungeeCherryListener implements Listener {

    private String listenerName;
    private boolean isCancelled;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     *
     * @param listenerName
     * @param isCancelled
     */
    public BungeeCherryListener(String listenerName, boolean isCancelled) {
        this.listenerName = listenerName;
        this.isCancelled = isCancelled;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     *
     * @return
     */
    public String getListenerName() {
        return listenerName;
    }

    /**
     *
     * @return
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    /**
     *
     * @param cancelled
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


}

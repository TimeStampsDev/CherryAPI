package net.cherryflavor.api.spigot.comms;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerCommsEvent extends Event {

    private String subchannel;
    private byte[] message;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerCommsEvent(String subchannel, byte[] message) {
        this.subchannel = subchannel;
        this.message = message;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public String getSubchannel() { return subchannel; }
    public byte[] getMessage() { return message; }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

}

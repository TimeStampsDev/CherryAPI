package net.cherryflavor.api.spigot.comms;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerCommsEvent extends Event {

    private ChannelCommType commType;
    private String subchannel;
    private byte[] message;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerCommsEvent(ChannelCommType commType, String subchannel, byte[] message) {
        this.commType = commType;
        this.subchannel = subchannel;
        this.message = message;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public ChannelCommType gCommType() { return this.commType; }


    public String getSubchannel() { return subchannel; }
    public byte[] getMessage() { return message; }

    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

}

package net.cherryflavor.api.spigot.comms;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerCommsEvent extends Event {

    private String subchannel;
    private byte[] message;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public ServerCommsEvent(String subchannel, byte[] message) {
        this.subchannel = subchannel;
        this.message = message;
    }

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

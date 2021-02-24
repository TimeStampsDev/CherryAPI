package net.cherryflavor.api.spigot.comms;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.cherryflavor.api.exceptions.InvalidCommsChannelException;
import net.cherryflavor.api.spigot.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerMessageListener implements PluginMessageListener {

    public ServerAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerMessageListener(ServerAPI api) {
        this.api = api;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            try {
                throw new InvalidCommsChannelException(channel + " is invalid comms channel");
            } catch (InvalidCommsChannelException e) {
                e.printStackTrace();
            }
            return;
        }

        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();

        ServerCommsEvent serverCommsEvent = new ServerCommsEvent(subchannel, message);
        Bukkit.getPluginManager().callEvent(serverCommsEvent);
    }

}

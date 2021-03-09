package net.cherryflavor.api.spigot.comms;

import net.cherryflavor.api.spigot.ServerAPI;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created on 3/12/2021
 * Time 8:03 PM
 */

public class ServerMessageController {

    private String debugPrefix = "[ServerMessageController]";
    private ServerAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerMessageController(ServerAPI api) {
        this.api = api;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public ServerAPI getAPI() {
        return api;
    }

    public String getDebugPrefix() {
        return debugPrefix;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Sends message to channel 'BungeeCord'
     * @param message
     */
    public void sendMessage(String message, String channel) {
        if (message != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            try {
                out.writeUTF(message);

                getAPI().getServer().sendPluginMessage(ServerAPI.getPlugin(), channel, stream.toByteArray());

                getAPI().debug(getDebugPrefix() + " To : " + channel + " Sent : " + message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("Message cannot be null");
        }
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

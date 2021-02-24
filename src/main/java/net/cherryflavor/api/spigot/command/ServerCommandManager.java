package net.cherryflavor.api.spigot.command;

import net.cherryflavor.api.spigot.ServerAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerCommandManager {

    private List<ServerCherryCommand> commandList;

    private ServerAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerCommandManager(ServerAPI api) {
        this.api = api;
        commandList = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public List<ServerCherryCommand> getCommandList() {
        return commandList;
    }

    public List<ServerCherryCommand> getEnabledCommands() {
        List<ServerCherryCommand> commands = new ArrayList<>();
        for (ServerCherryCommand command : commandList) {
            if (command.isCancelled() == false) {
                commands.add(command);
            }
        }
        return commands;
    }

    public List<ServerCherryCommand> getCancelledCommands() {
        List<ServerCherryCommand> commands = new ArrayList<>();
        for (ServerCherryCommand command : commandList) {
            if (command.isCancelled() == true) {
                commands.add(command);
            }
        }
        return commands;
    }

    public void registerCommand(ServerCherryCommand... command) {
        api.registerCommand(command);
        for (ServerCherryCommand c : command)  {
            commandList.add(c);
            api.debug("[CommandManager] " + c.getClass().getName() + " has been registered");
        }
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    public void setCancelled(boolean cancelled, ServerCherryCommand... command) {
        for (ServerCherryCommand c : command) {
            c.setCancelled(cancelled);
        }
    }

}

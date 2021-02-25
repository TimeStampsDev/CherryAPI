package net.cherryflavor.api.bungee.command;

import net.cherryflavor.api.bungee.ProxyAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class BungeeCommandManager {

    private List<BungeeCherryCommand> commandList;
    private List<String> registered;

    private ProxyAPI api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     *
     * @param api
     */
    public BungeeCommandManager(ProxyAPI api) {
        this.api = api;
        commandList = new ArrayList<>();
        registered = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     *
     * @return
     */
    public List<BungeeCherryCommand> getCommandList() {
        return this.commandList;
    }

    /**
     *
     * @return
     */
    public List<String> getRegistered() { return this.registered; }

    /**
     * Get commands that cancelled is set to false
     * @return
     */
    public List<BungeeCherryCommand> getEnabledCommands() {
        List<BungeeCherryCommand> commands = new ArrayList<>();
        for (BungeeCherryCommand command : commandList) {
            if (command.isCancelled() == false) {
                commands.add(command);
            }
        }
        return commands;
    }

    /**
     * Get commmands that cancelled is set to true
     * @return
     */
    public List<BungeeCherryCommand> getCancelledCommands() {
        List<BungeeCherryCommand> commands = new ArrayList<>();
        for (BungeeCherryCommand command : commandList) {
            if (command.isCancelled() == true) {
                commands.add(command);
            }
        }
        return commands;
    }

    /**
     * Returns command
     * @param command
     * @return
     */
    public BungeeCherryCommand getCommand(String command) {
        for (BungeeCherryCommand cmd : getCommandList()) {
            if (cmd.getCommand().equalsIgnoreCase(command)) {
                return cmd;
            }
        }
        return null;
    }

    /**
     * Returns boolean is command exists
     * @param command
     * @return
     */
    public boolean commandExists(String command) {
        for (String cmd : registered) {
            if (cmd.equalsIgnoreCase(command)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    /**
     * Sets cancelled
     * @param cancelled
     * @param command
     */
    public void setCancelled(boolean cancelled, BungeeCherryCommand... command) {
        for (BungeeCherryCommand c : command) {
            c.setCancelled(cancelled);
        }
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Registers command
     * @param command
     */
    public void registerCommand(BungeeCherryCommand... command) {
        for (BungeeCherryCommand c : command)  {
            commandList.add(c);
            registered.add(c.getCommand());
            api.registerCommand(c);
            api.debug("[CommandManager] " + c.getClass().getSimpleName() + " has been registered");
        }
    }

    /**
     * Unregisters command
     * @param command
     */
    public void unregisterCommand(BungeeCherryCommand... command) {
        for (BungeeCherryCommand c : command) {
            commandList.remove(c);
            registered.remove(c);
            api.unregisterCommand(c);
            api.debug("[CommandManager] " + c.getClass().getSimpleName() + " has been unregistered");
        }
    }

}

package net.cherryflavor.api.spigot.command;

import net.cherryflavor.api.other.TabCommand;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public abstract class ServerCherryCommand implements CommandExecutor, TabCompleter {

    private String unknownCommand = "Unknown command. Type \"/help\" for help.";
    private String noPermission = ServerAPI.getAPI().getBasicMessages().getString("no-permission");

    private String command;
    private String permission;
    private String[] aliases;

    private boolean isCancelled;

    private List<TabCommand> tabCommandList;
    private List<TabCommand> consoleTabCommandList;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param isCancelled
     * @param command
     */
    public ServerCherryCommand(boolean isCancelled, String command) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
        consoleTabCommandList = new ArrayList<>();
    }

    /**
     * @param isCancelled
     * @param command
     * @param permission
     */
    public ServerCherryCommand(boolean isCancelled, String command, String permission) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
        consoleTabCommandList = new ArrayList<>();
    }

    /**
     * @param isCancelled
     * @param command
     * @param permission
     * @param aliases
     */
    public ServerCherryCommand(boolean isCancelled, String command, String permission, String... aliases) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
        consoleTabCommandList = new ArrayList<>();
    }

    /**
     * @param isCancelled
     * @param command
     * @param aliases
     */
    public ServerCherryCommand(boolean isCancelled, String command, String... aliases) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
        consoleTabCommandList = new ArrayList<>();
    }

    //==================================================================================================================
    // BOOLEAN METHODS
    //==================================================================================================================

    /**
     * Only executes if players perform the command
     * @param player
     * @param command
     * @param label
     * @param args
     * @return
     */
    public abstract boolean playerExecute(OnlinePlayer player, Command command, String label, String[] args);

    /**
     * Only executes if console perform the command
     * @param console
     * @param command
     * @param label
     * @param args
     * @return
     */
    public abstract boolean consoleExecute(CommandSender console, Command command, String label, String[] args);

    /**
     * @param commandSender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (isCancelled == true) {
            commandSender.sendMessage(unknownCommand);
        } else  {
            if (commandSender instanceof Player) {
                OnlinePlayer player = new OnlinePlayer(((Player) commandSender).getUniqueId());
                if (this.permission.isEmpty()) {
                    return playerExecute(player, command, label, args);
                } else {
                    if (player.hasPermission(this.permission)) {
                        return playerExecute(player, command, label, args);
                    } else {
                        player.sendColorMessage(noPermission);
                    }
                }
            } else {
                return consoleExecute(commandSender, command, label, args);
            }
        }
        return true;
    }

    /**
     * Method for when tab is pressed when performing commands
     * @param commandSender
     * @param command
     * @param label
     * @param args
     * @return
     */
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        for (TabCommand tabCommand : tabCommandList) {
            if (args.length == tabCommand.getArgument()) {
                return tabCommand.getTabList();
            }
        }
        return Collections.emptyList();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Get command
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets permission for commmand
     * @return
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Gets command aliases
     * @return
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Get tabcommands list for players
     * @return
     */
    public List<TabCommand> getTabCommandList() {
        return tabCommandList;
    }

    /**
     * Get tabcommands list for console
     * @return
     */
    public List<TabCommand> getConsoleTabCommandList() {
        return consoleTabCommandList;
    }

    /**
     *
     * @return
     */
    public boolean isCancelled() { return isCancelled; }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Adds list to return for argument
     * @param argument
     * @param tabList
     */
    public void addTab(int argument, List<String> tabList) {
        TabCommand tabCommand = new TabCommand(argument, tabList);
        tabCommandList.add(tabCommand);
    }

    /**
     * Removes list to return for argument
     * @param argument
     * @param tabList
     */
    public void removeTab(int argument, List<String> tabList) {
        TabCommand tabCommand = new TabCommand(argument, tabList);
        tabCommandList.remove(tabCommand);
    }

    /**
     * Adds list to return for argument
     * @param argument
     * @param tabList
     */
    public void addConsoleTab(int argument, List<String> tabList) {
        TabCommand tabCommand = new TabCommand(argument, tabList);
        consoleTabCommandList.add(tabCommand);
    }

    /**
     * Removes list to return for argument
     * @param argument
     * @param tabList
     */
    public void removeConsoleTab(int argument, List<String> tabList) {
        TabCommand tabCommand = new TabCommand(argument, tabList);
        consoleTabCommandList.remove(tabCommand);
    }

}

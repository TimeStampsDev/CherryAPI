package net.cherryflavor.api.bungee.command;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.other.TabCommand;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public abstract class BungeeCherryCommand extends Command implements TabExecutor {

    public String unknownCommand = "Unknown command. Type \"/help\" for help.";
    public String noPermission = ProxyAPI.getAPI().getBasicMessages().getString("no-permission");

    private ProxyAPI proxyAPI = ProxyAPI.getAPI();

    private String command;
    private String permission;
    private String[] aliases;

    private boolean isCancelled;

    private CommandSender sender;

    private List<TabCommand> tabCommandList;
    private List<TabCommand> consoleTabCommandList;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param isCancelled
     * @param command
     */
    public BungeeCherryCommand(boolean isCancelled, String command) {
        super(command);
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
    public BungeeCherryCommand(boolean isCancelled, String command, String permission) {
        super(command, permission, "");
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
    public BungeeCherryCommand(boolean isCancelled, String command, String permission, String... aliases) {
        super(command,permission,aliases);
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
    public BungeeCherryCommand(boolean isCancelled, String command, String... aliases) {
        super(command,"",aliases);
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
     * @param args
     */
    public abstract void playerExecute(BungeePlayer player, String[] args);

    /**
     * Only executes if console perform the command
     * @param console
     * @param args
     * @return
     */
    public abstract void consoleExecute(CommandSender console, String[] args);

    /**
     * @param commandSender
     * @param args
     */
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        this.sender = commandSender;
        if (isCancelled == true) {
            commandSender.sendMessage(unknownCommand);
        } else {
            if (commandSender instanceof ProxiedPlayer) {
                BungeePlayer bungeePlayer = new BungeePlayer(((ProxiedPlayer) commandSender).getUniqueId());
                if (this.permission.isEmpty()) {
                    playerExecute(bungeePlayer, args);
                } else {
                    if (bungeePlayer.hasPermission(this.permission)) {
                        playerExecute(bungeePlayer, args);
                    } else {
                        bungeePlayer.sendColorMessage(noPermission);
                    }
                }
            } else {
                consoleExecute(commandSender, args);
            }
        }
    }

    /**
     * Method for when tab is pressed when performing commands
     * @param sender
     * @param args
     * @return
     */
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            for (TabCommand tabCommand : tabCommandList) {
                if (args.length == tabCommand.getArgument()) {
                    return tabCommand.getTabList();
                }
            }
        } else {
            for (TabCommand tabCommand : consoleTabCommandList) {
                if (args.length == tabCommand.getArgument()) {
                    return tabCommand.getTabList();
                }
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
    @Override
    public String getPermission() {
        return permission;
    }

    /**
     * Gets command aliases
     * @return
     */
    @Override
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
     * Checks if command is cancelled
     * @return
     */
    public boolean isCancelled() { return isCancelled; }

    /**
     * Returns already-initialized ProxyAPI
     * @return
     */
    public ProxyAPI getAPI() { return proxyAPI; }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    /**
     * @param cancelled
     */
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

    /**
     * Sends commandSender a message
     * @param message
     */
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    /**
     * Sends commandSender a colorful message
     * @param message
     */
    public void sendColorfulMessage(String message) {
        sender.sendMessage(TextFormat.colorize(message));
    }

}

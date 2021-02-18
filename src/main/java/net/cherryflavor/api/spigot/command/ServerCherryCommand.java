package net.cherryflavor.api.spigot.command;

import net.cherryflavor.api.other.TabCommand;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ServerCherryCommand implements CommandExecutor, TabCompleter {

    private String unknownCommand = "Unknown command. Type \"/help\" for help.";
    private String noPermission = ServerAPI.getAPI().getBasicMessages().getString("no-permission");

    private String command;
    private String permission;
    private String[] aliases;

    private boolean isCancelled;

    private List<TabCommand> tabCommandList;

    public ServerCherryCommand(boolean isCancelled, String command) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
    }

    public ServerCherryCommand(boolean isCancelled, String command, String permission) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
    }

    public ServerCherryCommand(boolean isCancelled, String command, String permission, String... aliases) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
    }

    public ServerCherryCommand(boolean isCancelled, String command, String... aliases) {
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
    }

    public abstract boolean playerExecute(OnlinePlayer player, Command command, String label, String[] args);
    public abstract boolean consoleExecute(CommandSender console, Command command, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (isCancelled == true) {
            commandSender.sendMessage(unknownCommand);
        } else  {
            if (commandSender instanceof Player) {
                OnlinePlayer player = new OnlinePlayer(((Player) commandSender).getUniqueId());
                if (player.hasPermission(this.permission)) {
                    return playerExecute(player, command, label, args);
                } else {
                    player.sendColorMessage(noPermission);
                }
            } else {
                return consoleExecute(commandSender, command, label, args);
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        for (TabCommand tabCommand : tabCommandList) {
            if (args.length == tabCommand.getArgument()) {
                return tabCommand.getTabList();
            }
        }
        return Collections.emptyList();
    }

    public String getCommand() {
        return command;
    }
    public String getPermission() {
        return permission;
    }
    public String[] getAliases() {
        return aliases;
    }
    public List<TabCommand> getTabCommandList() {
        return tabCommandList;
    }
    public boolean isCancelled() { return isCancelled; }

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

}

package net.cherryflavor.api.bungee.command;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.other.TabCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BungeeCherryCommand extends Command implements TabExecutor {

    private String unknownCommand = "Unknown command. Type \"/help\" for help.";
    private String noPermission = ProxyAPI.getAPI().getBasicMessages().getString("no-permission");

    private String command;
    private String permission;
    private String[] aliases;

    private boolean isCancelled;

    private List<TabCommand> tabCommandList;

    public BungeeCherryCommand(boolean isCancelled, String command) {
        super(command);
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
    }

    public BungeeCherryCommand(boolean isCancelled, String command, String permission) {
        super(command, permission, "");
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = new String[] {""};
        tabCommandList = new ArrayList<>();
    }

    public BungeeCherryCommand(boolean isCancelled, String command, String permission, String... aliases) {
        super(command,permission,aliases);
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = permission;
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
    }

    public BungeeCherryCommand(boolean isCancelled, String command, String... aliases) {
        super(command,"",aliases);
        this.isCancelled = isCancelled;
        this.command = command;
        this.permission = "";
        this.aliases = aliases;
        tabCommandList = new ArrayList<>();
    }

    public abstract void playerExecute(BungeePlayer player, String[] args);
    public abstract void consoleExecute(CommandSender console, String[] args);

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (isCancelled == true) {
            commandSender.sendMessage(unknownCommand);
        } else {
            if (commandSender instanceof ProxiedPlayer) {
                BungeePlayer bungeePlayer = new BungeePlayer((ProxiedPlayer) commandSender);
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

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
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
    @Override
    public String getPermission() {
        return permission;
    }
    @Override
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

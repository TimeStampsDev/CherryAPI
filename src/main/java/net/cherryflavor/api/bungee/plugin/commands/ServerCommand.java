package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.player.BungeePlayer;

import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ServerCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public ServerCommand() {
        super(false, "server", new String[]{"goto"});
        addTab(1, getAPI().getStringListServers());

        addConsoleTab(1, getAPI().getStringListOfOnlinePlayers());
        addConsoleTab(2, getAPI().getStringListServers());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public List<String> getColorCodedServerList() {
        List<String> colorCodedServerList = new ArrayList<>();
        for (ServerInfo info : getAPI().getPlugin().getProxy().getServers().values()) {
            if (getAPI().isServerOnline(info)) {
                colorCodedServerList.add(TextFormat.colorize("&e" + info.getName() + "&r"));
            } else {
                colorCodedServerList.add(TextFormat.colorize("&c" + info.getName() + "&r"));
            }
        }
        return colorCodedServerList;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        int serverSize = getAPI().getStringListServers().size();
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /server <server>");
            player.sendColorfulMessage(
                    "&cThere are currently : &f" + serverSize + " server" + TextFormat.pluralization(serverSize) + " &e⬛-Online &c⬛-Offline",
                    "&f" + TextFormat.stripOutliers(getColorCodedServerList().toString())
            );
        } else if (args.length == 1) {
            if (getAPI().getStringListServers().contains(args[0].toLowerCase())) {
                ServerInfo info = getAPI().getPlugin().getProxy().getServerInfo(args[0].toLowerCase());
                if (getAPI().isServerOnline(info)) {
                    if (player.hasPermission(getAPI().getServerPermissionsMap().get(info))) {
                        player.sendColorfulMessage(noPermission);
                    } else {
                        if (player.getCurrentServerInfo().getName().equalsIgnoreCase(info.getName())) {
                            player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("already-connected"), info.getName()));
                        } else {
                            player.sendTo(info);
                            player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("sent-to-server"), info.getName()));
                        }
                    }
                } else {
                    player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("server-not-online"), info.getName()));
                }
            } else {
                player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("server-not-exists"),args[0].toLowerCase()));
            }
        } else {
            player.sendColorfulMessage("&cUsage: /server <server>");
            player.sendColorfulMessage(
                    "&cThere are currently : &f" + serverSize + " server" + TextFormat.pluralization(serverSize) + " &e⬛-Online &c⬛-Offline",
                    "&f" + TextFormat.stripOutliers(getColorCodedServerList().toString())
            );
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /server <user> <server>");
        } else if (args.length == 1) {
            sendColorfulMessage("&cUsage: /server <user> <server>");
        } else if (args.length == 2) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                if (getAPI().getStringListServers().contains(args[1].toLowerCase())) {
                    ServerInfo info = getAPI().getPlugin().getProxy().getServerInfo(args[1].toLowerCase());

                    if (getAPI().isServerOnline(info)) {
                        if (target.getCurrentServerInfo().equals(info)) {
                            sendColorfulMessage("&cTarget is already connnected to server provided");
                        } else {
                            target.sendTo(info);
                            sendColorfulMessage("&eSuccessfully sent &e" + target.getUsername() + " &eto &f" + info.getName() + "&e.");
                        }
                    } else {
                        sendColorfulMessage("&CServer provided is not online");
                    }
                } else {
                    sendColorfulMessage("&cServer provided doesn't exist");
                }
            } else {
                sendColorfulMessage("&cPlayer provided is not online");
            }
        } else {
            sendColorfulMessage("&cUsage: /server <user> <toServer>");
        }
    }

}

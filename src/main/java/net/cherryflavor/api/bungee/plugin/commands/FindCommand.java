package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * Created on 2/24/21
 * Time 6:18 PM
 */
public class FindCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public FindCommand() {
        super(false,"find","locate");

        addTabToBoth(1, getAPI().getStringListOfOnlinePlayers());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public ServerInfo find(BungeePlayer bungeePlayer) {
        return bungeePlayer.getCurrentServerInfo();
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /find <player>");
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                ServerInfo info = target.getCurrentServerInfo();
                player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("player-found-message"), target.getUsername(), info.getName()));
            } else {
                sendColorfulMessage(getAPI().getBasicMessages().getString("player-not-online"));
            }
        } else {
            player.sendColorfulMessage("&cUsage: /find <player>");
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /find <player>");
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                ServerInfo info = target.getCurrentServerInfo();
                sendColorfulMessage("&e" + target.getUsername() + " &7is located on &e" + info.getName() + "&7.");
            } else {
                sendColorfulMessage("&cPlayer provided is not online");
            }
        } else {
            sendColorfulMessage("&cUsage: /find <player>");
        }
    }
}

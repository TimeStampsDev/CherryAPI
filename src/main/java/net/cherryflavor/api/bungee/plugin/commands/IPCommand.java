package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;

/**
 * Created on 2/24/21
 * Time 7:03 PM
 */
public class IPCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public IPCommand() {
        super(false, "ip", new String[] {"getip","findip"});

        addTabToBoth(1, getAPI().getStringListOfOnlinePlayers());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /ip <player>");
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("ip-is-message"),target.getUsername(), target.getIPAddress().getHostString()));
            } else {
                player.sendColorfulMessage(getAPI().getBasicMessages().getString("player-not-online"));
            }
        } else {
            player.sendColorfulMessage("&cUsage: /ip <player>");
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /ip <player>");
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                sendColorfulMessage("&e" + target.getUsername() + "'s &7ip address is &e" + target.getIPAddress().getHostString());
            } else {
                sendColorfulMessage("&cPlayer provided is not online");
            }
        } else {
            sendColorfulMessage("&cUsage: /ip <player>");
        }
    }

}

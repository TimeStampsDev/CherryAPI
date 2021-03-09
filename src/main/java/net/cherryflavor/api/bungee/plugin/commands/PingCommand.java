package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;

/**
 * Created on 3/8/2021
 * Time 9:37 PM
 */

public class PingCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public PingCommand() {
        super(false, "ping");

        addTabToBoth(1, "", getAPI().getStringListOfOnlinePlayers());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("ping.personal"), player.getPing().toString() + " ms"));
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("ping.other"),target.getUsername(), target.getPing().toString() + " ms"));
            } else {
                sendColorfulMessage(getAPI().getBasicMessages().getString("player-not-online"));
            }
        } else {
            sendColorfulMessage("&cUsage: /ping <user>");
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /ping <user>");
        } else if (args.length == 1) {
            if (getAPI().isOnline(args[0])) {
                BungeePlayer target = new BungeePlayer(args[0]);
                sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("ping.other"),target.getUsername(), target.getPing().toString() + " ms"));
            } else {
                sendColorfulMessage(getAPI().getBasicMessages().getString("player-not-online"));
            }
        } else {
            sendColorfulMessage("&cUsage: /ping <user>");
        }
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

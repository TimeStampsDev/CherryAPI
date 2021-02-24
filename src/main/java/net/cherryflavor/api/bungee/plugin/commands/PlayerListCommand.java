package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2/24/21
 * Time 5:56 PM
 */
public class PlayerListCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public PlayerListCommand() {
        super(false, "playerlist","plist");
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public Map<ServerInfo, List<String>> getServerPlayerListMap() {
        Map<ServerInfo, List<String>> serverPlayerListMap = new HashMap<>();
        for (ServerInfo serverInfo : getAPI().getPlugin().getProxy().getServers().values()) {
            List<String> playerList = new ArrayList<>();

            for (ProxiedPlayer player : serverInfo.getPlayers()) {
                playerList.add(player.getName());
            }

            serverPlayerListMap.put(serverInfo, playerList);
        }
        return serverPlayerListMap;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        if (args.length == 0) {
            Map<ServerInfo, List<String>> serverPlayerListMap = getServerPlayerListMap();
            player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("playerlist-count"), getAPI().getOnlinePlayers().size() + " player" + TextFormat.pluralization(getAPI().getOnlinePlayers().size())));
            for (ServerInfo serverInfo : serverPlayerListMap.keySet()) {
                List<String> playerList = serverPlayerListMap.get(serverInfo);
                player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("playerlist-preview"),serverInfo.getName(), TextFormat.stripOutliers(playerList.toString())));
            }
        } else {
            player.sendColorfulMessage("&cUsage: /playerlist");
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {
        if (args.length == 0) {
            Map<ServerInfo, List<String>> serverPlayerListMap = getServerPlayerListMap();
            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("playerlist-count"), getAPI().getOnlinePlayers().size() + " player" + TextFormat.pluralization(getAPI().getOnlinePlayers().size())));
            for (ServerInfo serverInfo : serverPlayerListMap.keySet()) {
                List<String> playerList = serverPlayerListMap.get(serverInfo);
                sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("playerlist-preview"),serverInfo.getName(), TextFormat.stripOutliers(playerList.toString())));
            }
        } else {
            sendColorfulMessage("&cUsage: /playerlist");
        }
    }

}

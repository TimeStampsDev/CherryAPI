package net.cherryflavor.api.spigot.plugin.commands;

import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.command.ServerCherryCommand;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.spigot.world.CherryWorld;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/2/2021
 * Time 12:17 AM
 */

public class WorldTeleportCommand extends ServerCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public WorldTeleportCommand() {
        super(false, "worldtp",new String[] {"tpw"});
        addTabToBoth(1, "", getWorldList());
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Returns string list of worlds
     * @return
     */
    public List<String> getWorldList() {
        List<String> worldList = new ArrayList<>();
        for (World world : ServerAPI.getWorldManager().getWorlds()) {
            worldList.add(world.getName());
        }
        return  worldList;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public boolean playerExecute(OnlinePlayer player, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /" + label.toLowerCase() + " <world>");
        } else if (args.length == 1) {
            String worldEntry = args[0];
            if (ServerAPI.getAPI().getServer().getWorld(worldEntry) != null) {
                World world = ServerAPI.getAPI().getServer().getWorld(worldEntry);
                CherryWorld cherryWorld = new CherryWorld(worldEntry);
                if (world.getPlayers().size() == cherryWorld.getMaxPlayersAllowed()) {
                    sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("world-playermax-reached"));
                } else {
                    player.teleport(world);
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("teleported-to-world"), worldEntry));
                }
            } else {
                sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), worldEntry));
            }
        } else {
            player.sendColorfulMessage("&cUsage: /" + label.toLowerCase() + " <world>");
        }
        return true;
    }

    @Override
    public boolean consoleExecute(CommandSender console, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /" + label.toLowerCase() + " <player> [world]");
        } else if (args.length == 1) {
            sendColorfulMessage("&cUsage: /" + label.toLowerCase() + " <player> [world]");
        } else if (args.length == 2) {
            String worldEntry = args[1];
            if (ServerAPI.getAPI().isOnline(args[0])) {
                OnlinePlayer player = new OnlinePlayer(args[0]);

                if (ServerAPI.getAPI().getServer().getWorld(worldEntry) != null) {
                    World world = ServerAPI.getAPI().getServer().getWorld(worldEntry);
                    CherryWorld cherryWorld = new CherryWorld(worldEntry);
                    if (world.getPlayers().size() == cherryWorld.getMaxPlayersAllowed()) {
                        sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("world-playermax-reached"));
                    } else {
                        player.teleport(world);
                        sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("sent-player-to-world"), player.getUsername(), worldEntry));
                    }
                } else {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), worldEntry));
                }

            } else {
                sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("player-not-online"), args[0]));
            }
        } else {
            sendColorfulMessage("&cUsage: /" + label.toLowerCase() + " <player> [world]");
        }
        return true;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

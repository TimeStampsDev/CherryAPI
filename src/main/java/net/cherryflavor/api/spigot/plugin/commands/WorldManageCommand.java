package net.cherryflavor.api.spigot.plugin.commands;

import net.cherryflavor.api.other.PageMaker;
import net.cherryflavor.api.other.PagePreviewBuilder;
import net.cherryflavor.api.other.help.HelpPageMaker;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.command.ServerCherryCommand;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.spigot.world.CherryWorld;
import net.cherryflavor.api.spigot.world.WorldFlag;
import net.cherryflavor.api.tools.TextFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 3/2/2021
 * Time 12:16 AM
 */

public class WorldManageCommand extends ServerCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public WorldManageCommand() {
        super(false, "worldmng", new String[] {"wmng"});

        addTabToBoth(1, "", Arrays.asList("help","list","create","delete","info","setspawn","setmaxplayers", "addflag", "removeflag", "listflags", "list"));
        addTabToBoth(2, "setmaxplayers", getWorldList());


        getHelpPage().addCommandHelp("/worldmng list", "Lists all worlds");
        getHelpPage().addCommandHelp("/worldmng create <worldname> <worldtype>", "Creates a world");
        getHelpPage().addCommandHelp("/worldmng delete <worldname>", "Deletes a world");
        getHelpPage().addCommandHelp("/worldmng info <world>", "Gets information of world");
        getHelpPage().addCommandHelp("/worldmng setspawn <world>", "Sets spawn for world");
        getHelpPage().addCommandHelp("/worldmng setmaxplayers <world> <max>", "Sets max amount of players for world");
        getHelpPage().addCommandHelp("/worldmng addflag <worldname> <flag>", "Adds a flag to a world");
        getHelpPage().addCommandHelp("/worldmng removeflag <worldname> <flag>", "Removes a flag to a world");
        getHelpPage().addCommandHelp("/worldmng listflags", "Gets list of flags and descriptions of each");
        getHelpPage().addCommandHelp("/worldmng list", "List worlds");

    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Creates an appealing preview of page
     * @param pageNumber
     * @return
     */
    public List<String> createHelpPageView(int pageNumber) {
        PagePreviewBuilder previewBuilder = new PagePreviewBuilder(getHelpPage());

        previewBuilder.addHeader(getAPI().getBasicMessages().getString("help.header"));
        previewBuilder.addFooter(getAPI().getBasicMessages().getString("help.footer"));

        return previewBuilder.build(pageNumber, false);
    }

    /**
     * Returns string list of worlds
     * @return
     */
    public List<String> getWorldList() {
        List<String> worldList = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worldList.add(world.getName());
        }
        return  worldList;
    }

    /**
     * Returns string list of flags
     * @param worldFlags
     * @return
     */
    public List<String> createStringWorldFlagList(List<WorldFlag> worldFlags) {
        List<String> worldFlagStringList = new ArrayList<>();
        String c = ServerAPI.getAPI().getBasicMessages().getString("world-info-message-color");
        for (WorldFlag flag : worldFlags) {
            worldFlagStringList.add("&f" + flag.getLabel());
        }
        return worldFlagStringList;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public boolean playerExecute(OnlinePlayer player, Command command, String label, String[] args) {
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /worldmng help [1-" + getHelpPage().getNumberOfPages() + "]");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                int n = 0;
                int size = getWorldList().size();
                sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("worlds-list-message"), size + " world" + TextFormat.pluralization(size)));
                for (String world : getWorldList()) {
                    n++;
                    sendColorfulMessage("&7#" + n + " &e" + world);
                }
            } else if (args[0].equalsIgnoreCase("listflags")) {
                int n = 0;
                int size = WorldFlag.values().length;
                sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("flags-list-message"), size + " flag" + TextFormat.pluralization(size)));
                for (WorldFlag flag : WorldFlag.values()) {
                    n++;
                    sendColorfulMessage("&7#" + n + " &e" + TextFormat.addRightPadding(flag.getLabel(), ' ', 20) + "&7" + flag.getDescription());
                }
            } else {
                player.sendColorfulMessage("&cUsage: /worldmng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), args[1].toLowerCase()));
                    sendColorfulMessage("&cWorld names are case sensitive.");
                } else {
                    CherryWorld world = new CherryWorld(args[1]);
                    Location spawn = world.getWorld().getSpawnLocation();
                    String c = ServerAPI.getAPI().getBasicMessages().getString("world-info-message-color");
                    sendColorfulMessage(
                            c + "Info for the world : &f" + world.getWorldName(),
                            c + "Max Players Allowed: &f" + world.getMaxPlayersAllowed(),
                            c + "Spawn Location:",
                            TextFormat.addLeftPadding("", ' ', 2) + c + "X: &f" + spawn.getX(),
                            TextFormat.addLeftPadding("", ' ', 2) + c + "Y: &f" + spawn.getY(),
                            TextFormat.addLeftPadding("", ' ', 2) + c + "Z: &f" + spawn.getZ()
                    );

                    List<WorldFlag> flags = world.getWorldFlags();
                    sendColorfulMessage(c + "World Flags: " + correctCommas(TextFormat.stripOutliers(createStringWorldFlagList(flags).toString())));
                }
            } else if (args[0].equalsIgnoreCase("setspawn")) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), args[1].toLowerCase()));
                    sendColorfulMessage("&cWorld names are case sensitive.");
                } else {
                    Location currentLocation = player.getLocation();
                    CherryWorld world = new CherryWorld(player.getLocation().getWorld().getName());
                    world.getWorld().setSpawnLocation(currentLocation);
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("spawn-location-set"), world.getWorldName()));
                }
            } else if (args[0].equalsIgnoreCase("setmaxplayers")) {
                player.sendColorfulMessage("&cUsage: /worldmng setmaxplayers <world> <size>");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("setmaxplayers")) {
                Integer maxPlayers = 0;
                try {
                    maxPlayers = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("invalid-number-message"));
                }

                if (Bukkit.getWorld(args[1]) == null) {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), args[1]));
                    sendColorfulMessage("&cWorld names are case sensitive.");
                } else {
                    CherryWorld world = new CherryWorld(args[1]);
                    world.getConfig().getConfig().set("maxplayers", maxPlayers);
                    world.getConfig().saveFile();
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("maxplayers-set"),maxPlayers, world.getWorldName()));
                }
            } else if (args[0].equalsIgnoreCase("addflag")) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), args[1]));
                    sendColorfulMessage("&cWorld names are case sensitive.");
                } else {
                    CherryWorld world = new CherryWorld(args[1]);
                    if (WorldFlag.parseByConfigTag(args[2]) == null) {
                        sendColorfulMessage(getAPI().getBasicMessages().getString("invalid-flag"));
                    } else {
                        WorldFlag flag = WorldFlag.parseByConfigTag(args[2]);
                        if (world.getWorldFlags().contains(flag)) {
                            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("flag.already-added"), world.getWorldName()));
                        } else {
                            getAPI().getWorldManager().addFlag(world, flag);
                            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("flag.added"), flag.getLabel(), world.getWorldName()));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("removeflag")) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("world-not-exists"), args[1].toLowerCase()));
                    sendColorfulMessage("&cWorld names are case sensitive.");
                } else {
                    CherryWorld world = new CherryWorld(args[1]);
                    if (WorldFlag.parseByConfigTag(args[2]) == null) {
                        sendColorfulMessage(getAPI().getBasicMessages().getString("invalid-flag"));
                    } else {
                        WorldFlag flag = WorldFlag.parseByConfigTag(args[2]);
                        if (!world.getWorldFlags().contains(flag)) {
                            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("flag.already-removed"), world.getWorldName()));
                        } else {
                            getAPI().getWorldManager().removeFlag(world, flag);
                            sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("flag.removed"), flag.getLabel(), world.getWorldName()));
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean consoleExecute(CommandSender console, Command command, String label, String[] args) {
        return false;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.command.BungeeCommandManager;
import net.cherryflavor.api.bungee.event.BungeeCherryListener;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.other.PageMaker;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/24/21
 * Time 7:19 PM
 */
public class HandlerManageCommand extends BungeeCherryCommand {

    private PageMaker helpPage;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public HandlerManageCommand() {
        super(false, "handlermng", new String[]{"hmanage","hm"});

        helpPage = new PageMaker(10);

        helpPage.addPage( // page 1
                "&7/handlermng enablecmd <command>",
                "  &7- Enables a command",
                "&7/handlermng disablecmd <command>",
                "  &7- Disables a command",
                "&7/handlermng cmdlist",
                "  &7- Previews sorted command list",
                "&7/handlermng enableevent <event>",
                "  &7- Enables an event",
                "&7/handlermng disableevent <event>",
                "  &7- Disables an event",
                "&7/handlermng eventlist",
                "  &7- Previews sorted event list"
        );
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Returns Help Page maker
     * @return
     */
    public PageMaker getHelpPage() { return this.helpPage; }

    /**
     * Creates an appealing preview of page
     * @param pageNumber
     * @return
     */
    public List<String> createHelpPageView(int pageNumber) {
        List<Object> page = getHelpPage().getPage(pageNumber);
        List<String> builder = new ArrayList<>();

        builder.add("&e" + TextFormat.addRightPadding("", '=', 25));
        builder.add(TextFormat.addLeftPadding("", ' ', 10) + "&6Help" + TextFormat.addRightPadding("", ' ', 10));
        if (pageNumber > 0) {
            builder.add(TextFormat.addLeftPadding("", ' ', 5) + "&7(Page " + pageNumber + " / " + getHelpPage().getNumberOfPages() + ")" + TextFormat.addRightPadding("", ' ', 5));
        }

        for (Object entryAsObject : page) {
            String entryAsString = (String) entryAsObject;
            builder.add(entryAsString);
        }

        builder.add("&e" + TextFormat.addRightPadding("", '=', 25));

        return builder;
    }

    /**
     * Get color coded command list
     * @return
     */
    public List<String> getColorCodedCommandList() {
        List<String> colorCodedCommandList = new ArrayList<>();

        for (BungeeCherryCommand command : getAPI().getCommandManager().getCommandList()) {
            if (!command.isCancelled()) {
                colorCodedCommandList.add("&e" + command.getCommand());
            } else {
                colorCodedCommandList.add("&c" + command.getCommand());
            }
        }

        return colorCodedCommandList;
    }

    /**
     * Get color coded event list
     * @return
     */
    public List<String> getColorCodedEventList() {
        List<String> colorCodedCommandList = new ArrayList<>();

        for (BungeeCherryListener command : getAPI().getListenerManager().getListenerList()) {
            if (!command.isCancelled()) {
                colorCodedCommandList.add("&e" + command.getListenerName());
            } else {
                colorCodedCommandList.add("&c" + command.getListenerName());
            }
        }

        return colorCodedCommandList;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    @Override
    public void playerExecute(BungeePlayer player, String[] args) {
        int commandSize = getAPI().getCommandManager().getCommandList().size();
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                player.sendColorfulMessage(createHelpPageView(0));
            } else if (args[0].equalsIgnoreCase("enablecmd")) {
                player.sendColorfulMessage("&cUsage: /handlermng enablecmd <command>");
            } else if (args[0].equalsIgnoreCase("disablecmd")) {
                player.sendColorfulMessage("&cUsage: /handlermng disablecmd <event>");
            } else if (args[0].equalsIgnoreCase("enableevent")) {
                player.sendColorfulMessage("&cUsage: /handlermng enableevent <event>");
            } else if (args[0].equalsIgnoreCase("disableevent")) {
                player.sendColorfulMessage("&cUsage: /handlermng disableevent <event>");
            } else if (args[0].equalsIgnoreCase("cmdlist")) {
                player.sendColorfulMessage(
                        String.format(getAPI().getBasicMessages().getString("current-command-count"), commandSize + " command" + TextFormat.pluralization(commandSize)),
                        "&f" + TextFormat.stripOutliers(getColorCodedCommandList().toString())
                );
            } else if (args[0].equalsIgnoreCase("eventlist")) {
                player.sendColorfulMessage(
                        String.format(getAPI().getBasicMessages().getString("current-command-count"), commandSize + " command" + TextFormat.pluralization(commandSize)),
                        "&f" + TextFormat.stripOutliers(getColorCodedCommandList().toString())
                );
            } else {
                player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                try {
                    Integer page = Integer.parseInt(args[1])-1;

                    if (page > getHelpPage().getNumberOfPages()) {
                        player.sendColorfulMessage(getAPI().getBasicMessages().getString("cannot-exceed-number-of-pages"));
                    } else {
                        player.sendColorfulMessage(createHelpPageView(page));
                    }

                } catch (NumberFormatException ex) {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("invalid-number-message"));
                }
            } else if (args[0].equalsIgnoreCase("enablecmd")) {
                String commandPrompt = args[1].toLowerCase();
                if (getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (!cmd.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-enabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(false);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-enabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("disablecmd")) {
                String commandPrompt = args[1].toLowerCase();
                if (getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (cmd.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-disabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(true);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-disabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("enableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (!listener.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-enabled"), listener.getListenerName().toLowerCase()));
                    } else {
                        listener.setCancelled(false);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-enabled"), listener.getListenerName().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("disableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (listener.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-disabled"), listener.getListenerName().toLowerCase()));
                    } else {
                        listener.setCancelled(true);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-disabled"), listener.getListenerName().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exist"));
                }
            } else {
                player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else {
            player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
        }
    }

    @Override
    public void consoleExecute(CommandSender console, String[] args) {

    }
}

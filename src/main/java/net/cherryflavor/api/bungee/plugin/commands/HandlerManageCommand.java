package net.cherryflavor.api.bungee.plugin.commands;

import net.cherryflavor.api.bungee.command.BungeeCherryCommand;
import net.cherryflavor.api.bungee.event.BungeeCherryListener;
import net.cherryflavor.api.bungee.player.BungeePlayer;
import net.cherryflavor.api.other.PageMaker;
import net.cherryflavor.api.other.PagePreviewBuilder;
import net.cherryflavor.api.other.help.HelpPageMaker;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/24/21
 * Time 7:19 PM
 */
public class HandlerManageCommand extends BungeeCherryCommand {

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public HandlerManageCommand() {
        super(false, "handlermng", new String[]{"hmanage","hm"});

        addTabToBoth(1, "", Arrays.asList("help","enablecmd","disablecmd","enableevent","disableevent","cmdlist","eventlist"));
        addTabToBoth(2, "enablecmd", getStringCommandList());
        addTabToBoth(2, "disablecmd", getStringCommandList());
        addTabToBoth(2, "enableevent", getStringEventList());
        addTabToBoth(2, "disableevent", getStringEventList());

        getHelpPage().addCommandHelp("/handlermng enablecmd <command>", "Enables a command");
        getHelpPage().addCommandHelp("/handlermng disablecmd <command>", "Disables a command");
        getHelpPage().addCommandHelp("/handlermng cmdlist", "Previews sorted command list");
        getHelpPage().addCommandHelp("/handlermng enableevent <event>", "Enables an event");
        getHelpPage().addCommandHelp("/handlermng disableevent <event>", "Disables an event");
        getHelpPage().addCommandHelp("/handlermng eventlist", "Previews sorted event list");


    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

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
     * Gets string list of commands
     * @return
     */
    public List<String> getStringCommandList() {
        List<String> commandList = new ArrayList<>();

        for (BungeeCherryCommand command : getAPI().getCommandManager().getCommandList()) {
            commandList.add(command.getCommand());
        }

        return commandList;
    }

    /**
     * Get color coded event list
     * @return
     */
    public List<String> getColorCodedEventList() {
        List<String> colorCodedCommandList = new ArrayList<>();

        for (BungeeCherryListener listener : getAPI().getListenerManager().getListenerList()) {
            if (!listener.isCancelled()) {
                colorCodedCommandList.add("&e" + listener.getListenerName());
            } else {
                colorCodedCommandList.add("&c" + listener.getListenerName());
            }
        }

        return colorCodedCommandList;
    }

    /**
     * Returns string list of events
     * @return
     */
    public List<String> getStringEventList() {
        List<String> eventList = new ArrayList<>();

        for (BungeeCherryListener listener : getAPI().getListenerManager().getListenerList()) {
            eventList.add(listener.getListenerName());
        }

        return eventList;
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
        int eventSize = getAPI().getListenerManager().getListenerList().size();
        if (args.length == 0) {
            player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enablecmd")) {
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
                        "&f" + correctCommas(TextFormat.stripOutliers(getColorCodedCommandList().toString()))
                );
            } else if (args[0].equalsIgnoreCase("eventlist")) {
                player.sendColorfulMessage(
                        String.format(getAPI().getBasicMessages().getString("current-event-count"), eventSize + " event" + TextFormat.pluralization(eventSize)),
                        "&f" + correctCommas(TextFormat.stripOutliers(getColorCodedEventList().toString()))
                );
            } else {
                player.sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("enablecmd")) {
                String commandPrompt = args[1].toLowerCase();
                if (!getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (!cmd.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-enabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(false);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-enabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exists"));
                }
            } else if (args[0].equalsIgnoreCase("disablecmd")) {
                String commandPrompt = args[1];
                if (!getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (cmd.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-disabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(true);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-disabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exists"));
                }
            } else if (args[0].equalsIgnoreCase("enableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (!listener.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-enabled"), listener.getListenerName()));
                    } else {
                        listener.setCancelled(false);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-enabled"), listener.getListenerName()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exists"));
                }
            } else if (args[0].equalsIgnoreCase("disableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (listener.isCancelled()) {
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-disabled"), listener.getListenerName()));
                    } else {
                        listener.setCancelled(true);
                        player.sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-disabled"), listener.getListenerName()));
                    }
                } else {
                    player.sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exists"));
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
        int commandSize = getAPI().getCommandManager().getCommandList().size();
        if (args.length == 0) {
            sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enablecmd")) {
                sendColorfulMessage("&cUsage: /handlermng enablecmd <command>");
            } else if (args[0].equalsIgnoreCase("disablecmd")) {
                sendColorfulMessage("&cUsage: /handlermng disablecmd <event>");
            } else if (args[0].equalsIgnoreCase("enableevent")) {
                sendColorfulMessage("&cUsage: /handlermng enableevent <event>");
            } else if (args[0].equalsIgnoreCase("disableevent")) {
                sendColorfulMessage("&cUsage: /handlermng disableevent <event>");
            } else if (args[0].equalsIgnoreCase("cmdlist")) {
                sendColorfulMessage(
                        String.format(getAPI().getBasicMessages().getString("current-command-count"), commandSize + " command" + TextFormat.pluralization(commandSize)),
                        "&f" + TextFormat.stripOutliers(getColorCodedCommandList().toString())
                );
            } else if (args[0].equalsIgnoreCase("eventlist")) {
                sendColorfulMessage(
                        String.format(getAPI().getBasicMessages().getString("current-command-count"), commandSize + " command" + TextFormat.pluralization(commandSize)),
                        "&f" + TextFormat.stripOutliers(getColorCodedEventList().toString())
                );
            } else {
                sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                try {
                    Integer page = Integer.parseInt(args[1])-1;

                    if (page > getHelpPage().getNumberOfPages()) {
                        sendColorfulMessage(getAPI().getBasicMessages().getString("cannot-exceed-number-of-pages"));
                    } else {
                        sendColorfulMessage(getHelpPage().getPagePreview(page));
                    }

                } catch (NumberFormatException ex) {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("invalid-number-message"));
                }
            } else if (args[0].equalsIgnoreCase("enablecmd")) {
                String commandPrompt = args[1].toLowerCase();
                if (getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (!cmd.isCancelled()) {
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-enabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(false);
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-enabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("disablecmd")) {
                String commandPrompt = args[1].toLowerCase();
                if (getAPI().getCommandManager().commandExists(commandPrompt)) {
                    BungeeCherryCommand cmd = getAPI().getCommandManager().getCommand(commandPrompt);
                    if (cmd.isCancelled()) {
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-already-disabled"), cmd.getCommand().toLowerCase()));
                    } else {
                        cmd.setCancelled(true);
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("command-disabled"), cmd.getCommand().toLowerCase()));
                    }
                } else {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("command-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("enableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (!listener.isCancelled()) {
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-enabled"), listener.getListenerName().toLowerCase()));
                    } else {
                        listener.setCancelled(false);
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-enabled"), listener.getListenerName().toLowerCase()));
                    }
                } else {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exist"));
                }
            } else if (args[0].equalsIgnoreCase("disableevent")) {
                String eventPrompt = args[1].toLowerCase();
                if (getAPI().getListenerManager().listenerExists(eventPrompt)) {
                    BungeeCherryListener listener = getAPI().getListenerManager().getListener(eventPrompt);
                    if (listener.isCancelled()) {
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-already-disabled"), listener.getListenerName().toLowerCase()));
                    } else {
                        listener.setCancelled(true);
                        sendColorfulMessage(String.format(getAPI().getBasicMessages().getString("event-disabled"), listener.getListenerName().toLowerCase()));
                    }
                } else {
                    sendColorfulMessage(getAPI().getBasicMessages().getString("event-not-exist"));
                }
            } else {
                sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
            }
        } else {
            sendColorfulMessage("&cUsage: /handlermng help [1-" + getHelpPage().getNumberOfPages() + "]");
        }
    }
}

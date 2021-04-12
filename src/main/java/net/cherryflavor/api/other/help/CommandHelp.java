package net.cherryflavor.api.other.help;

public class CommandHelp {

    String command;
    String description;

    public CommandHelp(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() { return this.command; }
    public String getDescription() { return this.description; }
    
}

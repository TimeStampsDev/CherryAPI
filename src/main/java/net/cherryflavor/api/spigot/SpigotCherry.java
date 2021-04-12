package net.cherryflavor.api.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import net.cherryflavor.api.spigot.voting.BallotType;
import net.cherryflavor.api.spigot.voting.Candidate;
import net.cherryflavor.api.spigot.voting.VoteSession;
import net.cherryflavor.api.spigot.interfaces.Lobby;
import net.cherryflavor.api.spigot.items.ItemManipulator;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class SpigotCherry extends JavaPlugin {

    private ServerAPI serverAPI;

    public static Lobby lobby;
    public static VoteSession session;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public void onEnable() {
        serverAPI = new ServerAPI(this);

        ItemManipulator.registerCustomEnchants();
        
        lobby = new Lobby("testlobby");
    
        session = new VoteSession("test", 50, lobby, BallotType.INVENTORY, 
        new Candidate("test1", "test"), 
        new Candidate("test2","fuck"), 
        new Candidate("test3","fuck"));

    }

    public void onDisable() {

        serverAPI.getWorldManager().saveWorlds();
        serverAPI.getWorldManager().saveFlagLists();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}

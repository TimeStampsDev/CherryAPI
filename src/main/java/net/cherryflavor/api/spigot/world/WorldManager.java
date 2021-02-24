package net.cherryflavor.api.spigot.world;

import net.cherryflavor.api.spigot.ServerAPI;
import org.bukkit.World;

import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class WorldManager {

    private ServerAPI serverAPI;
    private List<World> worlds;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param serverAPI
     */
    public WorldManager(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
        this.worlds = serverAPI.getServer().getWorlds();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Gets list of worlds
     * @return
     */
    public List<World> getWorlds() {
        return worlds;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    public void generateWorld() {

    }

    /**
     * Adds world to world list
     * @param world
     */
    public void addWorld(World world) {
        worlds.add(world);
    }

    /**
     * Removes world to world list
     * @param world
     */
    public void removeWorld(World world) {
        worlds.remove(world);
    }
}

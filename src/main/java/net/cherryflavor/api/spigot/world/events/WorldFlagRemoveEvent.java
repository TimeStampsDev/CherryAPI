package net.cherryflavor.api.spigot.world.events;

import net.cherryflavor.api.spigot.world.CherryWorld;
import net.cherryflavor.api.spigot.world.WorldFlag;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created on 3/8/2021
 * Time 10:39 PM
 */

public class WorldFlagRemoveEvent extends Event {

    private CherryWorld world;
    private WorldFlag flag;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public WorldFlagRemoveEvent(CherryWorld world, WorldFlag flag) {
        this.world = world;
        this.flag = flag;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public WorldFlag getFlag() {
        return flag;
    }

    public CherryWorld getWorld() {
        return world;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

}

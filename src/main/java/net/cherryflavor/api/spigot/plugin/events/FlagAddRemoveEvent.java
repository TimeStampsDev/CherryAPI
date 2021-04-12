package net.cherryflavor.api.spigot.plugin.events;

import net.cherryflavor.api.spigot.event.ServerCherryListener;
import net.cherryflavor.api.spigot.world.events.WorldFlagAddEvent;
import net.cherryflavor.api.spigot.world.events.WorldFlagRemoveEvent;
import net.md_5.bungee.event.EventHandler;

public class FlagAddRemoveEvent extends ServerCherryListener {

    public FlagAddRemoveEvent() {
        super("API_FlagAddRemove", false);
    }

    @EventHandler
    public void add(WorldFlagAddEvent event) {
      
    }

    @EventHandler
    public void remove(WorldFlagRemoveEvent event) {
      
    }
    
}

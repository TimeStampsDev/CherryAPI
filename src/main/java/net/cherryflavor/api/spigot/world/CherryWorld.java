package net.cherryflavor.api.spigot.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mysql.fabric.Server;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.event.ServerCherryListener;
import net.cherryflavor.api.spigot.player.OnlinePlayer;

/**
 * Created on 3/2/2021
 * Time 1:19 AM
 */

public class CherryWorld {

    private String worldName;
    private World world;
    private String permissionAccess;
    private List<WorldFlag> worldFlags = new ArrayList<>();
    private int maxPlayersAllowed;

    private boolean isLoaded;
    private boolean eventsLoaded;

    private CherryConfig worldConfig;

    private List<ServerCherryListener> worldFlagEvents;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public CherryWorld(String worldName, int maxPlayersAllowed, List<WorldFlag> worldFlags) {
        this.worldName = worldName;
    
        initialize(worldName);

        worldConfig.getConfig().set("maxplayers", getDefaultMaxPlayersAllowed());
        worldConfig.getConfig().set("flags", worldFlags);
        worldConfig.saveFile();

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getAPI().getWorldManager().loadWorld(worldName);
        }

        this.world = Bukkit.getWorld(worldName);


    }

    public CherryWorld(World world) {
        this.worldName = world.getName();
    
        initialize(this.worldName);

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getAPI().getWorldManager().loadWorld(worldName);
        }

        this.world = Bukkit.getWorld(worldName);

    }

    public CherryWorld(String world) {
        initialize(world);

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getAPI().getWorldManager().loadWorld(worldName);
        }

        this.world = Bukkit.getWorld(worldName);

    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

   /**
     * Returns world flags
     * @return
     */
    public List<WorldFlag> getWorldFlags() {
        return ServerAPI.getAPI().getWorldManager().flagMap.get(new CherryWorld(world));
    }


    /**
     * Returns string list of world flags
     * @return
     */
    public List<String> getWorldFlagStringList() {
        List<String> sList = new ArrayList<>();
        for (WorldFlag flag : getWorldFlags()) {
            sList.add(flag.getConfigTag());
        }
        return sList;
    }

    /**
     *
     * @return
     */
    public CherryConfig getConfig() {
        return new CherryConfig(this.worldConfig.getFile());
    }

    /**
     * Returns Bukkit World
     * @return
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns permission to go to world
     * @return
     */
    public String getPermissionAccess() {
        return permissionAccess;
    }

    /**
     *
     * @return
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     *
     * @return
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     *
     * @return
     */
    public int getMaxPlayersAllowed() {
        return maxPlayersAllowed;
    }

    /**
     * 
     * @return
     */
    public int getDefaultMaxPlayersAllowed() {
        return ServerAPI.getAPI().getWorldManager().getConfig().getInt("default-max-players-allowed-per-world");
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Initialize config
     */
    public void initialize(String worldName) {
        this.worldName = worldName;
        this.worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
        this.permissionAccess = WorldManager.accessPermission + worldName;
        this.worldFlagEvents = new ArrayList<>();
        this.worldFlags.addAll(WorldFlag.parseStringList(this.worldConfig.getConfig().getStringList("flags")));
        this.isLoaded = false;
        this.eventsLoaded = false;
        if (!new File("plugins/CherryAPI/worlds/" + worldName + ".yml").exists()) {
            this.maxPlayersAllowed = getMaxPlayersAllowed();
            CherryConfig.createFile("plugins/CherryAPI/worlds/" + worldName + ".yml");

            this.worldConfig.loadFile();
            this.worldConfig.getConfig().set("name", worldName);
            this.worldConfig.getConfig().set("maxplayers", getDefaultMaxPlayersAllowed());
            this.worldConfig.getConfig().set("flags", Collections.EMPTY_LIST);

            this.worldConfig.saveFile();
     
        } else {
            this.maxPlayersAllowed = worldConfig.getConfig().getInt("maxplayers");
        
        }

        ServerAPI.getAPI().getWorldManager().flagMap.put(this, worldFlags);
    }

    /**
     * Loads
     */
    public void load() {
        if (isLoaded == false) {
            this.isLoaded = true;

            if (eventsLoaded == false) {
                eventsLoaded = true;
                ServerAPI.getAPI().getListenerManager().silentRegisterListener(
                        new FALL(),
                        new HOSTILE_SPAWN(),
                        new PASSIVE_SPAWN(),
                        new PVP(),
                        new BLOCKPLACE(),
                        new BLOCKDESTROY(),
                        new NOITEMDROP()
                );
                ServerAPI.getAPI().getListenerManager().debug("All Flag-Events have registed for " + worldName);
            }

            if (Bukkit.getServer().getWorld(worldName) == null) {
                ServerAPI.getAPI().getWorldManager().loadWorld(worldName);
                this.world = Bukkit.getServer().getWorld(worldName);
            } else {
                this.world = Bukkit.getServer().getWorld(worldName);
            }

            if (getWorldFlagStringList().contains(WorldFlag.NO_HOSTILE_MOBS.getConfigTag())) {
                new HOSTILE_SPAWN().update();
            }

            if (getWorldFlagStringList().contains(WorldFlag.NO_PASSIVE_MOBS.getConfigTag())) {
                new PASSIVE_SPAWN().update();
            }

        }
    }

    public void deleteConfigFile() {
        CherryConfig.deleteFolder(getConfig().getFile());
    }

    //==================================================================================================================
    // EVENTS
    //==================================================================================================================

    public class FALL extends ServerCherryListener {

        public FALL() {
            super(worldName + "_FALLDAMAGE", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player) {
                if (event.getEntity().getLocation().getWorld().getName().equals(world.getName())) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        if (getWorldFlags().contains(WorldFlag.NO_FALL_DAMAGE)) {
                            event.setCancelled(true);
                        } else {
                            event.setCancelled(false);
                        }
                    }
                }
            }
        }


    }

    public class HOSTILE_SPAWN extends ServerCherryListener {

        public HOSTILE_SPAWN() {
            super(worldName + "_HOSTILESPAWN", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(EntitySpawnEvent event) {
            if (event.getEntity() instanceof Creature) {
                if (event.getEntity() instanceof Monster) {
                    if (event.getEntity().getLocation().getWorld().getName().equals(world.getName())) {
                        if (getWorldFlags().contains(WorldFlag.NO_HOSTILE_MOBS)) {
                            event.setCancelled(true);
                        } else {
                            event.setCancelled(false);
                        }
                    }
                }
            }
        }

        public void update() {
            for (Entity entities : getWorld().getEntities()) {
                if (entities instanceof Monster) {
                    entities.remove();
                }
            }
        }

    }

    public class PASSIVE_SPAWN extends ServerCherryListener {

        public PASSIVE_SPAWN() {
            super(worldName + "_PASSIVESPAWN", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(EntitySpawnEvent event) {
            if (event.getEntity() instanceof Creature) {
                if (!(event.getEntity() instanceof Monster)) {
                    if (event.getEntity().getLocation().getWorld().getName().equals(world.getName())) {
                        if (getWorldFlags().contains(WorldFlag.NO_PASSIVE_MOBS)) {
                            event.setCancelled(true);
                        } else {
                            event.setCancelled(false);
                        }
                    }
                }
            }
        }

        public void update() {
            for (Entity entities : getWorld().getEntities()) {
                if (!(entities instanceof Monster)) {
                    if (!(entities instanceof Item)) {
                        entities.remove();
                    }
                }
            }
        }

    }

    public class PVP extends ServerCherryListener {

        public PVP() {
            super(worldName + "_PVP", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(EntityDamageByEntityEvent event) {
            if (event.getEntity() instanceof Player) {
                if (event.getEntity().getLocation().getWorld().getName().equals(world.getName())) {
                    if (event.getDamager() instanceof Player) {
                        Player attacker = (Player) event.getDamager();
                        OnlinePlayer damager = new OnlinePlayer(attacker);
                        if (getWorldFlags().contains(WorldFlag.NO_PVP)) {

                            event.setCancelled(true);
                            damager.sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("pvp-disabled"));
                        }
                    }
                }
            }
        }

    }

    public class BLOCKPLACE extends ServerCherryListener {

        public BLOCKPLACE() {
            super(worldName + "_BLOCKPLACE", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(BlockPlaceEvent event) {
            OnlinePlayer player = new OnlinePlayer(event.getPlayer());
            System.out.print(getWorldFlagStringList().toString());
            System.out.println(ServerAPI.getAPI().getWorldManager().flagMap.get(new CherryWorld(world)).contains(WorldFlag.BLOCK_PLACE));
            if (getWorldFlags().contains(WorldFlag.BLOCK_PLACE)) {
                if (!player.hasPermission("cherryapi.flag-override")) {
                    event.setCancelled(true);
                    player.sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("blockplace-disabled"));
                }
            }
        }

    }

    public class BLOCKDESTROY extends ServerCherryListener {

        public BLOCKDESTROY() {
            super(worldName + "_BLOCKDESTROY", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(BlockBreakEvent event) {
            OnlinePlayer player = new OnlinePlayer(event.getPlayer());
            if (getWorldFlagStringList().contains(WorldFlag.BLOCK_DESTROY.getConfigTag())) {
                if (!player.hasPermission("cherryapi.flag-override")) {
                    event.setCancelled(true);
                    player.sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("blockdestroy-disabled"));
                }
            }
        }

    }

    public class NOITEMDROP extends ServerCherryListener {

        public NOITEMDROP() {
            super(worldName + "_NOITEMDROP", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(PlayerDropItemEvent event) {
            if (getWorldFlags().contains(WorldFlag.NO_ITEM_DROP)) {
                Player player = (Player) event.getPlayer();
                if (!player.hasPermission("cherryapi.flag-override")) {
                    event.setCancelled(true);
                    new OnlinePlayer(player).sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("itemdrop-disabled"));  
                }      
            }
        }

    }

}

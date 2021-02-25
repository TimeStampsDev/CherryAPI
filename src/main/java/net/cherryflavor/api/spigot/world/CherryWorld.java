package net.cherryflavor.api.spigot.world;

import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.event.ServerCherryListener;
import net.cherryflavor.api.spigot.event.ServerListenerManager;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.spigot.plugin.commands.WorldManageCommand;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/2/2021
 * Time 1:19 AM
 */

public class CherryWorld {

    private String worldName;
    private World world;
    private String permissionAccess;
    private List<WorldFlag> worldFlags;
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
        this.permissionAccess = WorldManager.accessPermission + worldName;
        this.maxPlayersAllowed = maxPlayersAllowed;
        this.worldFlags = worldFlags;

        this.isLoaded = false;
        this.eventsLoaded = false;

        this.worldFlagEvents = new ArrayList<>();

        if (!new File("plugins/CherryAPI/worlds/" + worldName + ".yml").exists()) {
            CherryConfig.createFile("plugins/CherryAPI/worlds/" + worldName + ".yml");
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            worldConfig.getConfig().set("name", worldName);
            worldConfig.getConfig().set("maxplayers", maxPlayersAllowed);
            worldConfig.getConfig().set("flags", worldFlags);
            worldConfig.saveFile();
            this.worldConfig = worldConfig;
        } else {
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            this.maxPlayersAllowed = worldConfig.getConfig().getInt("maxplayers");
            this.worldFlags = WorldFlag.parseStringList(worldConfig.getConfig().getStringList("flags"));
            this.worldConfig = worldConfig;
        }

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getWorldManager().loadWorld(worldName);
        }

        this.world = Bukkit.getWorld(worldName);


    }

    public CherryWorld(World world) {
        this.worldName = world.getName();
        this.permissionAccess = WorldManager.accessPermission + worldName;
        this.worldFlagEvents = new ArrayList<>();

        this.isLoaded = false;
        this.eventsLoaded = false;

        if (!new File("plugins/CherryAPI/worlds/" + worldName + ".yml").exists()) {
            this.maxPlayersAllowed = getMaxPlayersAllowed();
            this.worldFlags = new ArrayList<>();
            CherryConfig.createFile("plugins/CherryAPI/worlds/" + worldName + ".yml");
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            worldConfig.loadFile();
            worldConfig.getConfig().set("name", worldName);
            worldConfig.getConfig().set("maxplayers", maxPlayersAllowed);
            worldConfig.getConfig().set("flags", worldFlags);
            worldConfig.saveFile();
            this.worldConfig = worldConfig;
        } else {
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            this.maxPlayersAllowed = worldConfig.getConfig().getInt("maxplayers");
            this.worldFlags = WorldFlag.parseStringList(worldConfig.getConfig().getStringList("flags"));
            this.worldConfig = worldConfig;
        }

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getWorldManager().loadWorld(worldName);
        }

        this.world = Bukkit.getWorld(worldName);

    }

    public CherryWorld(String world) {
        this.worldName = world;
        this.permissionAccess = WorldManager.accessPermission + worldName;
        this.worldFlagEvents = new ArrayList<>();

        this.isLoaded = false;
        this.eventsLoaded = false;

        if (!new File("plugins/CherryAPI/worlds/" + worldName + ".yml").exists()) {
            this.maxPlayersAllowed = getMaxPlayersAllowed();
            this.worldFlags = new ArrayList<>();
            CherryConfig.createFile("plugins/CherryAPI/worlds/" + worldName + ".yml");
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            worldConfig.loadFile();
            worldConfig.getConfig().set("name", worldName);
            worldConfig.getConfig().set("maxplayers", maxPlayersAllowed);
            worldConfig.getConfig().set("flags", worldFlags);
            worldConfig.saveFile();
            this.worldConfig = worldConfig;
        } else {
            CherryConfig worldConfig = new CherryConfig(new File("plugins/CherryAPI/worlds/" + worldName + ".yml"));
            this.maxPlayersAllowed = worldConfig.getConfig().getInt("maxplayers");
            this.worldFlags = WorldFlag.parseStringList(worldConfig.getConfig().getStringList("flags"));
            this.worldConfig = worldConfig;
        }

        if (Bukkit.getWorld(getWorldName()) == null) {
            ServerAPI.getWorldManager().loadWorld(worldName);
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
        return worldFlags;
    }

    /**
     *
     * @return
     */
    public CherryConfig getConfig() {
        return worldConfig;
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

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Loads
     */
    public void load() {
        if (isLoaded == false) {
            this.isLoaded = true;

            if (eventsLoaded == false) {
                eventsLoaded = true;
                ServerAPI.getListenerManager().registerListener(
                        new FALL(),
                        new PASSIVE_SPAWN(),
                        new HOSTILE_SPAWN(),
                        new PVP()
                );
            }

            if (Bukkit.getServer().getWorld(worldName) == null) {
                ServerAPI.getWorldManager().loadWorld(worldName);
                this.world = Bukkit.getServer().getWorld(worldName);
            } else {
                this.world = Bukkit.getServer().getWorld(worldName);
            }

            if (worldFlags.contains(WorldFlag.NO_HOSTILE_MOBS)) {
                ServerAPI.getPlugin().getServer().getWorld(worldName).setDifficulty(Difficulty.PEACEFUL);
            }

            if (worldFlags.contains(WorldFlag.NO_PASSIVE_MOBS)) {
                for (Entity entity : Bukkit.getWorld(worldName).getEntities()) {
                    if (entity instanceof Creature) {
                        if (!(entity instanceof Monster)) {
                            entity.remove();
                        }
                    }
                }
            }
        }
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

    }

    public class PVP extends ServerCherryListener {

        public PVP() {
            super(worldName + "_PVP", false);
            worldFlagEvents.add(this);
        }

        @EventHandler
        public void event(EntityDamageByEntityEvent event) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                OnlinePlayer damaged = new OnlinePlayer(player);
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

}

package net.cherryflavor.api.database;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.exceptions.InvalidAPIObjectException;
import net.cherryflavor.api.spigot.ServerAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class DatabaseManager {

    private static String prefix = "[DatabaseManager]";

    private List<Database> databaseList;
    private static CherryConfig config;

    private static boolean debug;

    public Object api;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param api
     */
    public DatabaseManager(Object api) {
        this.databaseList = new ArrayList<>();

        this.api = api;

        createFolder();
        createConfigFile();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Object must be cast to either ProxyAPI or ServerAPI only!
     * @return
     */
    public Object getAPI() {
        if (api instanceof ServerAPI) {
            return api;
        } else if (api instanceof ProxyAPI) {
            return api;
        } else {
            try {
                throw new InvalidAPIObjectException("Invalid api type");
            } catch (InvalidAPIObjectException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Creates 'databases' folder
     */
    public static void createFolder() {
        CherryConfig.makeFolder("plugins/CherryAPI/databases");
    }

    /**
     * creates 'databases-config.yml'
     */
    public void createConfigFile() {
        CherryConfig.createResource("databases-config.yml", new File(CherryConfig.getDataFolder(), "/databases/databases-config.yml"));

        config = new CherryConfig(new File(CherryConfig.getDataFolder(), "databases/databases-config.yml"));

        debug = config.getConfig().getBoolean("debug");
    }

    /**
     * if debug = true in databases-config.yml
     * debug messages will appear
     * @param debugMessage
     */
    public static void debug(String debugMessage) {
        if (config.getConfig().getBoolean("debug")) {
            System.out.println(this.prefix + " " + debugMessage);
        }
    }

    public void addDatabase(Database database) {
        if (Database d : databaseList) {
            if (!d.getActualName().equalsIgnoreCase(database.getActualName())) {
                databaseList.add(database);
            }
        }
    }

}

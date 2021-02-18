package net.cherryflavor.api.database;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.exceptions.InvalidAPIObjectException;
import net.cherryflavor.api.spigot.ServerAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private List<Database> databaseList;
    private CherryConfig config;

    private static boolean debug;

    public Object api;

    public DatabaseManager(Object api) {
        this.databaseList = new ArrayList<>();

        this.api = api;

        createFolder();
        createConfigFile();
    }

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

    public static void initFilesFor(Database database) {
        CherryConfig.createFile(database.getCherryConfig().getFile());
    }

    public static void createFolder() {
        CherryConfig.makeFolder("plugins/CherryAPI/databases");
    }

    public void createConfigFile() {
        CherryConfig.createResource("databases-config.yml", new File(CherryConfig.getDataFolder(), "/databases/databases-config.yml"));

        config = new CherryConfig(new File(CherryConfig.getDataFolder(), "databases/databases-config.yml"));

        debug = config.getConfig().getBoolean("debug");
    }

    public static void debug(String debugMessage) {
        if (debug == true) {
            System.out.println("[DatabaseManager] " + debugMessage);
        }
    }

}

package net.cherryflavor.api.database;

import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.exceptions.InvalidAPIObjectException;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private List<Database> databaseList;

    public Object api;

    public DatabaseManager(Object api) {
        this.databaseList = new ArrayList<>();

        this.api = api;
    }

    public Object getAPI() { return api; }

    public static void initFilesFor(Database database) {
        CherryConfig.createFile(database.getCherryConfig().getFile());
    }

    public static void createFolder() {
        CherryConfig.makeFolder("databases");
    }

    private static File getDataFolder(Object api) throws InvalidAPIObjectException {
        if (api instanceof JavaPlugin) {
            return ((JavaPlugin) api).getDataFolder();
        } else if (api instanceof Plugin) {
            return ((Plugin) api).getDataFolder();
        } else {
            throw new InvalidAPIObjectException("Object provided is not a JavaPlugin or Plugin");
        }
    }

    public static void debug(String debugMessage) {
        System.out.println("[DatabaseManager] " + debugMessage);
    }

}

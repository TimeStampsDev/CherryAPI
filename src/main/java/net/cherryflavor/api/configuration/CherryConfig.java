package net.cherryflavor.api.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class CherryConfig {

    private File file;
    private Configuration configuration;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public CherryConfig(String fileName) throws NullPointerException {
        if (fileExists(fileName)) {
            this.file = new File(fileName);
            configuration = loadConfigOfFile(file);
        } else {
            throw new NullPointerException("File name provided in input is invalid");
        }
    }

    public CherryConfig(File file) {
        if (file.exists()) {
            this.file = file;
            configuration = loadConfigOfFile(file);
        } else {
            throw new NullPointerException("File provided does not exist");
        }
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public static File getDataFolder() {
        return new File("plugins/CherryAPI");
    }

    public File getFile() { return this.file; }
    public Configuration getConfig() { return this.configuration; }

    public static final InputStream getResourceAsStream(String resource) {
        return CherryConfig.class.getClassLoader().getResourceAsStream(resource);
    }

    public static boolean fileExists(String fileName) {
        return new File("plugins/CherryAPI/" + fileName).exists();
    }

    private Configuration loadConfigOfFile(File file) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return config;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    public static void makeFolder(String folderName) {
        new File(folderName).mkdirs();
    }

    public static void createFile(File file) {
        if (!file.exists()) {
            try (InputStream in = CherryConfig.class.getResourceAsStream(file.getName())) {
                Files.copy(in, file.toPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void createFile(String filePath) {
        if (!fileExists(filePath)) {
            try (InputStream in = CherryConfig.class.getResourceAsStream(filePath)) {
                if (in == null) {
                    new File(filePath).createNewFile();
                } else {
                    Files.copy(in, new File(filePath).toPath());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void createResource(String resourceName, File toPath) {
        if (!toPath.exists()) {
            try (InputStream in = getResourceAsStream(resourceName)) {
                Files.copy(in, toPath.toPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void loadFile() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



}

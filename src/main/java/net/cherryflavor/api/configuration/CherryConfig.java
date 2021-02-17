package net.cherryflavor.api.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CherryConfig {

    private File file;
    private Configuration configuration;

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

    public File getFile() { return this.file; }
    public Configuration getConfig() { return this.configuration; }

    private static boolean fileExists(String fileName) {
        return new File(fileName).exists();
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

    public final InputStream getResourceAsStream(String resource) {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }

    public static void makeFolder(String folderName) {
        new File(folderName).mkdir();
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
                Files.copy(in, new File(filePath).toPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }



}

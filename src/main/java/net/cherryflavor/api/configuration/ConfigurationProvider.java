package net.cherryflavor.api.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public abstract class ConfigurationProvider {

    private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap();

    public ConfigurationProvider() {
    }

    public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider) {
        return (ConfigurationProvider)providers.get(provider);
    }

    public abstract void save(Configuration var1, File var2) throws IOException;

    public abstract void save(Configuration var1, Writer var2);

    public abstract Configuration load(File var1) throws IOException;

    public abstract Configuration load(File var1, Configuration var2) throws IOException;

    public abstract Configuration load(Reader var1);

    public abstract Configuration load(Reader var1, Configuration var2);

    public abstract Configuration load(InputStream var1);

    public abstract Configuration load(InputStream var1, Configuration var2);

    public abstract Configuration load(String var1);

    public abstract Configuration load(String var1, Configuration var2);

    static {
        try {
            providers.put(YamlConfiguration.class, new YamlConfiguration());
        } catch (NoClassDefFoundError var2) {
        }

        try {
            providers.put(JsonConfiguration.class, new JsonConfiguration());
        } catch (NoClassDefFoundError var1) {
        }

    }

}

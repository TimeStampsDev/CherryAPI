package net.cherryflavor.api.configuration;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class JsonConfiguration extends ConfigurationProvider {

    private final Gson json = (new GsonBuilder()).serializeNulls().setPrettyPrinting().registerTypeAdapter(Configuration.class, new JsonSerializer<Configuration>() {
        public JsonElement serialize(Configuration src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.self);
        }
    }).create();

    public void save(Configuration config, File file) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
        Throwable var4 = null;

        try {
            this.save(config, (Writer)writer);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (writer != null) {
                if (var4 != null) {
                    try {
                        writer.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    writer.close();
                }
            }

        }

    }

    public void save(Configuration config, Writer writer) {
        this.json.toJson(config.self, writer);
    }

    public Configuration load(File file) throws IOException {
        return this.load((File)file, (Configuration)null);
    }

    public Configuration load(File file, Configuration defaults) throws IOException {
        FileInputStream is = new FileInputStream(file);
        Throwable var4 = null;

        Configuration var5;
        try {
            var5 = this.load((InputStream)is, defaults);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (is != null) {
                if (var4 != null) {
                    try {
                        is.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    is.close();
                }
            }

        }

        return var5;
    }

    public Configuration load(Reader reader) {
        return this.load((Reader)reader, (Configuration)null);
    }

    public Configuration load(Reader reader, Configuration defaults) {
        Map<String, Object> map = (Map)this.json.fromJson(reader, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }

        return new Configuration((Map)map, defaults);
    }

    public Configuration load(InputStream is) {
        return this.load((InputStream)is, (Configuration)null);
    }

    public Configuration load(InputStream is, Configuration defaults) {
        return this.load((Reader)(new InputStreamReader(is, Charsets.UTF_8)), defaults);
    }

    public Configuration load(String string) {
        return this.load((String)string, (Configuration)null);
    }

    public Configuration load(String string, Configuration defaults) {
        Map<String, Object> map = (Map)this.json.fromJson(string, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }

        return new Configuration((Map)map, defaults);
    }

    JsonConfiguration() {
    }

}

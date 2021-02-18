package net.cherryflavor.api.configuration;

import com.google.common.base.Charsets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class YamlConfiguration extends ConfigurationProvider {

    private final ThreadLocal<Yaml> yaml = new ThreadLocal<Yaml>() {
        protected Yaml initialValue() {
            Representer representer = new Representer() {
                {
                    this.representers.put(Configuration.class, new Represent() {
                        public Node representData(Object data) {
                            return represent(((Configuration)data).self);
                        }
                    });
                }
            };
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            return new Yaml(new Constructor(), representer, options);
        }
    };

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
        ((Yaml)this.yaml.get()).dump(config.self, writer);
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
        Map<String, Object> map = (Map)((Yaml)this.yaml.get()).loadAs(reader, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }

        return new Configuration((Map)map, defaults);
    }

    public Configuration load(InputStream is) {
        return this.load((InputStream)is, (Configuration)null);
    }

    public Configuration load(InputStream is, Configuration defaults) {
        Map<String, Object> map = (Map)((Yaml)this.yaml.get()).loadAs(is, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }

        return new Configuration((Map)map, defaults);
    }

    public Configuration load(String string) {
        return this.load((String)string, (Configuration)null);
    }

    public Configuration load(String string, Configuration defaults) {
        Map<String, Object> map = (Map)((Yaml)this.yaml.get()).loadAs(string, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }

        return new Configuration((Map)map, defaults);
    }

    YamlConfiguration() {
    }

}

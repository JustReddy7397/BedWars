package ga.justreddy.wiki.bedwars.utility;

import ga.justreddy.wiki.bedwars.model.addon.Addon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
 * @author JustReddy
 */
public class ClassUtil {

    public static List<Class<? extends Addon>> findAddons(File file) {
        List<Class<? extends Addon>> classes = new ArrayList<>();

        try {
            URL url = file.toURI().toURL();

            try (JarInputStream stream = new JarInputStream(url.openStream());
                 URLClassLoader loader = new URLClassLoader(new URL[]{url}, ClassUtil.class.getClassLoader());
                 JarFile jarFile = new JarFile(file)) {

                JarEntry addonYmlEntry = jarFile.getJarEntry("addon.yml");
                FileConfiguration config = null;

                if (addonYmlEntry != null) {
                    try (InputStream inputStream = jarFile.getInputStream(addonYmlEntry);
                         InputStreamReader reader = new InputStreamReader(inputStream)) {
                        config = YamlConfiguration.loadConfiguration(reader);
                    }
                }

                if (config == null || !config.contains("main-class")) {
                    return classes;
                }

                String mainClassPrefix = config.getString("main-class");

                JarEntry entry;
                while ((entry = stream.getNextJarEntry()) != null) {
                    if (!entry.getName().endsWith(".class")) {
                        continue;
                    }

                    String className = entry.getName()
                            .replace('/', '.')
                            .substring(0, entry.getName().length() - 6);

                    if (!className.startsWith(mainClassPrefix)) {
                        continue;
                    }

                    try {
                        Class<?> loadedClass = Class.forName(className, true, loader);
                        if (Addon.class.isAssignableFrom(loadedClass)) {
                            classes.add(loadedClass.asSubclass(Addon.class));
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

}

package ga.justreddy.wiki.bedwars.model.game.generator;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.config.Config;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class GeneratorManager {

    private final BedWars plugin;
    private final List<Generator> generators;
    private final File generatorsFile;
    private final Config config;

    public GeneratorManager(BedWars plugin) {
        this.plugin = plugin;
        this.generators = new ArrayList<>();
        try {
            this.config = new Config(plugin, "generators.yml");
        } catch (InvalidConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
        this.generatorsFile = config.getFile();
        start();
    }

    private void start() {
        final FileConfiguration config = getFileConfig();
        final ConfigurationSection section = config.getConfigurationSection("generators");
        for (final String id : section.getKeys(false)) {
            final ConfigurationSection genSection = section.getConfigurationSection(id);
            final Material item = XMaterial.matchXMaterial(genSection.getString("item")).orElseThrow(() -> new IllegalArgumentException("Invalid material for generator " + id)).get();
            final String display = genSection.getString("display");
            final List<String> lore = genSection.getStringList("lore");
            final GeneratorType type = GeneratorType.valueOf(genSection.getString("type").toUpperCase());
            final Map<String, Double> spawnDelays = new HashMap<>();
            final ConfigurationSection spawnDelaySection = genSection.getConfigurationSection("spawn-delays");
            for (final String key : spawnDelaySection.getKeys(false)) {
                spawnDelays.put(key, spawnDelaySection.getDouble(key));
            }
            final int maxSpawn = genSection.getInt("max-spawn");
            final int spawnAmount = genSection.getInt("spawn-amount");
            GeneratorArmorStand armorStand = null;
            if (type == GeneratorType.SPAWN_AIR) {
                final ConfigurationSection armorStandSection = genSection.getConfigurationSection("armorstand");
                final boolean enabled = armorStandSection.getBoolean("enabled");
                if (enabled) {
                    final List<String> lines = armorStandSection.getStringList("lines");
                    final boolean bobbing = armorStandSection.getBoolean("bobbing");
                    final Material armorStandItem = XMaterial.matchXMaterial(armorStandSection.getString("item")).orElseThrow(() -> new IllegalArgumentException("Invalid material for armor stand")).get();
                    final double rotationSpeed = armorStandSection.getDouble("rotation-speed");
                    armorStand = new GeneratorArmorStand(lines, bobbing, armorStandItem, rotationSpeed);
                }
                final Generator generator = new Generator(id, item, display, lore, type, spawnDelays, maxSpawn, spawnAmount, armorStand);
                generators.add(generator);
            }
        }
    }

    public void reload() throws IOException, InvalidConfigurationException {
        stop();
        config.reload();
        start();
    }

    public void stop() {
        generators.clear();
    }

    public List<Generator> getGenerators() {
        return generators;
    }

    public Config getConfig() {
        return config;
    }

    public FileConfiguration getFileConfig() {
        return config.getConfig();
    }


}

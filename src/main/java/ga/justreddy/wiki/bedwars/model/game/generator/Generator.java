package ga.justreddy.wiki.bedwars.model.game.generator;

import org.bukkit.Material;

import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class Generator {

    private final String id;
    private final Material item;
    private final String display;
    private final List<String> lore;
    private final GeneratorType type;
    private final Map<String, Double> spawnDelays ;
    private final int maxSpawn;
    private final int spawnAmount;
    private GeneratorArmorStand armorStand;

    public Generator(String id, Material item, String display, List<String> lore,
                     GeneratorType type, Map<String, Double> spawnDelays, int maxSpawn,
                     int spawnAmount,
                     GeneratorArmorStand armorStand) {
        this.id = id;
        this.item = item;
        this.display = display;
        this.lore = lore;
        this.type = type;
        this.spawnDelays = spawnDelays;
        this.maxSpawn = maxSpawn;
        this.spawnAmount = spawnAmount;
        this.armorStand = armorStand;
    }

    public String getId() {
        return id;
    }

    public Material getItem() {
        return item;
    }

    public String getDisplay() {
        return display;
    }

    public List<String> getLore() {
        return lore;
    }

    public GeneratorType getType() {
        return type;
    }

    public Map<String, Double> getSpawnDelays() {
        return spawnDelays;
    }

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public int getSpawnAmount() {
        return spawnAmount;
    }

    public GeneratorArmorStand getArmorStand() {
        return armorStand;
    }
}

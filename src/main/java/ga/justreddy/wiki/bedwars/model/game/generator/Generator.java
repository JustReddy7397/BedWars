package ga.justreddy.wiki.bedwars.model.game.generator;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.utility.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class Generator {

    private final BedWars plugin = BedWars.getPlugin(BedWars.class);

    private final String id;
    private final Material item;
    private final String display;
    private final List<String> lore;
    private final GeneratorType type;
    private final Map<String, Double> spawnDelays;
    private int tier = 1;
    private final int maxSpawn;
    private final int spawnAmount;
    private final GeneratorArmorStand armorStand;
    private int spawnTaskId = -1;

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

    public void spawn(World world, Location location) {
        // Spawn logic here
        if (armorStand != null) {
            armorStand.spawn(world, location.clone().add(0, 2, 0));
        }

        startSpawnTask(world, location.clone());
    }

    private void startSpawnTask(World world, Location location) {
        if (spawnTaskId != -1) {
            Bukkit.getScheduler().cancelTask(spawnTaskId);
        }
        final double delay = spawnDelays.getOrDefault("tier" + tier, 10.0);
        spawnTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            int entityCount = 0;

            for (final Entity entity : world.getNearbyEntities(location, 2, 1, 2)) {
                if (entity.getType() != EntityType.DROPPED_ITEM) {
                    continue;
                }
                entityCount++;
                if (entityCount >= maxSpawn) {
                    break;
                }
            }

            if (entityCount >= maxSpawn) {
                return;
            }

            for (int i = 0; i < spawnAmount; i++) {
                if (entityCount >= maxSpawn) {
                    break;
                }
                world.dropItemNaturally(location, new ItemBuilder(item).withName(display).withLore(lore)
                        .build());
                entityCount++;
            }

        }, (long) (delay * 20), (long) (delay * 20));

    }

    public void upgrade(World world, Location location) {
        tier++;
        startSpawnTask(world, location);
    }

    public void remove() {
        if (spawnTaskId != -1) {
            Bukkit.getScheduler().cancelTask(spawnTaskId);
            spawnTaskId = -1;
        }
        armorStand.remove();
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

    public Generator cloneGenerator()  {
        return new Generator(id, item, display, new ArrayList<>(lore), type, new HashMap<>(spawnDelays), maxSpawn, spawnAmount, new GeneratorArmorStand(
                armorStand.getLines(),
                armorStand.isBobbing(),
                armorStand.getItem(),
                armorStand.getRotationSpeed()
        ));
    }
}

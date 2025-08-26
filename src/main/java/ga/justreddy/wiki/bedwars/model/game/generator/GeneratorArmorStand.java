package ga.justreddy.wiki.bedwars.model.game.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

/**
 * @author JustReddy
 */
public class GeneratorArmorStand {

    private static final double OFFSET = 0.25;

    private final List<String> lines;
    private final boolean bobbing;
    private final Material item;
    private final double rotationSpeed;

    public GeneratorArmorStand(List<String> lines, boolean bobbing, Material item, double rotationSpeed) {
        this.lines = lines;
        this.bobbing = bobbing;
        this.item = item;
        this.rotationSpeed = rotationSpeed;
    }

    public void spawn(World world, Location location) {

    }

    public List<String> getLines() {
        return lines;
    }

    public boolean isBobbing() {
        return bobbing;
    }

    public Material getItem() {
        return item;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }
}

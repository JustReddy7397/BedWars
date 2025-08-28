package ga.justreddy.wiki.bedwars.model.game.generator;

import ga.justreddy.wiki.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JustReddy
 */
public class GeneratorArmorStand {

    private static final double OFFSET = 0.25;

    // Can't be bothered to use the constructor stuff here lmao
    private final BedWars plugin = BedWars.getPlugin(BedWars.class);

    private final List<String> lines;
    private final boolean bobbing;
    private final Material item;
    private final double rotationSpeed;
    private final List<ArmorStand> armorStands;
    private ArmorStand helmStand;
    private int rotationTaskId;
    private boolean up;
    private int rotate;

    public GeneratorArmorStand(List<String> lines, boolean bobbing, Material item, double rotationSpeed) {
        this.lines = lines;
        this.bobbing = bobbing;
        this.item = item;
        this.rotationSpeed = rotationSpeed;
        this.armorStands = new ArrayList<>();
    }

    public void spawn(World world, Location location) {
        List<String> newLines = new ArrayList<>(lines);
        Collections.reverse(newLines);
        double yaw = location.getYaw() + 2.0;
        for (final String line : newLines) {
            Location loc = location.clone().add(0.0, OFFSET, 0.0);
            loc.setYaw((float) yaw);
            ArmorStand armorStand = world.spawn(loc, ArmorStand.class);
            armorStand.setVisible(false);
            armorStand.setCustomName(line);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStand.setBasePlate(false);
            armorStands.add(armorStand);
            location.add(0.0, OFFSET, 0.0);
            yaw += 2.0;
        }
        if (item != null && item != Material.AIR) {
            Location loc = location.clone().add(0.0, OFFSET, 0.0);
            yaw -= 2.0;
            loc.setYaw((float) yaw);
            helmStand = world.spawn(loc, ArmorStand.class);
            helmStand.setVisible(false);
            helmStand.setCustomNameVisible(false);
            helmStand.setGravity(false);
            helmStand.setMarker(true);
            helmStand.setBasePlate(false);
            helmStand.getEquipment().setHelmet(new ItemStack(item));
        }

        rotationTaskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    if (helmStand == null) {
                        return;
                    }
                    if (up) {
                        if (rotate >= 540) {
                            up = false;
                        }
                        if (rotate > 500) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 1), 0));
                        } else if (rotate > 470) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 2), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().add(0, 0.005D, 0));
                            }
                        } else if (rotate > 450) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 3), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().add(0, 0.001D, 0));
                            }
                        } else {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 4), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().add(0, 0.002D, 0));
                            }
                        }
                    } else {
                        if (rotate <= 0) {
                            up = true;
                        }
                        if (rotate > 120) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 4), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().subtract(0, 0.002D, 0));
                            }
                        } else if (rotate > 90) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 3), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().add(0, 0.001D, 0));
                            }
                        } else if (rotate > 70) {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 2), 0));
                            if (bobbing) {
                                helmStand.teleport(helmStand.getLocation().add(0, 0.005D, 0));
                            }
                        } else {
                            helmStand.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 1), 0));
                        }
                    }
                }, 0L, 1L).

                getTaskId();
    }

    public void remove() {
        Bukkit.getScheduler().cancelTask(rotationTaskId);
        armorStands.forEach(ArmorStand::remove);
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

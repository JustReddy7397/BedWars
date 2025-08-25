package ga.justreddy.wiki.bedwars.model.addon;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.addon.cosmetic.Cosmetic;
import ga.justreddy.wiki.bedwars.model.addon.cosmetic.CosmeticType;
import ga.justreddy.wiki.bedwars.utility.ClassUtil;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author JustReddy
 */
public class AddonManager {

    private static boolean initialized = false;
    private final BedWars plugin;
    private final Set<Addon> addons;
    private final File addonsFolder;

    public AddonManager(BedWars plugin) {
        if (initialized) {
            throw new IllegalStateException("AddonManager is already initialized!");
        }
        this.plugin = plugin;
        this.addons = new HashSet<>();
        this.addonsFolder = new File(plugin.getDataFolder(), "addons");
        if (!addonsFolder.exists()) {
            addonsFolder.mkdirs();
        }
        start();
        initialized = true;
    }

    public void start() {
        File[] files = this.addonsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (files != null) {
            for (File file : files) {
                this.registerAddons(file);
            }
        }
    }

    public void registerAddon(Addon addon) {
        this.addons.add(addon);
    }

    public void unregisterAddon(Addon addon) {
        this.addons.remove(addon);
    }

    public Set<Addon> getAddons() {
        return new HashSet<>(addons);
    }

    private void registerAddons(File jarFile) {
        try {
            List<Class<? extends Addon>> addonClasses = ClassUtil.findAddons(jarFile);
            for (Class<? extends Addon> addonClass : addonClasses) {
                Addon addon = addonClass.getConstructor().newInstance();
                this.registerAddon(addon);
                plugin.getLogger().info("Registered addon: " + addon.getAddonName() + " from: " + jarFile.getName());
            }
        } catch (Exception exception) {
            plugin.getLogger().warning("Couldn't register addons from: " + jarFile.getName());
        }
    }

    public Set<Cosmetic> getCosmetics() {
        Set<Cosmetic> cosmetics = new HashSet<>();
        for (Addon addon : addons) {
            if (addon instanceof Cosmetic) {
                cosmetics.add((Cosmetic) addon);
            }
        }
        return cosmetics;
    }

    public <T> T getCosmeticByIdAndType(int id, CosmeticType type) {
        return null; // TODO: implement
    }

}

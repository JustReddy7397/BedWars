package ga.justreddy.wiki.bedwars;

import org.bukkit.plugin.java.JavaPlugin;

public final class BedWars extends JavaPlugin {

    private static BedWars instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BedWars getInstance() {
        return instance;
    }
}

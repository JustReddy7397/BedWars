package ga.justreddy.wiki.bedwars.model.game.shop;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class Shop {

    private final BedWars plugin;
    private final FileConfiguration config;
    private final ShopType type;
    private final List<ShopCategory> categories;

    public Shop(BedWars plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        this.categories = new ArrayList<>();
        this.type = ShopType.valueOf(config.getString("type"));
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ShopType getType() {
        return type;
    }

    public List<ShopCategory> getCategories() {
        return categories;
    }

    public void open(GamePlayer gamePlayer) {
        final ShopGui clone = new ShopGui(plugin, config);
        clone.open(gamePlayer);
    }

    public void spawn(Location location) {
        // TODO spawn villager owo
    }


    public void loadCategories(ShopManager shopManager) {
        for (final String category : config.getStringList("categories")) {
            ShopCategory shopCategory = shopManager.getCategoryById(category);
            if (shopCategory == null) continue;
            categories.add(shopCategory);
        }

    }
}

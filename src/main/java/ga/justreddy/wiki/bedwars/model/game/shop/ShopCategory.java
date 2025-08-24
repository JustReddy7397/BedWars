package ga.justreddy.wiki.bedwars.model.game.shop;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class ShopCategory {

    private final ShopManager shopManager;
    private final String category;
    private final List<ShopItem> items;
    private final FileConfiguration configuration;
    private ShopGui gui;


    public ShopCategory(ShopManager shopManager, FileConfiguration configuration) {
        this.shopManager = shopManager;
        this.configuration = configuration;
        this.category = configuration.getString("category");
        this.items = new ArrayList<>();
        loadItems(configuration);
    }

    public void setGui(ShopGui gui) {
        this.gui = gui;
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public String getCategory() {
        return category;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public ShopGui getGui() {
        return gui;
    }

    private void loadItems(FileConfiguration configuration) {
        final ConfigurationSection items = configuration.getConfigurationSection("items");
        for (final String key : items.getKeys(false)) {
            final ShopItem item = new ShopItem(shopManager, items.getConfigurationSection(key));
            this.items.add(item);
        }
    }

}

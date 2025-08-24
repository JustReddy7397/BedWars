package ga.justreddy.wiki.bedwars.model.game.shop;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.game.shop.item.CustomShopItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class ShopManager {

    private final BedWars plugin;
    private final Map<String, Shop> shops;
    private final Map<String, ShopCategory> categories;
    private final Map<String, ShopItem> items;
    private final Map<String, CustomShopItem> customShopItems;
    private final File shopFolder;
    private final File categoryFolder;
    private final XMaterial fillerMaterial;

    public ShopManager(BedWars plugin) {
        this.plugin = plugin;
        this.fillerMaterial = XMaterial.matchXMaterial(plugin.getSettingsConfig().getConfig().getString("gui.filler").toUpperCase()).orElse(XMaterial.BLACK_STAINED_GLASS_PANE);
        this.shops = new HashMap<>();
        this.categories = new HashMap<>();
        this.items = new HashMap<>();
        this.customShopItems = new HashMap<>();
        this.shopFolder = new File(plugin.getDataFolder(), "shops");
        this.categoryFolder = new File(plugin.getDataFolder(), "/shops/categories");
        if (!shopFolder.exists()) {
            shopFolder.mkdirs();
        }
        if (!categoryFolder.exists()) {
            categoryFolder.mkdirs();
        }

        File file;

        if (!(file = new File(shopFolder.getAbsolutePath() + "/main_shop.yml")).exists()) {
            plugin.saveResource("shops/main_shop.yml", false);
        }

        registerShop("main", YamlConfiguration.loadConfiguration(file));


        if (!(file = new File(shopFolder.getAbsolutePath() + "/upgrade_shop.yml")).exists()) {
            plugin.saveResource("shops/upgrade_shop.yml", false);
        }

        registerShop("upgrade", YamlConfiguration.loadConfiguration(file));

        if (!categoryFolder.exists()) categoryFolder.mkdirs();

        start();
    }

    private void start() {
        loadShops();
        loadCategories();

        for (final Shop shop : shops.values()) {
            shop.loadCategories(this);
        }

        for (ShopCategory category : categories.values()) {
            for (ShopItem item : items.values()) {
                if (items.containsKey(item.getId())) {
                    continue;
                }
                items.put(item.getId(), item);
            }
        }

    }

    private void loadShops() {
        final File[] files = shopFolder.listFiles();

        if (files == null) {
            return;
        }

        for (final File file : files) {
            String name = file.getName();
            if (!name.endsWith("_shop.yml")) {
                continue;
            }
            if (name.equals("main_shop.yml")) continue;
            if (name.equals("upgrade_shop.yml")) continue;
            name = name.replace("_shop.yml", "");
            registerShop(name, YamlConfiguration.loadConfiguration(file));
        }
    }

    private void loadCategories() {
        final File[] files = categoryFolder.listFiles();

        if (files == null) {
            return;
        }

        for (final File file : files) {
            String name = file.getName();
            if (!name.endsWith("_category.yml")) {
                continue;
            }
            name = name.replace("_category.yml", "");
            registerCategory(name, YamlConfiguration.loadConfiguration(file));
        }

    }

    private void registerShop(String name, FileConfiguration config) {
        if (shops.containsKey(name)) {
            throw new IllegalArgumentException("Shop with name " + name + " already exists.");
        }
        final Shop shop = new Shop(plugin, config);
        shops.put(name, shop);
        ConfigurationSection section = config.getConfigurationSection("items");
        if (section == null) {
            return;
        }
        for (final String key : section.getKeys(false)) {
            ShopItem item = new ShopItem(this, section.getConfigurationSection(key));
            items.putIfAbsent(item.getId(), item);
        }
    }

    private void registerCategory(String name, FileConfiguration config) {
        if (categories.containsKey(name)) {
            throw new IllegalArgumentException("Category with name " + name + " already exists.");
        }

        final ShopCategory category = new ShopCategory(this, config);
        categories.put(name, category);
    }

    public Shop getByType(ShopType type) {
        for (final Shop shop : shops.values()) {
            if (shop.getType() == type) {
                return shop;
            }
        }
        return null;
    }

    public ShopCategory getCategoryById(String id) {
        return categories.getOrDefault(id, null);
    }

    public ShopItem getItemById(String id) {
        return items.getOrDefault(id, null);
    }

    public void registerCustomShopItem(CustomShopItem item) {
        if (customShopItems.containsKey(item.getId())) {
            throw new IllegalArgumentException("Custom shop item with ID " + item.getId() + " already exists.");
        }

        customShopItems.put(item.getId(), item);
    }

    public CustomShopItem getCustomItemById(String id) {
        return customShopItems.getOrDefault(id, null);
    }

    public XMaterial getFillerMaterial() {
        return fillerMaterial;
    }
}

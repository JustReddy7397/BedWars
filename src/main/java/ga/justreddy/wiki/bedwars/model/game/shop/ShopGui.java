package ga.justreddy.wiki.bedwars.model.game.shop;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import ga.justreddy.wiki.bedwars.utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author JustReddy
 */
public class ShopGui implements InventoryHolder {

    private final BedWars plugin;
    private final ShopManager shopManager;
    private final FileConfiguration config;
    private final Map<Integer, ShopItem> items;
    private final Map<Integer, ShopItem> quickBuy;
    private final Inventory inventory;

    public ShopGui(BedWars plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.shopManager = plugin.getShopManager();
        this.config = config;
        this.items = new HashMap<>();
        this.quickBuy = new HashMap<>();
        this.inventory = Bukkit.createInventory(this, config.getInt("rows") * 9, TextUtil.colorize(config.getString("title")));
        loadItems();
    }

    public void onClick(GamePlayer gamePlayer, int slot, boolean shift) {
        ShopItem item = items.get(slot);
        if (item == null) {
            item = quickBuy.get(slot);
            if (item == null) {
                return;
            }
            item.give(plugin.getActionManager(), gamePlayer, shift);
            return;
        }
        item.give(plugin.getActionManager(), gamePlayer, shift);
    }

    public void open(GamePlayer gamePlayer) {
        Optional<Player> optionalPlayer = gamePlayer.getBukkitPlayer();

        if (optionalPlayer.isEmpty()) {
            return;
        }

        final Player player = optionalPlayer.get();
        player.openInventory(inventory);

        if (config.isSet("quick-buy-slots")) {
            Map<Integer, ShopItem> quickBuy = new HashMap<>();
            ConfigurationSection section = plugin.getSettingsConfig().getConfig()
                    .getConfigurationSection("gui.quickbuy");

            final ShopItem item = new ShopItem(shopManager, section, "emptyslot.");

            for (final int i : config.getIntegerList("quick-buy-slots")) {
                quickBuy.put(i, item);
                inventory.setItem(i, item.getItemStack());
            }

            loadQuickBuy(gamePlayer.getQuickBuy().getQuickBuyItems());

        }
    }

    private void loadItems() {
        final ConfigurationSection section = config.getConfigurationSection("items");
        if (section == null) {
            return;
        }
        for (final String key : section.getKeys(false)) {
            final String id = section.getString(key + ".id");
            if (id == null) {
                continue;
            }
            final ShopItem item = shopManager.getItemById(id);
            for (final int slot : item.getSlots()) {
                items.put(slot, item);
            }

            for (final int slot : items.keySet()) {
                inventory.setItem(slot, items.get(slot).getItemStack());
            }
        }

    }

    public void loadQuickBuy(Map<Integer, String> items) {
        for (final int slot : items.keySet()) {
            ShopItem item = shopManager.getItemById(items.get(slot));
            ItemStack stack = inventory.getItem(slot);
            if (stack.getType() != XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
                continue;
            }
            if (item == null) {
                continue;
            }
            quickBuy.put(slot, item);
            inventory.setItem(slot, item.getItemStack());
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public List<ShopItem> getItems() {
        return new ArrayList<>(items.values());
    }

}

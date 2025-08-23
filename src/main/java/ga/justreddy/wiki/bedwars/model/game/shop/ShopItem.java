package ga.justreddy.wiki.bedwars.model.game.shop;

import ga.justreddy.wiki.bedwars.model.game.shop.item.CustomShopItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class ShopItem {

    private final String id;
    private ItemStack itemStack;
    private ShopPrice shopPrice;
    private int amount = 0;
    private int[] slots;
    private final boolean shouldColor;
    private final boolean canQuickBuy;
    private boolean dummy;
    private final List<String> actions;
    private boolean customItem;
    private CustomShopItem customShopItem;

    public ShopItem(Material material) {
        this.id = material.name() + UUID.randomUUID().toString().substring(0, 6);
        this.itemStack = new ItemStack(material);
        this.dummy = true;
        this.slots = new int[]{0};
        this.shouldColor = false;
        this.canQuickBuy = false;
        this.actions = new ArrayList<>();
        this.customItem = false;
        this.customShopItem = null;
    }

    public ShopItem(ConfigurationSection section, String id) {}

    public ShopItem(ConfigurationSection section) {}

    public void give() {}
}

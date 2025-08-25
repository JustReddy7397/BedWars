package ga.justreddy.wiki.bedwars.model.game.shop.item;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.bedwars.action.ActionManager;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import ga.justreddy.wiki.bedwars.model.game.shop.ShopManager;
import ga.justreddy.wiki.bedwars.utility.Replaceable;
import ga.justreddy.wiki.bedwars.utility.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

/**
 * @author JustReddy
 */
public class ShopItem {

    private final ShopManager shopManager;

    private final String id;
    private final ItemStack itemStack;
    private ShopPrice shopPrice;
    private int amount = 0;
    private int[] slots;
    private final boolean shouldColor;
    private final boolean canQuickBuy;
    private final boolean dummy;
    private final List<String> actions;
    private boolean customItem;
    private CustomShopItem customShopItem;
    private ItemType itemType = ItemType.NONE;

    public ShopItem(ShopManager shopManager, XMaterial material) {
        this.id = material.name() + UUID.randomUUID().toString().substring(0, 6);
        this.itemStack = material.parseItem();
        this.dummy = true;
        this.slots = new int[]{0};
        this.shouldColor = false;
        this.canQuickBuy = false;
        this.actions = new ArrayList<>();
        this.customItem = false;
        this.customShopItem = null;
        this.shopManager = shopManager;
    }

    public ShopItem(ShopManager shopManager, ConfigurationSection section, String id) {
        this.shopManager = shopManager;
        this.canQuickBuy = false;
        this.id = id + UUID.randomUUID().toString().substring(0, 6);
        ItemBuilder builder;
        if (section.getString("material").equals("FILLER")) {
            builder = new ItemBuilder(shopManager.getFillerMaterial().parseItem());
        } else {
            Optional<XMaterial> item = XMaterial.matchXMaterial(section.getString("material"));
            if (item.isEmpty())
                throw new NullPointerException("The material " + section.getString("material") + " is not a valid material!");
            builder = new ItemBuilder(item.get().parseItem());
        }
        this.dummy = true;
        if (section.contains("name")) builder.withName(section.getString("name"));
        if (section.contains("lore")) builder.withLore(section.getStringList("lore"));
        this.itemStack = builder.build();
        this.slots = new int[]{0};
        this.shouldColor = false;
        this.actions = new ArrayList<>();
        if (section.contains("customItem")) {
            this.customItem = true;
            this.customShopItem = shopManager.getCustomItemById(section.getString("customItem"));
        }
    }

    public ShopItem(ShopManager shopManager, ConfigurationSection section) {
        this.shopManager = shopManager;
        this.id = section.getString("id");
        ItemBuilder builder;
        if (section.getString("item.material").equals("FILLER")) {
            builder = new ItemBuilder(shopManager.getFillerMaterial().parseItem());
            this.dummy = true;
        } else {
            Optional<XMaterial> item = XMaterial.matchXMaterial(section.getString("item.material"));
            if (item.isEmpty())
                throw new NullPointerException("The material " + section.getString("item.material") + " is not a valid material!");
            builder = new ItemBuilder(item.get().parseItem());
            this.dummy = section.getBoolean("dummy");
        }
        if (!dummy) {
            Optional<XMaterial> priceMaterial = XMaterial.matchXMaterial(section.getString("price.material"));
            if (priceMaterial.isEmpty())
                throw new NullPointerException("The material " + section.getString("price.material") + " is not a valid material!");
            int price = section.getInt("price.amount");
            this.shopPrice = new ShopPrice(priceMaterial.get().parseMaterial(), price);
            this.amount = section.getInt("amount");
        }

        if (section.contains("item.name")) builder.withName(section.getString("item.name"));
        if (shopPrice != null) {
            if (section.contains("item.lore")) builder.withLore(
                    section.getStringList("item.lore"),
                    new Replaceable("<price>", shopPrice.getPrice() + " " + shopPrice.getMaterial().name())
            );
        } else {
            if (section.contains("item.lore")) builder.withLore(
                    section.getStringList("item.lore")
            );
        }

        this.itemStack = builder.build();
        this.canQuickBuy = section.getBoolean("quick-buy");


        if (section.contains("enchantments")) {
            for (String enchantment : section.getStringList("enchantments")) {
                String[] split = enchantment.split(":");
                builder.withEnchantment(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
            }
        }

        if (section.contains("slots")) {
            String[] slots = section.getString("slots").split(", ");
            this.slots = new int[slots.length];
            for (int i = 0; i < slots.length; i++) {
                this.slots[i] = Integer.parseInt(slots[i]);
            }
        } else if (section.contains("slot")) {
            this.slots = new int[]{section.getInt("slot")};
        }
        this.shouldColor = section.getBoolean("color");
        this.actions = section.getStringList("actions");
        if (amount != 0) this.itemStack.setAmount(amount);
        if (section.contains("customItem")) {
            this.customItem = true;
            this.customShopItem = shopManager.getCustomItemById(section.getString("customItem"));
        }
        if (section.contains("type")) {
            this.itemType = ItemType.valueOf(section.getString("type").toUpperCase());
        }
    }

    public void give(ActionManager actionManager, GamePlayer gamePlayer, boolean shift) {

        if (shift && canQuickBuy) {
            // TODO OPEN QUICK BUY MENU
        }

        for (final String action : actions) {
            final String[] split = action.split(";");
            final String actionId = split[0];
            if (actionId.equalsIgnoreCase("category")) {
                actionManager.triggerAction(actionId, gamePlayer, split[1]);
            }
            if (actionId.equalsIgnoreCase("give") && !dummy) {
                actionManager.triggerAction(actionId, gamePlayer, this);
            }
        }
    }

    public boolean hasEnough(GamePlayer gamePlayer) {
        Optional<Player> optionalPlayer = gamePlayer.getBukkitPlayer();
        if (optionalPlayer.isEmpty()) {
            return false;
        }
        final Player player = optionalPlayer.get();

        final PlayerInventory playerInventory = player.getInventory();
        final ItemStack[] contents = playerInventory.getContents();
        for (final ItemStack content : contents) {
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }
            if (content.getType() != shopPrice.getMaterial()) {
                continue;
            }
            if (content.getAmount() < shopPrice.getPrice()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public void takeItems(GamePlayer gamePlayer) {
        Optional<Player> optionalPlayer = gamePlayer.getBukkitPlayer();
        if (optionalPlayer.isEmpty()) {
            return;
        }
        final Player player = optionalPlayer.get();

        final PlayerInventory playerInventory = player.getInventory();
        final ItemStack[] contents = playerInventory.getContents();
        for (final ItemStack content : contents) {
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }
            if (content.getType() != shopPrice.getMaterial()) {
                continue;
            }
            if (content.getAmount() < shopPrice.getPrice()) {
                continue;
            }
            if (content.getAmount() == shopPrice.getPrice()) {
                playerInventory.removeItem(content);
                player.updateInventory();
                break;
            }
            content.setAmount(content.getAmount() - shopPrice.getPrice());
            player.updateInventory();
            break;
        }
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ShopPrice getShopPrice() {
        return shopPrice;
    }

    public int getAmount() {
        return amount;
    }

    public int[] getSlots() {
        return slots;
    }

    public boolean isShouldColor() {
        return shouldColor;
    }

    public boolean isCanQuickBuy() {
        return canQuickBuy;
    }

    public boolean isDummy() {
        return dummy;
    }

    public List<String> getActions() {
        return actions;
    }

    public boolean isCustomItem() {
        return customItem;
    }

    public CustomShopItem getCustomShopItem() {
        return customShopItem;
    }

    public ItemType getItemType() {
        return itemType;
    }
}

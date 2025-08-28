package ga.justreddy.wiki.bedwars.action.actions;

import ga.justreddy.wiki.bedwars.BedWars;
import ga.justreddy.wiki.bedwars.action.Action;
import ga.justreddy.wiki.bedwars.model.entity.GamePlayer;
import ga.justreddy.wiki.bedwars.model.entity.data.PlayerHotBar;
import ga.justreddy.wiki.bedwars.model.game.shop.item.CustomShopItem;
import ga.justreddy.wiki.bedwars.model.game.shop.item.ItemType;
import ga.justreddy.wiki.bedwars.model.game.shop.item.ShopItem;
import ga.justreddy.wiki.bedwars.utility.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

/**
 * @author JustReddy
 */
public class GiveAction extends Action {

    @Override
    public String getId() {
        return "give";
    }

    @Override
    public void onAction(BedWars bedWars, GamePlayer gamePlayer, Object data) {
        if (gamePlayer.getGame() == null) {
            return;
        }
        if (!(data instanceof ShopItem)) {
            return;
        }

        final PlayerHotBar hotBar = gamePlayer.getPlayerHotBar();

        final ShopItem item = (ShopItem) data;

        if (!item.hasEnough(gamePlayer)) {
            // TODO send message to player
        }

        Optional<Player> optionalPlayer = gamePlayer.getBukkitPlayer();

        if (!optionalPlayer.isPresent()) {
            return;
        }

        final Player player = optionalPlayer.get();
        final PlayerInventory playerInventory = player.getInventory();
        ItemBuilder builder = null;
        if (!item.isCustomItem()) {
            final ItemStack itemStack = item.getItemStack();
            builder = new ItemBuilder(itemStack);
            if (itemStack.hasItemMeta()) {
                final ItemMeta meta = itemStack.getItemMeta();
                if (meta.hasEnchants()) {
                    builder.withEnchantments(meta.getEnchants());
                }
                if (item.isShouldColor()) {
                    // TODO GET GAME TEAM AND COLOR
                }

                builder.withName(itemStack.getItemMeta().getDisplayName());

                builder.withAmount(item.getAmount());
                item.takeItems(gamePlayer);
                giveHotBarItems(hotBar, item, playerInventory, builder);
            }
        } else {
            final CustomShopItem customShopItem = item.getCustomShopItem();
            builder = new ItemBuilder(customShopItem.getStarterItem());
            builder.withAmount(item.getAmount());
            // TODO SET PersistentData for custom item
            if (customShopItem.isUpgradable()) {
                final ItemStack upgrade = customShopItem.nextUpgrade(gamePlayer);
                final ItemStack previous = customShopItem.previousUpgrade(gamePlayer);
                if (upgrade == null) {
                    // TODO throw error
                    return;
                }

                builder.withAmount(upgrade.getAmount());
                if (upgrade.hasItemMeta()) {
                    builder.withName(upgrade.getItemMeta().getDisplayName());
                    builder.withLore(upgrade.getItemMeta().getLore());
                }

                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    final ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack == null) {
                        continue;
                    }
                    // TODO CHECK PERSISTENT DATA STUFF
                    if (previous != null) {
                        giveHotBarItems(hotBar, item, playerInventory, builder);
                    } else {
                        playerInventory.removeItem(itemStack);
                        playerInventory.setItem(i, upgrade);
                    }
                    break;
                }
                item.takeItems(gamePlayer);
            } else {
                item.takeItems(gamePlayer);
                giveHotBarItems(hotBar, item, playerInventory, builder);
            }
        }
    }

    private void giveHotBarItems(PlayerHotBar hotBar, ShopItem item, PlayerInventory playerInventory, ItemBuilder builder) {
        final ItemType itemType = item.getItemType();
        final List<Integer> slots = hotBar.getSlotsFromType(itemType);
        boolean given = false;
        for (final int slot : slots) {
            final ItemStack hotBarItem = playerInventory.getItem(slot);
            if (hotBarItem == null || hotBarItem.getType() == Material.AIR) {
                given = true;
                playerInventory.setItem(slot, builder.build());
                break;
            }
        }
        if (!given) {
            playerInventory.addItem(builder.build());
        }
    }
}

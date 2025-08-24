package ga.justreddy.wiki.bedwars.model.entity.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player's quick buy items in BedWars.
 * This class allows players to add, remove, and retrieve items
 * from their quick buy slots.
 *
 * @author JustReddy
 */
public class PlayerQuickBuy {

    private final Map<Integer, String> quickBuyItems;

    public PlayerQuickBuy() {
        this.quickBuyItems = new HashMap<>();
    }

    /**
     * Adds an item to the quick buy slots.
     * If the slot already contains an item, it will be replaced.
     *
     * @param slot The slot number where the item should be added
     * @param item The item ID to be added to the quick buy slot
     */
    public void add(int slot, String item) {
        if (this.quickBuyItems.containsKey(slot)) {
            remove(slot);
        }
        this.quickBuyItems.put(slot, item);
    }

    /**
     * Retrieves the item ID from a specific quick buy slot.
     *
     * @param slot The slot number to retrieve the item from
     * @return The item ID in the specified slot, or null if the slot is empty
     */
    public String get(int slot) {
        return this.quickBuyItems.get(slot);
    }

    /**
     * Retrieves the slot number for a given item ID.
     *
     * @param id The item ID to search for
     * @return The slot number where the item is located, or -1 if not found
     */
    public int get(String id) {
        for (Map.Entry<Integer, String> entry : this.quickBuyItems.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Removes an item from a specific quick buy slot.
     * @param slot The slot number from which the item should be removed
     */
    public void remove(int slot) {
        this.quickBuyItems.remove(slot);
    }

    /**
     * Returns the quick buy items.
     * @return A map of slot numbers to item IDs
     */
    public Map<Integer, String> getQuickBuyItems() {
        return quickBuyItems;
    }
}

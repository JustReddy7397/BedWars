package ga.justreddy.wiki.bedwars.model.entity.data;

import ga.justreddy.wiki.bedwars.model.game.shop.item.ItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class PlayerHotBar {

    private final Map<Integer, ItemType> itemTypes;

    public PlayerHotBar() {
        this.itemTypes = new HashMap<>();
    }

    public void addItem(int slot, ItemType itemType) {
        this.itemTypes.put(slot, itemType);
    }

    public void removeItem(int slot) {
        this.itemTypes.remove(slot);
    }

    public List<Integer> getSlotsFromType(ItemType itemType) {
        return this.itemTypes.entrySet().stream()
                .filter(entry -> entry.getValue() == itemType)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public ItemType getItemFromSlot(int slot) {
        return this.itemTypes.get(slot);
    }

    public Map<Integer, ItemType> getItemTypes() {
        return itemTypes;
    }
}

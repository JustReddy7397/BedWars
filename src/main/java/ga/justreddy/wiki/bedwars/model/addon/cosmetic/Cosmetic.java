package ga.justreddy.wiki.bedwars.model.addon.cosmetic;

import ga.justreddy.wiki.bedwars.model.addon.Addon;

import java.util.Objects;

/**
 * @author JustReddy
 */
public abstract class Cosmetic implements Addon {

    private final int id;
    private final String displayName;
    private final int price;
    private final CosmeticRarity rarity;
    private final CosmeticType type;

    public Cosmetic(int id, String displayName, int price, CosmeticRarity rarity, CosmeticType type) {
        this.id = id;
        this.displayName = displayName;
        this.price = price;
        this.rarity = rarity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    public CosmeticRarity getRarity() {
        return rarity;
    }

    public CosmeticType getType() {
        return type;
    }

    @Override
    public String getAddonName() {
        return "Cosmetic";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cosmetic)) {
            return false;
        }

        final Cosmetic other = (Cosmetic) obj;
        return other.id == this.id && other.type == this.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }


}

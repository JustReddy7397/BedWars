package ga.justreddy.wiki.bedwars.model.game.shop.item;

import org.bukkit.Material;

/**
 * @author JustReddy
 */
public class ShopPrice {

    private final Material material;
    private final int price;

    public ShopPrice(Material material, int price) {
        this.material = material;
        this.price = price;
    }

    public Material getMaterial() {
        return material;
    }

    public int getPrice() {
        return price;
    }

}
